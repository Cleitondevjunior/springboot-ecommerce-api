package com.projetospringboot.projeto.mapper;

import org.springframework.stereotype.Component;

import com.projetospringboot.projeto.dto.ProductDTO;
import com.projetospringboot.projeto.entity.Product;

/**
 * Classe responsável pelas conversões entre Product (Entity)
 * e ProductDTO.
 *
 * Centraliza a lógica de transformação de dados.
 */
@Component
public class ProductMapper {

    // ===================== ENTITY -> DTO =====================

    /**
     * Converte uma entidade Product para ProductDTO.
     *
     * @param entity entidade do produto
     * @return DTO do produto
     */
    public ProductDTO toDTO(Product entity) {

        if (entity == null) {
            return null;
        }

        return new ProductDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getImgUrl()
        );
    }

    // ===================== DTO -> ENTITY =====================

    /**
     * Converte ProductDTO para entidade Product.
     *
     * Usado principalmente na criação de novos produtos.
     *
     * @param dto dados do produto
     * @return entidade Product
     */
    public Product toEntity(ProductDTO dto) {

        if (dto == null) {
            return null;
        }

        Product entity = new Product();

        copyToEntity(dto, entity);

        return entity;
    }

    // ===================== UPDATE =====================

    /**
     * Copia os dados do DTO para uma entidade existente.
     *
     * Utilizado na atualização de produtos.
     *
     * Não altera o ID, pois ele é controlado pelo banco.
     *
     * @param dto dados novos
     * @param entity entidade existente
     */
    public void copyToEntity(ProductDTO dto, Product entity) {

        if (dto == null || entity == null) {
            return;
        }

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }
}