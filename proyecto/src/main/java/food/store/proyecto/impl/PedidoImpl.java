package food.store.proyecto.impl;

import food.store.proyecto.entity.DetallePedido;
import food.store.proyecto.entity.Pedido;
import food.store.proyecto.entity.Producto;
import food.store.proyecto.entity.Usuario;
import food.store.proyecto.entity.dto.detallePedido.DetallePedidoEdit;
import food.store.proyecto.entity.dto.pedido.*;
import food.store.proyecto.entity.enums.Estado;
import food.store.proyecto.entity.mapper.DetallePedidoMapper;
import food.store.proyecto.entity.mapper.PedidoMapper;
import food.store.proyecto.repository.PedidoRepository;
import food.store.proyecto.repository.ProductoRepository;
import food.store.proyecto.repository.UsuarioRepository;
import food.store.proyecto.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoImpl extends BaseImpl<Pedido, PedidoDto, PedidoCreate, Long, PedidoEdit> implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final DetallePedidoMapper detallePedidoMapper;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    // ==============================
    // 🟢 CREAR PEDIDO
    // ==============================
    @Override
    @Transactional
    public PedidoDto save(PedidoCreate create) {
        // 1️⃣ Verificar usuario
        Usuario usuario = usuarioRepository.findById(create.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + create.usuarioId()));

        // 2️⃣ Mapear el pedido y asignar el usuario
        Pedido pedido = pedidoMapper.toEntity(create, usuario);

        // 3️⃣ Procesar los detalles (items)
        List<DetallePedido> detalles = create.items() != null
                ? create.items().stream().map(detalleCreate -> {
            DetallePedido detalle = detallePedidoMapper.toEntity(detalleCreate);

            // Buscar producto
            Producto producto = productoRepository.findById(detalleCreate.productoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleCreate.productoId()));

            // Validar stock
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Reducir stock si el pedido está pendiente
            if (create.estado() == Estado.PENDIENTE) {
                producto.setStock(producto.getStock() - detalle.getCantidad());
                productoRepository.save(producto);
            }

            detalle.setProducto(producto);
            detalle.setPedido(pedido);
            return detalle;
        }).toList()
                : List.of();

        pedido.setItems(detalles);

        // 4️⃣ Guardar el pedido (cascada guarda detalles)
        Pedido saved = pedidoRepository.save(pedido);

        return pedidoMapper.toDto(saved);
    }

    // ==============================
    // 🟠 ACTUALIZAR PEDIDO
    // ==============================
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PedidoDto update(Long id, PedidoEdit editDto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        // Actualizar campos principales
        pedido.setFecha(editDto.fecha());
        pedido.setEstado(editDto.estado());
        pedido.setTotal(editDto.total());

        // 🔸 Reintegrar stock al cancelar pedido
        if (editDto.estado() == Estado.CANCELADO) {
            for (DetallePedido detalle : pedido.getItems()) {
                Producto producto = productoRepository.findById(detalle.getProducto().getId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado para reintegro"));
                producto.setStock(producto.getStock() + detalle.getCantidad());
                productoRepository.save(producto);
            }
        }

        // 🔸 Si vienen detalles para modificar
        if (editDto.items() != null && !editDto.items().isEmpty()) {
            for (DetallePedidoEdit detalleEdit : editDto.items()) {
                DetallePedido existente = pedido.getItems().stream()
                        .filter(d -> d.getId().equals(detalleEdit.id()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("DetallePedido no encontrado con ID: " + detalleEdit.id()));

                existente.setCantidad(detalleEdit.cantidad());
                existente.setSubtotal(detalleEdit.subtotal());
            }
        }

        Pedido actualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(actualizado);
    }


    @Override
    @Transactional(readOnly = true)
    public List<PedidoDto> listarPorUsuario(Long usuarioId) {
        // 1️⃣ Verificar existencia del usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        // 2️⃣ Buscar pedidos asociados a ese usuario
        List<Pedido> pedidos = pedidoRepository.findByUsuario_Id(usuarioId);

        if (pedidos.isEmpty()) {
            System.out.println("⚠️ No se encontraron pedidos para el usuario con ID: " + usuarioId);
            return List.of();
        }

        // 3️⃣ Mapear a DTO incluyendo sus detalles (items)
        return pedidos.stream()
                .map(pedidoMapper::toDto)
                .toList();
    }

}
