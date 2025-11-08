package food.store.proyecto.entity.dto.pedido;

import food.store.proyecto.entity.dto.detallePedido.DetallePedidoCreate;
import food.store.proyecto.entity.enums.Estado;
import java.util.List;

public record PedidoCreate(
        Estado estado,        // PENDIENTE, CONFIRMADO, etc.
        double total,         // Total calculado
        Long usuarioId,       // ID del usuario (del front)
        List<DetallePedidoCreate> items // Detalles del pedido
) {}
