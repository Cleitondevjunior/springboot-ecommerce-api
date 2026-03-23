package com.projetospringboot.projeto.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.projetospringboot.projeto.dto.CategoryDTO;
import com.projetospringboot.projeto.entity.Category;

/**
 * Classe utilitária responsável por conversões entre
 * Category (Entity) e CategoryDTO.
 *
 * Centraliza a lógica de transformação de dados.
 */
public final class CategoryMapper {

    // impede instanciação
    private CategoryMapper() {
    }

    // ===================== ENTITY -> DTO =====================

    /**
     * Converte uma entidade Category para CategoryDTO.
     *
     * @param entity entidade de categoria
     * @return DTO correspondente
     */
    public static CategoryDTO toDTO(Category entity) {

        if (entity == null) {
            return null;
        }

        return new CategoryDTO(
                entity.getId(),
                entity.getName()
        );
    }

    // ===================== DTO -> ENTITY =====================

    /**
     * Converte um CategoryDTO para entidade Category.
     *
     * @param dto DTO de categoria
     * @return entidade correspondente
     */
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

    /**
     * Converte lista de entidades para lista de DTOs.
     *
     * @param list lista de categorias
     * @return lista de DTOs
     */
    public static List<CategoryDTO> toDTOList(List<Category> list) {

        if (list == null) {
            return Collections.emptyList();
        }

        return list.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===================== LIST DTO -> LIST ENTITY =====================

    /**
     * Converte lista de DTOs para lista de entidades.
     *
     * @param list lista de DTOs
     * @return lista de entidades
     */
    public static List<Category> toEntityList(List<CategoryDTO> list) {

        if (list == null) {
            return Collections.emptyList();
        }

        return list.stream()
                .map(CategoryMapper::toEntity)
                .collect(Collectors.toList());
    }
}