package food.store.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity // Activa la seguridad web

public class SecurityConfig {
    @Bean // Un bean es un objeto gestionado por Spring. En este caso, es un filtro de seguridad.
    // SecurityFilterChain es un filtro de seguridad que se encarga de aplicar seguridad a una solicitud.
    // HttpSecurity es un objeto que representa la configuración de seguridad.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Un filtro de seguridad es un objeto que se encarga de aplicar seguridad a una solicitud.
        http.csrf(csrf -> csrf.disable()) // Deshabilita el csrf para que no se genere un token
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // endpoints de autenticación públicos
                        .anyRequest().authenticated() // resto protegido
                ); // authorizeHttpRequests es un metodo que permite configurar las reglas de autorización

        return http.build(); // se devuelve un objeto de tipo HttpSecurity
    }
    // ✅ Configuración CORS global
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // tu front Vite
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
