package food.store.proyecto.entity;

import food.store.proyecto.entity.enums.Rol;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Usuario extends Base{

    private String nombre;
    private String apellido;
    @Column(unique = true, nullable = false) // Indica que el mail debe ser único y no puede ser nulo (obligatorio)
    private String mail;
    private int celular;
    @Column(nullable = false) // Indica que la contraseña debe ser obligatoria
    private String contraseña;

    @Enumerated(EnumType.STRING) // se determina el rol como String
    private Rol rol;

//    @OneToMany //(cascade = CascadeType.ALL)
//    @JoinColumn(name = "usuario_id") // FK en la tabla pedido
//    private List<Pedido> pedidos;

}
