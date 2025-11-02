package food.store.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Activa la seguridad web

public class SecurityConfig {
    @Bean // Un bean es un objeto gestionado por Spring. En este caso, es un filtro de seguridad.
    // SecurityFilterChain es un filtro de seguridad que se encarga de aplicar seguridad a una solicitud.
    // HttpSecurity es un objeto que representa la configuración de seguridad.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Un filtro de seguridad es un objeto que se encarga de aplicar seguridad a una solicitud.
        http.csrf(csrf -> csrf.disable()) // Deshabilita el csrf para que no se genere un token
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // endpoints de autenticación públicos
                        .requestMatchers("/categorias/**").permitAll() // ahora categorías públicas
                        .requestMatchers("/productos/**").permitAll() // ahora categorías públicas
                        .anyRequest().authenticated() // resto protegido
                ); // authorizeHttpRequests es un metodo que permite configurar las reglas de autorización

        return http.build(); // se devuelve un objeto de tipo HttpSecurity
    }
}
