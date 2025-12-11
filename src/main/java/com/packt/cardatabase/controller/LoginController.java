package com.packt.cardatabase.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cardatabase.domain.AccountCredentials;
import com.packt.cardatabase.service.JwtService;

@RestController
@Tag(name = "Authentication", description = "API для аутентификации пользователей")
public class LoginController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginController(JwtService jwtService, 
            AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Operation(
        summary = "Вход в систему",
        description = "Аутентификация пользователя и получение JWT токена. " +
                     "Используйте: user/user или admin/admin"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Успешная аутентификация. JWT токен возвращается в заголовке Authorization"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Неверные учетные данные",
            content = @Content(mediaType = "text/plain")
        )
    })
    @SecurityRequirements // Этот endpoint не требует аутентификации
    @PostMapping("/login")
    public ResponseEntity<?> getToken(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Учетные данные пользователя",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = AccountCredentials.class),
                    examples = {
                        @ExampleObject(
                            name = "User login",
                            value = "{\"username\": \"gulnaz\", \"password\": \"gulnaz\"}",
                            description = "Обычный пользователь"
                        ),
                        @ExampleObject(
                            name = "Admin login",
                            value = "{\"username\": \"admin\", \"password\": \"admin\"}",
                            description = "Администратор"
                        )
                    }
                )
            )
            @RequestBody AccountCredentials credentials) {
        UsernamePasswordAuthenticationToken creds = 
            new UsernamePasswordAuthenticationToken(
                credentials.username(), 
                credentials.password());

        Authentication auth = authenticationManager.authenticate(creds);

        // Generate token
        String jwts = jwtService.getToken(auth.getName());

        // Build response with the generated token
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
            .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
            .build();
    }
}

