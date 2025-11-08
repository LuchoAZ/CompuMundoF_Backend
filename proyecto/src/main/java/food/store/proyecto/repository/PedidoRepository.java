package food.store.proyecto.repository;

import food.store.proyecto.entity.Pedido;

import java.util.List;

public interface PedidoRepository extends BaseRepository<Pedido, Long> {
    List<Pedido> findByUsuario_Id(Long usuarioId);
}
