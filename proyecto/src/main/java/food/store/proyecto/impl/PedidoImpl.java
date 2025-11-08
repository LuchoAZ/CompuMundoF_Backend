package food.store.proyecto.impl;

import food.store.proyecto.entity.DetallePedido;
import food.store.proyecto.entity.Pedido;
import food.store.proyecto.entity.dto.detallePedido.DetallePedidoEdit;
import food.store.proyecto.entity.dto.pedido.*;
import food.store.proyecto.entity.enums.Estado;
import food.store.proyecto.entity.mapper.DetallePedidoMapper;
import food.store.proyecto.entity.mapper.PedidoMapper;
import food.store.proyecto.repository.PedidoRepository;
import food.store.proyecto.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import food.store.proyecto.entity.Producto;
import food.store.proyecto.repository.ProductoRepository;
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



    @Override
    public PedidoDto save(PedidoCreate create) {
        Pedido pedido = pedidoMapper.toEntity(create);

        // Convertimos los DetallePedidoCreate a entidades y validamos stock
        List<DetallePedido> detalles = create.detalles() != null
                ? create.detalles().stream().map(detalleCreate -> {
            DetallePedido detalle = detallePedidoMapper.toEntity(detalleCreate);

            // Traemos el producto desde la base de datos
            Producto producto = productoRepository.findById(detalleCreate.productoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleCreate.productoId()));

            // Validamos stock
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Reducimos stock si el pedido está pendiente
            if (create.estado() == Estado.PENDIENTE) {
                producto.setStock(producto.getStock() - detalle.getCantidad());
                productoRepository.save(producto);
                productoRepository.flush(); // 🔹 Fuerza escritura inmediata
            }

            detalle.setProducto(producto); // Asociamos el producto real al detalle
            return detalle;
        }).toList()
                : List.of();

        pedido.setDetalles(detalles);

        Pedido saved = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(saved);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PedidoDto update(Long id, PedidoEdit editDto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        // Actualizamos datos básicos
        pedido.setFecha(editDto.fecha());
        pedido.setEstado(editDto.estado());
        pedido.setTotal(editDto.total());

        // 🔸 Reintegro de stock al pasar a CANCELADO
        if (editDto.estado() == Estado.CANCELADO) {
            for (DetallePedido detalle : pedido.getDetalles()) {
                Producto producto = productoRepository.findById(detalle.getProducto().getId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado para reintegro"));
                producto.setStock(producto.getStock() + detalle.getCantidad());
                productoRepository.save(producto);
                productoRepository.flush(); // 🔹 Fuerza escritura inmediata
            }
        }

        // 🔸 Si se envían detalles nuevos, actualizamos sin reemplazar lista (manteniendo IDs)
        if (editDto.detalles() != null && !editDto.detalles().isEmpty()) {
            for (DetallePedidoEdit detalleEdit : editDto.detalles()) {
                DetallePedido existente = pedido.getDetalles().stream()
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

}
