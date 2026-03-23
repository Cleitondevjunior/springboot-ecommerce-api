package com.projetospringboot.projeto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetospringboot.projeto.dto.LoginDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {

        //  validação simples (mock)
        if ("admin".equals(dto.getUsername()) && "123".equals(dto.getPassword())) {
            return ResponseEntity.ok("TOKEN_FAKE_123456");
        }

        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }
}