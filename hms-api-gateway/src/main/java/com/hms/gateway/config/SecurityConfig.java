package com.hms.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable()) // 🔥 Disable CSRF
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/auth/**").permitAll() // Allow login/register etc.
                        .pathMatchers("/profile/**").permitAll()
                        .pathMatchers("/appointment/**").permitAll()
                        .pathMatchers("/emergency/**").permitAll()
                        .pathMatchers("/inpatient/**").permitAll()
                        .pathMatchers("/operation/**").permitAll()
                        .pathMatchers("/discharge/**").permitAll()
                        .pathMatchers("/mortuary/**").permitAll()
                        .pathMatchers("/ambulance/**").permitAll()
                        .pathMatchers("/feedback/**").permitAll()
                        .pathMatchers("/laboratory/**").permitAll()
                        .pathMatchers("/radiology/**").permitAll()
                        .pathMatchers("/bloodbank/**").permitAll()
                        .pathMatchers("/laundry/**").permitAll()
                        .pathMatchers("/billing/**").permitAll()
                        .pathMatchers("/collection/**").permitAll()
                        .pathMatchers("/insurance/**").permitAll()
                        .pathMatchers("/inventory/**").permitAll()
                        .pathMatchers("/inventory_setup/**").permitAll()
                        .pathMatchers("/mrd/**").permitAll()
                        .pathMatchers("/misDashboard/**").permitAll()
                        .pathMatchers("/misReport/**").permitAll()
                        .pathMatchers("/softwareManagement/**").permitAll()
                        .pathMatchers("/hrService/**").permitAll()
                        .pathMatchers("/labSetUp/**").permitAll()

                        .anyExchange().authenticated()
                )
                .build();
    }
}
