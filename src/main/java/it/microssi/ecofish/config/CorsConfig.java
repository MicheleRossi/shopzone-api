package it.microssi.ecofish.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Configura il CORS per tutti gli endpoint
                        .allowedOrigins("http://localhost:5173") // Permetti richieste da questa origine
                        .allowedMethods("*") // Permetti tutti i metodi HTTP (GET, POST, PUT, DELETE, ecc.)
                        .allowedHeaders("*") // Permetti tutti gli header
                        .allowCredentials(true); // Permetti l'invio di cookie e header di autenticazione
            }
        };
    }
}