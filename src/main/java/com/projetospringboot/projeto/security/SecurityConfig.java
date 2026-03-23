package com.projetospringboot.projeto.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http

            // desativa CSRF
            .csrf(csrf -> csrf.disable())

            // necessário para o H2 console funcionar
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))

            // libera absolutamente tudo
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );

        return http.build();
    }
}