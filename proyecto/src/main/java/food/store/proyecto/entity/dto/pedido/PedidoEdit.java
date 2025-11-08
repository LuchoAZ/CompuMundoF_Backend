package food.store.proyecto.entity.dto.pedido;

import food.store.proyecto.entity.dto.detallePedido.DetallePedidoEdit;
import food.store.proyecto.entity.enums.Estado;
import java.time.LocalDate;
import java.util.List;

public record PedidoEdit(
        LocalDate fecha,              // Puede mantenerse si se permite editar
        Estado estado,
        double total,
        Long usuarioId,
        List<DetallePedidoEdit> items // Si el pedido permite modificar detalles
) {}
