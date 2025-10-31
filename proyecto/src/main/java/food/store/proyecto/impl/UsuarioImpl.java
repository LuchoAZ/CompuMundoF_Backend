package food.store.proyecto.impl;

import food.store.proyecto.entity.Usuario;
import food.store.proyecto.entity.dto.usuario.*;
import food.store.proyecto.service.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioImpl extends BaseImpl<Usuario, UsuarioDto, UsuarioCreate,Long,UsuarioEdit> implements UsuarioService {
    @Override
    public UsuarioDto findById(Long aLong) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public UsuarioDto update(Long aLong, UsuarioEdit usuarioEdit) {
        return null;
    }


}
