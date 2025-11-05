package food.store.proyecto.impl;
import food.store.proyecto.entity.dto.producto.*;
import food.store.proyecto.entity.Producto;
import food.store.proyecto.entity.Categoria;
import food.store.proyecto.entity.mapper.ProductoMapper;
import food.store.proyecto.repository.CategoriaRepository;
import food.store.proyecto.repository.ProductoRepository;
import food.store.proyecto.service.ProductoService;
import org.springframework.stereotype.Service;

@Service
public class ProductoImpl extends BaseImpl<Producto, ProductoDto, ProductoCreate,Long,ProductoEdit> implements ProductoService {
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final CategoriaRepository categoriaRepository;

    public ProductoImpl(ProductoRepository productoRepository, ProductoMapper productoMapper, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.baseRepository = productoRepository;
        this.baseMapper = productoMapper;
        this.categoriaRepository = categoriaRepository;
    }
    @Override
    public ProductoDto update(Long id, ProductoEdit editDto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        producto.setNombre(editDto.nombre());
        producto.setPrecio(editDto.precio());
        //Chequear cambio de categoria dentro de la página
        //producto.setCategoria(editDto.categoriaId());
        producto.setDescripcion(editDto.descripcion());
        producto.setImagen(editDto.imagen());
        producto.setStock(editDto.stock());
        producto.setDisponible(editDto.disponible());

        if (editDto.categoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(editDto.categoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(categoria);
        }
        Producto actualizado = productoRepository.save(producto);
        return productoMapper.toDto(actualizado);
    }
    @Override
    public ProductoDto save(ProductoCreate createDto) {
        Producto producto = productoMapper.toEntity(createDto);

        // Obtenemos la categoría existente
        Long categoriaId = createDto.categoriaId();
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + categoriaId));

        producto.setCategoria(categoria);

        Producto guardado = productoRepository.save(producto);
        return productoMapper.toDto(guardado);
    }
}
