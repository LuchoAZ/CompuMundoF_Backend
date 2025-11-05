package food.store.proyecto.entity.dto.producto;

public record ProductoEdit(String nombre, double precio, Long categoriaId, String descripcion, String imagen, int stock, boolean disponible) {
}
