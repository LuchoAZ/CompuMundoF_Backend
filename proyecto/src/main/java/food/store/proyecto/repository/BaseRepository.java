package food.store.proyecto.repository;

import food.store.proyecto.entity.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository <E extends Base, ID> extends JpaRepository <E, ID> {
    List<E> findAllByEliminadoFalse();

    public default List<E> findAll() {return findAllByEliminadoFalse();}
    public default void deleteById(ID id){
        E entity = getById(id);
        entity.setEliminado(true);
        save(entity);
    };

    public Optional<E> findByIdAndEliminadoFalse(ID id);

    public default E getById(ID id){
        return findByIdAndEliminadoFalse(id).orElseThrow(() -> new NullPointerException("No se encontro el elemento"));
    }
}
