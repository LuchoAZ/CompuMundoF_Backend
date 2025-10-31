package food.store.proyecto.impl;
import food.store.proyecto.entity.dto.producto.*;
import food.store.proyecto.entity.Producto;
import food.store.proyecto.entity.mapper.ProductoMapper;
import food.store.proyecto.repository.ProductoRepository;
import food.store.proyecto.service.ProductoService;
import org.springframework.stereotype.Service;

@Service
public class ProductoImpl extends BaseImpl<Producto, ProductoDto, ProductoCreate,Long,ProductoEdit> implements ProductoService {
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public ProductoImpl(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.baseRepository = productoRepository;
        this.baseMapper = productoMapper;
    }

    @Override
    public ProductoDto update(Long id, ProductoEdit editDto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        producto.setNombre(editDto.nombre());
        producto.setPrecio(editDto.precio());

        Producto actualizado = productoRepository.save(producto);
        return productoMapper.toDto(actualizado);
    }
}
