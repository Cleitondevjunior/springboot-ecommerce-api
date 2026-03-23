package com.projetospringboot.projeto.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO responsável por transportar os dados do usuário entre
 * a camada de controller e a camada de service.
 *
 * Ele evita expor diretamente a entidade User na API
 * e permite aplicar validações de entrada.
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador do usuário.
     * Normalmente usado em respostas da API.
     */
    private Long id;

    /**
     * Nome do usuário.
     * Não pode ser nulo, vazio ou conter apenas espaços.
     * Também possui limite mínimo e máximo de caracteres.
     */
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String name;

    /**
     * Email do usuário.
     * Não pode ser vazio e deve estar em formato válido.
     */
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Informe um email válido")
    @Size(max = 150, message = "O email deve ter no máximo 150 caracteres")
    private String email;

    /**
     * Construtor padrão.
     * Obrigatório para serialização e desserialização do JSON.
     */
    public UserDTO() {
    }

    /**
     * Construtor com parâmetros.
     *
     * @param id identificador do usuário
     * @param name nome do usuário
     * @param email email do usuário
     */
    public UserDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Retorna o ID do usuário.
     *
     * @return id do usuário
     */
    public Long getId() {
        return id;
    }

    /**
     * Retorna o nome do usuário.
     *
     * @return nome do usuário
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna o email do usuário.
     *
     * @return email do usuário
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o ID do usuário.
     *
     * @param id identificador do usuário
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Define o nome do usuário.
     *
     * @param name nome do usuário
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Define o email do usuário.
     *
     * @param email email do usuário
     */
    public void setEmail(String email) {
        this.email = email;
    }
}