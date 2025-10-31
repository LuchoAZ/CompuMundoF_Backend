package food.store.proyecto.repository;

import food.store.proyecto.entity.Producto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends BaseRepository<Producto, Long> {
}
