package com.senai.ContaBancaria.Infrastructure.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bankOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Conta Bancária")
                        .description("Gestão do Pandora Bank")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipe Pandora Bank")
                                .email("suporte@pandorabank.com")
                        )
                )
                // Aplica segurança JWT ao Swagger
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", createAPIKeyScheme())
                );
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}

