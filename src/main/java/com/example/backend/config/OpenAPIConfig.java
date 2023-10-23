package com.example.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class OpenAPIConfig {

    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Backend REST API")
                .description("Description")
                .version("1.0")
                .contact(new Contact()
                        .name("Name Surname")
                        .email("www.example.com"))
                .license(new License()
                        .name("License of API")
                        .url("API license URL")));
    }
}
