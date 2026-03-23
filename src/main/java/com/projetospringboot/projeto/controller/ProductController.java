package com.projetospringboot.projeto.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projetospringboot.projeto.dto.ProductDTO;
import com.projetospringboot.projeto.entity.Product;
import com.projetospringboot.projeto.mapper.ProductMapper;
import com.projetospringboot.projeto.services.ProductService;

import jakarta.validation.Valid;

/**
 * Controller responsável por expor os endpoints REST da entidade Product.
 *
 * Aqui ficam as rotas da API relacionadas aos produtos.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    /**
     * Serviço responsável pelas regras de negócio dos produtos.
     */
    private final ProductService service;

    /**
     * Mapper responsável por converter Entity <-> DTO.
     */
    private final ProductMapper mapper;

    /**
     * Injeção de dependência por construtor.
     *
     * @param service serviço de produtos
     * @param mapper mapper de produtos
     */
    public ProductController(ProductService service, ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // ===================== GET ALL =====================

    /**
     * Busca todos os produtos cadastrados.
     *
     * @return resposta HTTP 200 com a lista de produtos
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {

        // Busca todas as entidades no banco
        List<Product> list = service.findAll();

        // Converte a lista de entidades para DTO
        List<ProductDTO> dtoList = list.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        // Retorna HTTP 200 (OK)
        return ResponseEntity.ok(dtoList);
    }

    // ===================== GET BY ID =====================

    /**
     * Busca um produto pelo ID.
     *
     * @param id identificador do produto
     * @return resposta HTTP 200 com o produto encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {

        // Busca a entidade pelo ID
        Product obj = service.findById(id);

        // Retorna o produto convertido para DTO
        return ResponseEntity.ok(mapper.toDTO(obj));
    }

    // ===================== INSERT =====================

    /**
     * Cadastra um novo produto.
     *
     * @param dto dados do produto recebidos no corpo da requisição
     * @return resposta HTTP 201 com o produto criado
     */
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto) {

        // Converte o DTO para entidade
        Product entity = mapper.toEntity(dto);

        // Salva a entidade no banco
        entity = service.insert(entity);

        // Monta a URI do recurso criado
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();

        // Retorna HTTP 201 (Created)
        return ResponseEntity.created(uri).body(mapper.toDTO(entity));
    }

    // ===================== DELETE =====================

    /**
     * Remove um produto pelo ID.
     *
     * @param id identificador do produto
     * @return resposta HTTP 204 sem conteúdo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        // Solicita a exclusão do produto
        service.delete(id);

        // Retorna HTTP 204 (No Content)
        return ResponseEntity.noContent().build();
    }

    // ===================== UPDATE =====================

    /**
     * Atualiza um produto existente.
     *
     * @param id identificador do produto
     * @param dto novos dados do produto
     * @return resposta HTTP 200 com o produto atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody ProductDTO dto) {

        // Busca a entidade existente no banco
        Product entity = service.findById(id);

        // Copia os novos dados do DTO para a entidade
        mapper.copyToEntity(dto, entity);

        // Persiste a atualização
        entity = service.update(id, entity);

        // Retorna o produto atualizado em formato DTO
        return ResponseEntity.ok(mapper.toDTO(entity));
    }
}