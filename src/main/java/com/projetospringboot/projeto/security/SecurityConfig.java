package com.projetospringboot.projeto.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe responsável pela configuração de segurança da aplicação.
 *
 * Define:
 * - rotas públicas
 * - rotas protegidas
 * - configurações básicas do Spring Security
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http

            // ===================== CSRF =====================
            // desativado para API REST (não usa sessão)
            .csrf(csrf -> csrf.disable())

            // ===================== H2 CONSOLE =====================
            // permite abrir o console do banco H2 no navegador
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))

            // ===================== AUTORIZAÇÃO =====================
            .authorizeHttpRequests(auth -> auth

                //  ROTAS PÚBLICAS
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()

                //  GETs públicos (opcional)
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/orders/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                
                //  resto precisa autenticação
                .anyRequest().authenticated()
            );

        return http.build();
    }
}