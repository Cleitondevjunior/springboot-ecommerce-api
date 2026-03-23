package com.projetospringboot.projeto.mapper;

import org.springframework.stereotype.Component;

import com.projetospringboot.projeto.dto.UserCreateDTO;
import com.projetospringboot.projeto.dto.UserDTO;
import com.projetospringboot.projeto.dto.UserUpdateDTO;
import com.projetospringboot.projeto.entity.User;

/**
 * Classe responsável por converter:
 * Entity <-> DTO
 * 
 * Centraliza a lógica de transformação de dados.
 */
@Component
public class UserMapper {

    // ===================== ENTITY -> DTO =====================

    /**
     * Converte Entity para DTO (resposta da API).
     */
    public UserDTO toDTO(User entity) {

        if (entity == null) return null;

        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail()
        );
    }

    // ===================== CREATE DTO -> ENTITY =====================

    /**
     * Converte UserCreateDTO para Entity.
     * Usado no cadastro de usuário.
     */
    public User toEntity(UserCreateDTO dto) {

        if (dto == null) return null;

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
     * Apenas campos não nulos são atualizados.
     */
    public void updateEntity(User entity, UserUpdateDTO dto) {

        if (dto == null || entity == null) return;

        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }

        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }

        /**
         * Regra importante:
         * Só atualiza senha se vier preenchida.
         */
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            entity.setPassword(dto.getPassword());
        }
    }
}