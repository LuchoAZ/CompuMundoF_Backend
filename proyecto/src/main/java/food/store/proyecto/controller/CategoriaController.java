package food.store.proyecto.controller;

import food.store.proyecto.entity.dto.categoria.CategoriaCreate;
import food.store.proyecto.entity.dto.categoria.CategoriaDto;
import food.store.proyecto.entity.dto.categoria.CategoriaEdit;
import food.store.proyecto.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/categorias")
public class CategoriaController extends BaseController<CategoriaDto, CategoriaCreate,Long, CategoriaEdit> {

    @Autowired
    private CategoriaService categoriaService;

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CategoriaEdit categoriaEdit) {
        try {
            return ResponseEntity.ok(categoriaService.update(id, categoriaEdit));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al actualizar la categoría: " + e.getMessage());
        }
    }
}
