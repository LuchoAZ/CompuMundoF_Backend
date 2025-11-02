package food.store.proyecto.controller;

import food.store.proyecto.entity.Producto;
import food.store.proyecto.entity.dto.producto.*;
import food.store.proyecto.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/productos")
public class ProductoController extends BaseController<ProductoDto, ProductoCreate,Long, ProductoEdit> {
    @Autowired
    private ProductoService productoService;

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductoEdit productoEdit) {
        try {
            return ResponseEntity.ok(productoService.update(id, productoEdit));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al actualizar el producto: " + e.getMessage());
        }
    }
}
