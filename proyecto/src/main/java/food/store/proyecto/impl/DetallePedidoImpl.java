package food.store.proyecto.impl;

import food.store.proyecto.entity.DetallePedido;
import food.store.proyecto.entity.Pedido;
import food.store.proyecto.entity.dto.detallePedido.*;
import food.store.proyecto.entity.mapper.BaseMapper;
import food.store.proyecto.entity.mapper.DetallePedidoMapper;
import food.store.proyecto.repository.BaseRepository;
import food.store.proyecto.repository.DetallePedidoRepository;
import food.store.proyecto.service.DetallePedidoService;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DetallePedidoImpl extends BaseImpl<DetallePedido, DetallePedidoDto, DetallePedidoCreate,Long , DetallePedidoEdit> implements DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;
    private final DetallePedidoMapper detallePedidoMapper;

    @Override
    public DetallePedidoDto update(Long id, DetallePedidoEdit editDto) {
        DetallePedido detalle = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado con ID: " + id));

        detalle.setCantidad(editDto.cantidad());
        detalle.setSubtotal(editDto.subtotal());
        if (editDto.productoId() != null) {
            detalle.getProducto().setId(editDto.productoId());
        }

        DetallePedido actualizado = detallePedidoRepository.save(detalle);
        return detallePedidoMapper.toDto(actualizado);
    }
}
