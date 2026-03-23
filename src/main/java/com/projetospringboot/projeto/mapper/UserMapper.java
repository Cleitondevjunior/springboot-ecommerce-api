package com.projetospringboot.projeto.mapper;

import org.springframework.stereotype.Component;

import com.projetospringboot.projeto.dto.UserDTO;
import com.projetospringboot.projeto.entity.User;

@Component
public class UserMapper {

    // ===================== ENTITY -> DTO =====================

    public UserDTO toDTO(User entity) {
        if (entity == null) return null;

        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail()
        );
    }

    // ===================== DTO -> ENTITY (CREATE) =====================

    public User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        copyToEntity(dto, user);
        return user;
    }

    // ===================== UPDATE =====================

    public void copyToEntity(UserDTO dto, User entity) {
        if (dto == null || entity == null) return;

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());

        // NÃO mexe em:
        // password
        // id (controlado pelo banco)
    }
}