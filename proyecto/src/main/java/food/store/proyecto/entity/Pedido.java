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
    private LocalDate fecha;//posible migracion a localdatetime para obtener hora
    private double total;
    @Enumerated(EnumType.STRING)
    private Estado estado;

    // 🔸 DUEÑO de la relación: guarda usuario_id en la tabla pedido
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Composición: Pedido contiene DetallePedido (unidireccional)
    // 🔸 Composición: pedido -> detalle (cascade + orphanRemoval)
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> items = new ArrayList<>();
}
