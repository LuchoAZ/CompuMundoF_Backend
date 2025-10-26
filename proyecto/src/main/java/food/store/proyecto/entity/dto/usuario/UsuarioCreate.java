package food.store.proyecto.entity.dto.usuario;

import food.store.proyecto.entity.enums.Rol;

public record UsuarioCreate(String nombre, String apellido, String mail, String contraseña, int celular, Rol rol) {
}
