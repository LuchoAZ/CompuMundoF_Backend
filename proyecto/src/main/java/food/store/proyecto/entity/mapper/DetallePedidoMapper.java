package food.store.proyecto.entity.mapper;

import food.store.proyecto.entity.DetallePedido;
import food.store.proyecto.entity.Producto;
import food.store.proyecto.entity.dto.detallePedido.*;
import org.springframework.stereotype.Component;

@Component
public class DetallePedidoMapper implements BaseMapper<DetallePedido, DetallePedidoDto, DetallePedidoCreate> {
    @Override
    public DetallePedido toEntity(DetallePedidoCreate createDto) {
        if (createDto == null) return null;

        Producto producto = new Producto(); // solo se setea el id, sin traer la entidad completa
        producto.setId(createDto.productoId());

        return DetallePedido.builder()
                .cantidad(createDto.cantidad())
                .subtotal(createDto.subtotal())
                .producto(producto)
                .build();
    }

    @Override
    public DetallePedidoDto toDto(DetallePedido entity) {
        if (entity == null) return null;

        return new DetallePedidoDto(
                entity.getId(),
                entity.getCantidad(),
                entity.getSubtotal(),
                entity.getProducto() != null ? entity.getProducto().getId() : null
        );
    }

    //Metodo para editar detallePedido desde Pedido
    public DetallePedido toEntityFromEdit(DetallePedidoEdit edit) {
        if (edit == null) return null;

        DetallePedido detalle = new DetallePedido();
        detalle.setId(edit.id());
        detalle.setCantidad(edit.cantidad());
        detalle.setSubtotal(edit.subtotal());

        if (edit.productoId() != null) {
            Producto producto = new Producto();
            producto.setId(edit.productoId());
            detalle.setProducto(producto);
        }

        return detalle;
    }
}
