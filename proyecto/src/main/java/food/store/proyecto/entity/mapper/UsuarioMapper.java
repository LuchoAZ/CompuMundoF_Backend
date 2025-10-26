package food.store.proyecto.entity.mapper;

import food.store.proyecto.entity.Usuario;
import food.store.proyecto.entity.dto.usuario.UsuarioCreate;
import food.store.proyecto.entity.dto.usuario.UsuarioDto;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper implements BaseMapper<Usuario, UsuarioDto, UsuarioCreate> {
    @Override
    public Usuario toEntity(UsuarioCreate usuarioCreate) {
        Usuario u = new Usuario();
        u.setNombre(usuarioCreate.nombre());
        u.setApellido(usuarioCreate.apellido());
        u.setMail(usuarioCreate.mail());
        u.setContraseña(usuarioCreate.contraseña());
        u.setCelular(usuarioCreate.celular());
        u.setRol(usuarioCreate.rol());

        return u;
    }

    @Override
    public UsuarioDto toDto(Usuario u) {
        return new UsuarioDto(u.getId(), u.getNombre(), u.getApellido(), u.getMail(),
                u.getContraseña(), u.getCelular(), u.getRol());
    }
}
