package food.store.proyecto.entity.dto.pedido;

import food.store.proyecto.entity.dto.detallePedido.DetallePedidoCreate;
import food.store.proyecto.entity.enums.Estado;

import java.time.LocalDate;
import java.util.List;

public record PedidoCreate(LocalDate fecha, Estado estado, double total, List<DetallePedidoCreate> detalles) {
}
