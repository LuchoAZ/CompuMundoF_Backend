package food.store.proyecto.service;

import food.store.proyecto.entity.dto.usuario.request.AuthResponse;
import food.store.proyecto.entity.dto.usuario.request.LoginRequest;
import food.store.proyecto.entity.dto.usuario.request.RegisterRequest;

// Interfaz que define los métodos de autenticación
// Implementada por AuthServiceImpl
public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);

}
