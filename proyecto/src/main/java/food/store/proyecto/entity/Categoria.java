package food.store.proyecto.entity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Categoria extends Base{
    private String nombre;
}
