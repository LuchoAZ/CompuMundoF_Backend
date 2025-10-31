package food.store.proyecto.entity.mapper;

import food.store.proyecto.entity.Categoria;
import food.store.proyecto.entity.dto.categoria.*;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper implements BaseMapper<Categoria, CategoriaDto, CategoriaCreate> {
    @Override
    public Categoria toEntity(CategoriaCreate categoriaCreate) {
        if (categoriaCreate == null) return null;
        return Categoria.builder()
                .nombre(categoriaCreate.nombre())
                .build();
    }

    @Override
    public CategoriaDto toDto(Categoria categoria) {
        if (categoria == null) return null;
        return new CategoriaDto(
                categoria.getId(),
                categoria.getNombre()
        );
    }
}
