package food.store.proyecto.entity.dto.producto;

public record ProductoDto(Long id, String nombre, double precio, Long categoriaId, String descripcion, String imagen, int stock, boolean disponible) {
}
