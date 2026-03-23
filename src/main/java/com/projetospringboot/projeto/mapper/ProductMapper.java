package com.projetospringboot.projeto.mapper;

import org.springframework.stereotype.Component;

import com.projetospringboot.projeto.dto.ProductDTO;
import com.projetospringboot.projeto.entity.Product;

@Component
public class ProductMapper {

    // ===================== ENTITY -> DTO =====================

    public ProductDTO toDTO(Product entity) {
        if (entity == null) return null;

        return new ProductDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getImgUrl()
        );
    }

    // ===================== DTO -> ENTITY =====================

    public Product toEntity(ProductDTO dto) {
        if (dto == null) return null;

        Product entity = new Product();
        copyToEntity(dto, entity);
        return entity;
    }

    // ===================== UPDATE (PROFISSIONAL 🔥) =====================

    public void copyToEntity(ProductDTO dto, Product entity) {
        if (dto == null || entity == null) return;

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }
}