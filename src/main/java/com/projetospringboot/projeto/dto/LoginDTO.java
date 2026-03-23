package com.projetospringboot.projeto.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO responsável por receber os dados de autenticação.
 *
 * Utilizado no endpoint /auth/login.
 */
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Nome de usuário (login).
     */
    @NotBlank(message = "O usuário é obrigatório")
    private String username;

    /**
     * Senha do usuário.
     */
    @NotBlank(message = "A senha é obrigatória")
    private String password;

    // ===================== CONSTRUTORES =====================

    public LoginDTO() {
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ===================== GETTERS =====================

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // ===================== SETTERS =====================

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}