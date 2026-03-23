package com.projetospringboot.projeto.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * DTO utilizado para atualização de usuário.
 * 
 * Diferente do CreateDTO:
 * - Campos são opcionais
 * - Permite atualização parcial
 */
public class UserUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Nome do usuário.
     * Opcional, mas validado se informado.
     */
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String name;

    /**
     * Email do usuário.
     * Opcional, mas validado se informado.
     */
    @Email(message = "Informe um email válido")
    @Size(max = 150, message = "O email deve ter no máximo 150 caracteres")
    private String email;

    /**
     * Telefone do usuário.
     * Opcional.
     */
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String phone;

    /**
     * Senha do usuário.
     * Opcional, mas com validação mínima se informada.
     */
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;

    /**
     * Construtor padrão.
     */
    public UserUpdateDTO() {
    }

    /**
     * Construtor com parâmetros.
     */
    public UserUpdateDTO(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // ===================== GETTERS =====================

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    // ===================== SETTERS =====================

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}