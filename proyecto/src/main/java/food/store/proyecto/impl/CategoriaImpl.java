package food.store.proyecto.impl;
import food.store.proyecto.entity.Categoria;
import food.store.proyecto.entity.dto.categoria.*;
import food.store.proyecto.entity.mapper.CategoriaMapper;
import food.store.proyecto.repository.CategoriaRepository;
import food.store.proyecto.service.CategoriaService;
import org.springframework.stereotype.Service;

@Service
public class CategoriaImpl extends BaseImpl<Categoria, CategoriaDto, CategoriaCreate,Long,CategoriaEdit> implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaImpl(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
        this.baseRepository = categoriaRepository;
        this.baseMapper = categoriaMapper;
    }

    @Override
    public CategoriaDto update(Long id, CategoriaEdit editDto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

        categoria.setNombre(editDto.nombre());
        categoria.setImagen(editDto.imagen());
        categoria.setDescripcion(editDto.descripcion());

        Categoria actualizada = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(actualizada);
    }
}
