package com.hms.gateway.config;
import com.hms.gateway.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public GatewayConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**").uri("lb://auth-service"))

                .route("profile-service", r -> r.path("/profile/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://profile-service"))

                .route("appointment-service", r -> r.path("/appointment/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://appointment-service"))

                .route("emergency-service", r -> r.path("/emergency/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://emergency-service"))

                .route("inpatient-service", r -> r.path("/inpatient/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://inpatient-service"))

                .route("operation-service", r -> r.path("/operation/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://operation-service"))

                .route("discharge-service", r -> r.path("/discharge/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://discharge-service"))

                .route("mortuary", r -> r.path("/mortuary/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://mortuary"))

                .route("ambulance-service", r -> r.path("/ambulance/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://ambulance-service"))

                .route("feedback-service", r -> r.path("/feedback/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://feedback-service"))

                .route(" laboratory-service", r -> r.path("/laboratory/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://laboratory-service"))

                .route(" radiology-service", r -> r.path("/radiology/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://radiology-service"))

                .route(" bloodbank-service", r -> r.path("/bloodbank/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://bloodbank-service"))

                .route(" linen-laundry-service", r -> r.path("/laundry/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://linen-laundry-service"))

                .route(" billing-service", r -> r.path("/billing/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://billing-service"))

                .route(" collection-service", r -> r.path("/collection/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://collection-service"))

                .route(" insurance-service", r -> r.path("/insurance/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://insurance-service"))

                .route(" inventory-service", r -> r.path("/inventory/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://inventory-service"))

                .route(" inventory-setup-service", r -> r.path("/inventory_setup/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://inventory-setup-service"))

                .route(" mrd-service", r -> r.path("/mrd/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://mrd-service"))

                .route(" mis-dashboard-service", r -> r.path("/misDashboard/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://mis-dashboard-service"))

                .route(" mis-report-service", r -> r.path("/misReport/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://mis-report-service"))

                .route(" software-management", r -> r.path("/softwareManagement/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://software-management"))

                .route(" hr-service", r -> r.path("/hrService/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://hr-service"))

                .route(" lab-setup-service", r -> r.path("/labSetUp/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://lab-setup-service"))

                .build();
    }
}
