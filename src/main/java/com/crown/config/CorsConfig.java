package com.crown.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Permitir credenciales (cookies, auth headers)
        config.setAllowCredentials(true);
        
        // ⚠️ IMPORTANTE: Aquí pones la URL EXACTA de tu frontend en Vercel sin la barra / al final
        config.addAllowedOrigin("https://fronten-cocolate.vercel.app"); 
        
        // Permitir todos los headers y métodos HTTP (GET, POST, etc.)
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
