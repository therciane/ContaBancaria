package com.senai.ContaBancaria.Infrastructure.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // ROTAS PÚBLICAS
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // EXEMPLOS DE REGRAS (corrigi algumas incoerências)
                        .requestMatchers(HttpMethod.POST, "/taxas/**").hasRole("GERENTE")
                        .requestMatchers(HttpMethod.GET, "/pagamentos/**").hasAnyRole("GERENTE", "ADMIN")

                        // QUALQUER OUTRA ROTA PRECISA DE LOGIN
                        .anyRequest().authenticated()
                )

                // FILTRO JWT ANTES DO FILTRO PADRÃO
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // USERDETAILS
                .userDetailsService(usuarioDetailsService);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

