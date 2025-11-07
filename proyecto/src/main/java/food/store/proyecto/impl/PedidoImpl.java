package food.store.proyecto.impl;

import food.store.proyecto.entity.DetallePedido;
import food.store.proyecto.entity.Pedido;
import food.store.proyecto.entity.dto.pedido.*;
import food.store.proyecto.entity.mapper.DetallePedidoMapper;
import food.store.proyecto.entity.mapper.PedidoMapper;
import food.store.proyecto.repository.PedidoRepository;
import food.store.proyecto.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoImpl extends BaseImpl<Pedido, PedidoDto, PedidoCreate, Long, PedidoEdit> implements PedidoService {
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final DetallePedidoMapper detallePedidoMapper;


    @Override
    public PedidoDto save(PedidoCreate create) {
        Pedido pedido = pedidoMapper.toEntity(create);

        // Convertimos los DetallePedidoCreate a entidades
        List<DetallePedido> detalles = create.detalles() != null
                ? create.detalles().stream()
                .map(detallePedidoMapper::toEntity)
                .toList()
                : List.of();

        pedido.setDetalles(detalles);
        Pedido saved = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(saved);
    }

    @Override
    public PedidoDto update(Long id, PedidoEdit editDto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        pedido.setFecha(editDto.fecha());
        pedido.setEstado(editDto.estado());
        pedido.setTotal(editDto.total());

        // Actualizamos los detalles si vienen en el Edit
        if (editDto.detalles() != null) {
            pedido.setDetalles(
                    editDto.detalles().stream()
                            .map(detalle -> detallePedidoMapper.toEntityFromEdit(detalle))
                            .toList()
            );
        }

        Pedido actualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(actualizado);
    }
}
