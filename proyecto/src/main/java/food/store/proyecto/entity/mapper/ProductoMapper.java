package food.store.proyecto.entity.mapper;

import food.store.proyecto.entity.Producto;
import food.store.proyecto.entity.dto.producto.*;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper implements BaseMapper<Producto, ProductoDto, ProductoCreate> {

    @Override
    public Producto toEntity(ProductoCreate productoCreate) {
        if (productoCreate == null) return null;
        return Producto.builder()
                .nombre(productoCreate.nombre())
                .precio(productoCreate.precio())
                .descripcion(productoCreate.descripcion())
                .imagen(productoCreate.imagen())
                .stock(productoCreate.stock())
                .disponible(productoCreate.disponible())
                //.categoria(productoCreate.categoriaId().getId())
                .build();
    }

    @Override
    public ProductoDto toDto(Producto producto) {
        if (producto == null) return null;
        return new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getCategoria().getId(), //se usa getId en lugar de getCategoriaId porque es un objeto
                producto.getDescripcion(),
                producto.getImagen(),
                producto.getStock(),
                producto.isDisponible()  //se usa isDisponible en lugar de getDisponible porque es un booleano
        );
    }
}
