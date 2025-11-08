package food.store.proyecto.entity.dto.detallePedido;

public record DetallePedidoCreate(int cantidad, double subtotal, Long productoId, Long pedidoId) {
}
