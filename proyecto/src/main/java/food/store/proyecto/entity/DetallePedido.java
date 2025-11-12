package food.store.proyecto.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class DetallePedido extends Base {

    private int cantidad;
    private double subtotal;
    private boolean eliminado = false; // opcional, útil para borrado lógico

    // 🔸 Cada detalle pertenece a un producto
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // 🔸 Cada detalle pertenece a un pedido (relación de composición)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;
}
