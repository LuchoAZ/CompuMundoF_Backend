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
public class Producto extends Base {

    private String nombre;
    private double precio;

}
