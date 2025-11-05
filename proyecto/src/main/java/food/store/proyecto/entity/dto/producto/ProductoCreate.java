package food.store.proyecto.entity.dto.producto;

public record ProductoCreate(String nombre, double precio, Long categoriaId, String imagen, String descripcion, int stock, boolean disponible) {
}
