package com.hms.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class GatewayCorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow only your React app (safer than "*")
        config.setAllowedOrigins(Arrays.asList("http://27.7.154.118:3000","http:localhost:3000","http://223.181.119.158:3000"));


        // Allow all methods (GET, POST, PUT, DELETE, OPTIONS)
        config.addAllowedMethod("*");

        // Allow all headers
        config.addAllowedHeader("*");

        // Very important: allow cookies/authorization headers (JWT, sessionId, etc.)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
