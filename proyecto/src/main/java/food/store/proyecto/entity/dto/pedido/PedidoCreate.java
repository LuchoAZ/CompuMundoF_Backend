package food.store.proyecto.entity.dto.pedido;

import food.store.proyecto.entity.enums.Estado;

import java.time.LocalDate;

public record PedidoCreate(LocalDate fecha, Estado estado, double total) {
}
