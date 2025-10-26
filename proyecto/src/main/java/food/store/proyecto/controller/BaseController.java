package food.store.proyecto.controller;

import food.store.proyecto.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseController <D, DC, ID>{

    @Autowired
    BaseService<D,DC,ID> baseService;

    @PostMapping
    public ResponseEntity<?> save (@RequestBody DC dc){
        try{
            return ResponseEntity.ok(baseService.save(dc));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        try{
            return ResponseEntity.ok(baseService.findAll());
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Ocurrio un error del tipo " + e.getClass() + " \nMensaje: " + e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById (@PathVariable ID id){
        try{
            return ResponseEntity.ok(baseService.findById(id));
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Ocurrio un error del tipo " + e.getClass() + " \nMensaje: " + e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById (@PathVariable ID id){
        try{
            baseService.deleteById(id);
            return ResponseEntity.ok("Elemento con id " + id + " eliminado correctamente");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Ocurrio un error del tipo " + e.getClass() + " \nMensaje: " + e.getMessage());
        }
    }
}
