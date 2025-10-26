package food.store.proyecto.repository;

import food.store.proyecto.entity.Usuario;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

    // Busca un usuario por su mail
    Optional<Usuario> findByMail(String mail);

    // Verifica si existe un usuario con el mail proporcionado
    boolean existsByMail(String mail);

}
