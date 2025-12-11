package com.packt.cardatabase.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
                .info(new Info()
                        .title("Car Database API")
                        .version("1.0")
                        .description("REST API для управления автомобилями с JWT аутентификацией. " +
                                   "Для тестирования: 1) Выполните POST /login с credentials " +
                                   "(user/user или admin/admin) 2) Скопируйте токен из ответа " +
                                   "3) Нажмите 'Authorize' и вставьте токен"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Local Server")
                ))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                    new Components()
                        .addSecuritySchemes(securitySchemeName,
                            new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Введите JWT токен")
                        )
                );
    }
}


