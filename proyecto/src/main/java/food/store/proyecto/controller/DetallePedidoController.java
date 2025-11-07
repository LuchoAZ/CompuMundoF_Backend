package food.store.proyecto.controller;
import food.store.proyecto.entity.dto.detallePedido.DetallePedidoCreate;
import food.store.proyecto.entity.dto.detallePedido.DetallePedidoDto;
import food.store.proyecto.entity.dto.detallePedido.DetallePedidoEdit;
import food.store.proyecto.service.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
@CrossOrigin(origins = "*")
public class DetallePedidoController extends BaseController<DetallePedidoDto, DetallePedidoCreate, Long, DetallePedidoEdit> {

    private final DetallePedidoService detallePedidoService;

    public DetallePedidoController(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoDto> update(@PathVariable Long id, @RequestBody DetallePedidoEdit detallePedidoEdit) {
        DetallePedidoDto updated = detallePedidoService.update(id, detallePedidoEdit);
        return ResponseEntity.ok(updated);
    }
}
