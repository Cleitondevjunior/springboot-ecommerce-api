package com.projetospringboot.projeto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetospringboot.projeto.dto.LoginDTO;
import com.projetospringboot.projeto.dto.TokenDTO;

import jakarta.validation.Valid;

/**
 * Controller responsável pela autenticação da aplicação.
 *
 * Atualmente utiliza validação mock.
 * Pode evoluir para JWT futuramente.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * Endpoint de login.
     *
     * @param dto dados de autenticação
     * @return token de acesso se válido
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) {

        //  validação mock (substituir por banco depois)
        if ("admin".equals(dto.getUsername()) && "123".equals(dto.getPassword())) {

            TokenDTO token = new TokenDTO("TOKEN_FAKE_123456", "Bearer");

            return ResponseEntity.ok(token);
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Usuário ou senha inválidos");
    }
}