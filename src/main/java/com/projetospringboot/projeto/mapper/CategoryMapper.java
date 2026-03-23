package com.projetospringboot.projeto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.projetospringboot.projeto.dto.CategoryDTO;
import com.projetospringboot.projeto.entity.Category;

public class CategoryMapper {

    // ===================== ENTITY -> DTO =====================
    public static CategoryDTO toDTO(Category entity) {
        if (entity == null) {
            return null;
        }
        return new CategoryDTO(entity.getId(), entity.getName());
    }

    // ===================== DTO -> ENTITY =====================
    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        Category entity = new Category();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    // ===================== LIST ENTITY -> LIST DTO =====================
    public static List<CategoryDTO> toDTOList(List<Category> list) {
        return list.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===================== LIST DTO -> LIST ENTITY =====================
    public static List<Category> toEntityList(List<CategoryDTO> list) {
        return list.stream()
                .map(CategoryMapper::toEntity)
                .collect(Collectors.toList());
    }
}