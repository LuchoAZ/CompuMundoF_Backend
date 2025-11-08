package food.store.proyecto.entity.mapper;

import food.store.proyecto.entity.DetallePedido;
import food.store.proyecto.entity.Pedido;
import food.store.proyecto.entity.Usuario;
import food.store.proyecto.entity.dto.detallePedido.DetallePedidoDto;
import food.store.proyecto.entity.dto.pedido.PedidoCreate;
import food.store.proyecto.entity.dto.pedido.PedidoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PedidoMapper implements BaseMapper<Pedido, PedidoDto, PedidoCreate> {

    private final DetallePedidoMapper detallePedidoMapper;

    /**
     * Convierte un DTO de creación a entidad, vinculando al usuario.
     * El usuario se pasa como parámetro desde el servicio antes de guardar.
     */
    public Pedido toEntity(PedidoCreate createDto, Usuario usuario) {
        if (createDto == null) return null;

        // Mapear los detalles (items)
        List<DetallePedido> detalles = createDto.items() != null
                ? createDto.items().stream()
                .map(detallePedidoMapper::toEntity)
                .toList()
                : List.of();

        Pedido pedido = Pedido.builder()
                .fecha(LocalDate.now()) // Se asigna fecha actual del servidor
                .estado(createDto.estado())
                .total(createDto.total())
                .usuario(usuario) // Se setea el usuario recibido
                .items(detalles)  // Vincula los detalles
                .build();

        // Vincular los detalles al pedido (composición)
        detalles.forEach(detalle -> detalle.setPedido(pedido));

        return pedido;
    }

    /**
     * Método implementado para compatibilidad con BaseMapper,
     * aunque el real es toEntity(PedidoCreate, Usuario).
     */
    @Override
    public Pedido toEntity(PedidoCreate createDto) {
        // En este contexto no se puede asignar usuario,
        // por eso se deja null para evitar confusión.
        return toEntity(createDto, null);
    }

    @Override
    public PedidoDto toDto(Pedido entity) {
        if (entity == null) return null;

        List<DetallePedidoDto> detalles = entity.getItems() != null
                ? entity.getItems().stream()
                .map(detallePedidoMapper::toDto)
                .toList()
                : List.of();

        return new PedidoDto(
                entity.getId(),
                entity.getFecha(),
                entity.getEstado(),
                entity.getTotal(),
                entity.getUsuario() != null ? entity.getUsuario().getId() : null,
                detalles
        );
    }
}
