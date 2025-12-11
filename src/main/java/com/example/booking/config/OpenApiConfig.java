package com.example.booking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Medical Appointment Booking System API")
                        .version("1.0.0")
                        .description("REST API for managing medical appointments between doctors and patients. " +
                                "This system allows Super Admins to manage users and doctors, " +
                                "and provides comprehensive appointment booking functionality.")
                        .contact(new Contact()
                                .name("Panadol Platform Team")
                                .email("support@panadol-platform.com")
                                .url("https://github.com/mhdirfanpn/panadol-platform"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.panadol-platform.com")
                                .description("Production Server")
                ));
    }
}

