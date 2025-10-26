package food.store.proyecto.entity;
import jakarta.persistence.Entity;
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
}
