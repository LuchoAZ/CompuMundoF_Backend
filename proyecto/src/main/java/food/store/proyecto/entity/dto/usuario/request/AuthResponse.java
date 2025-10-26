package food.store.proyecto.entity.dto.usuario.request;

public record AuthResponse(Long id, String mail, String rol, String token) {
}
