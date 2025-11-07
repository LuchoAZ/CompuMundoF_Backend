package food.store.proyecto.entity;

import food.store.proyecto.entity.enums.Estado;

import java.time.LocalDate;
import java.util.ArrayList;
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

    // Composición: Pedido contiene DetallePedido (unidireccional)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id", nullable = false) // FK en DetallePedido
    @Builder.Default
    private List<DetallePedido> detalles = new ArrayList<>();
}
