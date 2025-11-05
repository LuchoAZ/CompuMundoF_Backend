package food.store.proyecto.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Producto extends Base {

    private String nombre;
    private double precio;
    private String imagen;
    private String descripcion;
    private int stock;
    private boolean disponible;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
