package food.store.proyecto.impl;

import food.store.proyecto.entity.Usuario;
import food.store.proyecto.entity.dto.usuario.UsuarioCreate;
import food.store.proyecto.entity.dto.usuario.UsuarioDto;
import food.store.proyecto.service.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioImpl extends BaseImpl<Usuario, UsuarioDto, UsuarioCreate,Long> implements UsuarioService {
}
