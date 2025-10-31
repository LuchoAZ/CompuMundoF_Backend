package food.store.proyecto.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class DetallePedido extends Base {
    private int cantidad;
    private double subtotal;


    // Relación muchos a uno: muchos detalles pueden compartir un producto, pero cada detalle solo puede tener asociado un producto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false) // FK en la tabla detalle_pedido
    private Producto producto;

}
