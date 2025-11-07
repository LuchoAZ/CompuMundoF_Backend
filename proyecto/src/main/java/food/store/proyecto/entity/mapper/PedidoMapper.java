package food.store.proyecto.entity.mapper;

import food.store.proyecto.entity.DetallePedido;
import food.store.proyecto.entity.Pedido;
import food.store.proyecto.entity.dto.detallePedido.DetallePedidoDto;
import food.store.proyecto.entity.dto.pedido.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component

public class PedidoMapper implements BaseMapper<Pedido, PedidoDto, PedidoCreate> {

private final DetallePedidoMapper detallePedidoMapper;

    public PedidoMapper(DetallePedidoMapper detallePedidoMapper) {
        this.detallePedidoMapper = detallePedidoMapper;
    }

    public DetallePedidoMapper getDetallePedidoMapper() {
        return detallePedidoMapper;
    }


    @Override
    public Pedido toEntity(PedidoCreate createDto) {
        if (createDto == null) return null;

       List<DetallePedido> detalles = createDto.detalles() != null
               ? createDto.detalles().stream()
               .map(detallePedidoMapper::toEntity)
               .toList()
               : List.of();

        return Pedido.builder()
                .fecha(createDto.fecha())
                .estado(createDto.estado())
                .total(createDto.total())
                .detalles(detalles)
                .build();
    }

    @Override
    public PedidoDto toDto(Pedido entity) {
        if (entity == null) return null;

        List<DetallePedidoDto> detalles = entity.getDetalles() != null
                ? entity.getDetalles().stream()
                .map(detallePedidoMapper::toDto)
                .toList()
                : List.of();

        return new PedidoDto(
                entity.getId(),
                entity.getFecha(),
                entity.getEstado(),
                entity.getTotal(),
                detalles
        );
    }

}
