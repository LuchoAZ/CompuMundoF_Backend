package food.store.proyecto.config;

// Clase que representa la utilidad para generar un hash SHA-256
// Se utiliza para encriptar la contraseña del usuario
// el hash es un valor unico que se genera a partir de la contraseña.
// sha-256 es un algoritmo de hash que genera un hash de 256 bits

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Util {
    public static String hash(String input) {  // Genera un hash SHA-256 a partir de una cadena de texto
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // Obtiene una instancia del algoritmo SHA-256
            //MessageDigest es una clase que representa un algoritmo de hash
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8)); // Genera el hash
            //Utilizamos un array de bytes para almacenar el hash
            //digest es un array de bytes que representa el hash
            //.digest() genera el hash a partir de la cadena de texto
            return bytesToHex(digest); // Convierte el array de bytes a una cadena hexadecimal
            //Una cadena hexadecimal es una cadena de texto que representa un valor en base 16

        } catch (NoSuchAlgorithmException e) { //NoSuchAlgorithmException es una excepción que se lanza cuando no se encuentra el algoritmo de hash
// No debería ocurrir: SHA-256 está disponible en la JVM estándar
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }


    // Convierte un array de bytes a una cadena hexadecimal (00..ff)
    private static String bytesToHex(byte[] bytes) { // bytes es un array de bytes que representa el hash
        StringBuilder sb = new StringBuilder(); //sb es un StringBuilder que se utiliza para construir la cadena de texto
        for (byte b : bytes) { // Recorre el array de bytes
            sb.append(String.format("%02x", b)); // Convierte el byte a una cadena hexadecimal
        }
        return sb.toString(); // Devuelve la cadena de texto
    }

}
