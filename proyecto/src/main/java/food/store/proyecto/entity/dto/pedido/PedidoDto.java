package food.store.proyecto.entity.dto.pedido;

import food.store.proyecto.entity.dto.detallePedido.DetallePedidoDto;
import food.store.proyecto.entity.enums.Estado;

import java.time.LocalDate;
import java.util.List;

public record PedidoDto(Long id, LocalDate fecha, Estado estado, double total, List<DetallePedidoDto> detalles) {
}
