package food.store.proyecto.controller;

import food.store.proyecto.entity.dto.usuario.request.LoginRequest;
import food.store.proyecto.entity.dto.usuario.request.RegisterRequest;
import food.store.proyecto.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import food.store.proyecto.entity.dto.usuario.request.AuthResponse;
@RestController // Indica que es un controlador
@RequestMapping("/api/auth") // RequestMapping sirve para mapear las peticiones. "/api/auth" es la direccion base para las peticiones
@CrossOrigin(origins = "*") // CrossOrigin sirve para permitir peticiones de diferentes orígenes
@AllArgsConstructor

public class AuthController {

    @Autowired // Inyecta el servicio de autenticación
    private final AuthService authService;


    // Endpoint para registrar un usuario. Recibe RegisterRequest (record) en el body.
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) { // Recibe RegisterRequest (record) en el body y devuelve AuthResponse
       // Invoca el servicio de autenticación para registrar al usuario
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(201).body(response);
    }


    // Endpoint para iniciar sesión. Devuelve AuthResponse con token.
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}
