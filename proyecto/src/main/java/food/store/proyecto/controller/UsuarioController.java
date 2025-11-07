package food.store.proyecto.controller;

import food.store.proyecto.entity.dto.usuario.UsuarioCreate;
import food.store.proyecto.entity.dto.usuario.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/usuarios")
public class UsuarioController extends BaseController<UsuarioDto, UsuarioCreate,Long,UsuarioEdit>{
}
