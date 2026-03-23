package com.projetospringboot.projeto.mapper;

import org.springframework.stereotype.Component;

import com.projetospringboot.projeto.dto.UserCreateDTO;
import com.projetospringboot.projeto.dto.UserDTO;
import com.projetospringboot.projeto.dto.UserUpdateDTO;
import com.projetospringboot.projeto.entity.User;

/**
 * Classe responsável por conversões entre:
 * - Entity <-> DTO
 *
 * Centraliza a lógica de transformação de dados da aplicação.
 */
@Component
public class UserMapper {

    // ===================== ENTITY -> DTO =====================

    /**
     * Converte uma entidade User para UserDTO.
     *
     * Usado nas respostas da API para evitar exposição de dados sensíveis.
     *
     * @param entity entidade User
     * @return UserDTO
     */
    public UserDTO toDTO(User entity) {

        if (entity == null) {
            return null;
        }

        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail()
        );
    }

    // ===================== CREATE DTO -> ENTITY =====================

    /**
     * Converte UserCreateDTO para entidade User.
     *
     * Utilizado no cadastro de novos usuários.
     *
     * @param dto dados de criação
     * @return entidade User pronta para persistência
     */
    public User toEntity(UserCreateDTO dto) {

        if (dto == null) {
            return null;
        }

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(dto.getPassword());

        return user;
    }

    // ===================== UPDATE =====================

    /**
     * Atualiza uma entidade existente com base no UserUpdateDTO.
     *
     * Apenas campos não nulos são atualizados (update parcial).
     *
     * @param entity entidade existente no banco
     * @param dto dados de atualização
     */
    public void updateEntity(User entity, UserUpdateDTO dto) {

        if (entity == null || dto == null) {
            return;
        }

        // Atualiza nome se informado
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }

        // Atualiza email se informado
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }

        // Atualiza telefone se informado
        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }

        /**
         * Regra de negócio:
         * Senha só é atualizada se vier preenchida e válida.
         */
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            entity.setPassword(dto.getPassword());
        }
    }
}