package food.store.proyecto.controller;

import food.store.proyecto.entity.dto.pedido.PedidoCreate;
import food.store.proyecto.entity.dto.pedido.PedidoDto;
import food.store.proyecto.entity.dto.pedido.PedidoEdit;
import food.store.proyecto.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController extends BaseController<PedidoDto, PedidoCreate, Long, PedidoEdit> {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // 🔸 Actualizar un pedido existente
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDto> update(@PathVariable Long id, @RequestBody PedidoEdit pedidoEdit) {
        PedidoDto updated = pedidoService.update(id, pedidoEdit);
        return ResponseEntity.ok(updated);
    }

    // 🔹 Listar todos los pedidos de un usuario específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoDto>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<PedidoDto> pedidos = pedidoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(pedidos);
    }
}
