package food.store.proyecto.entity;

import food.store.proyecto.entity.enums.Estado;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Pedido extends Base {
    private LocalDate fecha;
    private double total;
    @Enumerated(EnumType.STRING)
    private Estado estado;

//    @OneToMany
//    @JoinColumn(name = "pedido_id") // FK en detalle_pedido
//    private List<DetallePedido> detalles;
}
