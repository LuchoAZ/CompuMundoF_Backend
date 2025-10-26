package food.store.proyecto.config;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;


@Component //hace que JwtUtil sea un bean de Spring
public class JwtUtil {

    // Clave ejemplo para fines académicos. Cambiar por variable de entorno en producción.
    // Debe ser una cadena de 256 bits (32 caracteres), aleatoria y segura
    // Se recomienda usar una variable de entorno para almacenar la clave (una variable de entorno es una variable que se almacena en el sistema operativo)

    //Carga las variables de entorno desde el archivo .env
    private static final Dotenv dotenv = Dotenv.load();

    //Obtiene el valor de la variable de entorno JWT_SECRET
    private static final String SECRET_KEY = dotenv.get("JWT_SECRET");

    // Tiempo de expiración del token en milisegundos (ej. 1 hora)
    private static final long EXPIRATION_MS = 1000L * 60 * 60;


    // Construimos la Key para firmar/verificar. Keys.hmacShaKeyFor valida el tamaño.
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());


    /**
     Genera un JWT
     (Codigo Token que el servidor genera cuando el usuario inicia sesion correctamente,
     el cual contiene información del usuario, está firmado digitalmente para que no se pueda falsificar,
     y tiene fecha de expiracion)
     con subject=mail y un claim adicional "rol".
     El token incluye issuedAt y expiration.
     */
    public String generateToken(String mail, String rol) { //genera un token para un usuario que inicia sesion o se registra
       //Jwts es una clase que representa un token JWT
        return Jwts.builder() //crea un builder para construir el token
                .setSubject(mail) // subject es el principal (aquí usamos mail)
                .claim("rol", rol) // claim personalizado con el rol.
                .setIssuedAt(new Date()) // fecha de emision
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)) // fecha de expiracion
                //SignatureAlgorithm es un enum que representa el algoritmo de firma
                .signWith(key, SignatureAlgorithm.HS256) // firma el token con la clave y el algoritmo
                .compact(); // compacta el token
    }


    // Valida el token (firma correcta y no expirado)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
    // Puede ser ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, etc.
            return false; //Si alguien intenta utilizar un token expirado o modificado
        }
    }


    // Extrae el email (subject) del token
    public String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    // Extrae el rol desde el claim "rol" para saber si el usuario es ADMIN o CLIENTE
    public String extractRole(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
        Object rol = claims.get("rol");
        return rol != null ? rol.toString() : null;
    }

}
