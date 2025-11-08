package food.store.proyecto.service;

import food.store.proyecto.entity.dto.pedido.*;

import java.util.List;

public interface PedidoService extends BaseService<PedidoDto, PedidoCreate, Long, PedidoEdit> {
    PedidoDto save(PedidoCreate request);
    List<PedidoDto> listarPorUsuario(Long usuarioId);
}
