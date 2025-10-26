package food.store.proyecto.service;

import food.store.proyecto.config.JwtUtil;
import food.store.proyecto.config.Sha256Util;
import food.store.proyecto.entity.Usuario;
import food.store.proyecto.entity.dto.usuario.request.AuthResponse;
import food.store.proyecto.entity.dto.usuario.request.LoginRequest;
import food.store.proyecto.entity.dto.usuario.request.RegisterRequest;
import food.store.proyecto.entity.enums.Rol;
import food.store.proyecto.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service //Indica que es un servicio
@AllArgsConstructor //Inyecta las dependencias

public class AuthServiceImpl implements AuthService {
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    /**
     Registra un nuevo usuario.
     Verifica unicidad de email.
     Hashea la contraseña con SHA-256 antes de persistir.
     Genera un JWT y lo devuelve en la respuesta.
     */
    @Override
    @Transactional // Indica que el método debe ser ejecutado dentro de una transacción
    public AuthResponse register(RegisterRequest request) {
// Validaciones básicas (puedes ampliar con validadores más estrictos)
        if (request.mail() == null || request.mail().isBlank()) { // en caso de que el campo mail sea nulo o vacio se arroja un error
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mail requerido");
        }


        if (usuarioRepository.existsByMail(request.mail())) { // en caso de que el email ya exista se arroja un error
        // Retornamos 400 BAD REQUEST si el email ya está registrado
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El mail ya está registrado");
        }


        // Hasheamos la contraseña con SHA-256
        String hashedPassword = Sha256Util.hash(request.password()); // encriptamos la contraseña


        // Convertimos el rol enviado desde el frontend
        Rol rolUsuario;
        if (request.rol() == null || request.rol().isBlank()) {
            rolUsuario = Rol.USUARIO; // Valor por defecto en caso de que el rol sea nulo
        } else {
            switch (request.rol().toUpperCase()) { // Convertimos a mayúscula para coincidir con el enum
                case "ADMIN":
                    rolUsuario = Rol.ADMIN;
                    break;
                case "USUARIO":
                    rolUsuario = Rol.USUARIO;
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol inválido"); //En caso de que se cree un rol nuevo en el front y éste no es validado en el backend
            }

        }
            // Construimos la entidad User (no guardamos la contraseña en claro)
            Usuario user = Usuario.builder()
                    .nombre(request.nombre())
                    .apellido(request.apellido())
                    .mail(request.mail())
                    .contraseña(hashedPassword)
                    .rol(rolUsuario)
                    .build();


            // Persistimos el usuario
            Usuario saved = usuarioRepository.save(user);


            // Generamos un JWT para el usuario recién creado
            String token = jwtUtil.generateToken(saved.getMail(), saved.getRol().name());


            return new AuthResponse(saved.getId(), saved.getMail(), saved.getRol().name(), token);
        }


        //-------------------------------------------------------------------------------------------------------------

        /**
         Autentica un usuario: compara hashes y genera token si es correcto.
         */
        @Override
        public AuthResponse login (LoginRequest request){ // En caso de que el email sea nulo o vacio se arroja un error
            if (request.mail() == null || request.mail().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email requerido");
            }


// Buscamos el usuario por email
            Usuario user = usuarioRepository.findByMail(request.mail()) // buscamos usuario por email
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));


// Calculamos el hash de la contraseña enviada y lo comparamos con el almacenado
            String hashedAttempt = Sha256Util.hash(request.password());
            if (!hashedAttempt.equals(user.getContraseña())) {
// No usamos detalles excesivos en el mensaje por seguridad
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
            }


// Si las credenciales son válidas generamos y devolvemos el token
            String token = jwtUtil.generateToken(user.getMail(), user.getRol().name());
            return new AuthResponse(user.getId(), user.getMail(), user.getRol().name(), token);
        }


    }
