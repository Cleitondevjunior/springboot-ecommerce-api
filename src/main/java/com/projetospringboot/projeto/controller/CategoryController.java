package com.projetospringboot.projeto.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projetospringboot.projeto.dto.CategoryDTO;
import com.projetospringboot.projeto.entity.Category;
import com.projetospringboot.projeto.mapper.CategoryMapper;
import com.projetospringboot.projeto.services.CategoryService;

import jakarta.validation.Valid;

/**
 * Controller responsável por expor os endpoints REST da entidade Category.
 *
 * Aqui ficam as rotas relacionadas às categorias da aplicação.
 */
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    /**
     * Serviço responsável pelas regras de negócio das categorias.
     */
    private final CategoryService categoryService;

    /**
     * Injeção de dependência por construtor.
     *
     * @param categoryService serviço de categorias
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ===================== BUSCAR TODAS =====================

    /**
     * Busca todas as categorias cadastradas.
     *
     * @return resposta HTTP 200 com a lista de categorias
     */
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {

        // Busca todas as entidades na camada de serviço
        List<Category> list = categoryService.findAll();

        // Converte a lista de entidades para DTO
        return ResponseEntity.ok(CategoryMapper.toDTOList(list));
    }

    // ===================== BUSCAR POR ID =====================

    /**
     * Busca uma categoria pelo ID.
     *
     * @param id identificador da categoria
     * @return resposta HTTP 200 com a categoria encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {

        // Busca a entidade na camada de serviço
        Category entity = categoryService.findById(id);

        // Retorna a categoria convertida para DTO
        return ResponseEntity.ok(CategoryMapper.toDTO(entity));
    }

    // ===================== INSERIR =====================

    /**
     * Cadastra uma nova categoria.
     *
     * @param dto dados da categoria recebidos no corpo da requisição
     * @return resposta HTTP 201 com a categoria criada
     */
    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryDTO dto) {

        // Converte o DTO para entidade
        Category entity = CategoryMapper.toEntity(dto);

        // Salva a nova categoria
        entity = categoryService.insert(entity);

        // Monta a URI do recurso criado: /categories/{id}
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();

        // Retorna HTTP 201 (Created)
        return ResponseEntity.created(uri).body(CategoryMapper.toDTO(entity));
    }

    // ===================== DELETAR =====================

    /**
     * Remove uma categoria pelo ID.
     *
     * @param id identificador da categoria
     * @return resposta HTTP 204 sem conteúdo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        // Solicita a exclusão da categoria
        categoryService.delete(id);

        // Retorna HTTP 204 (No Content)
        return ResponseEntity.noContent().build();
    }

    // ===================== ATUALIZAR =====================

    /**
     * Atualiza uma categoria existente.
     *
     * @param id identificador da categoria
     * @param dto novos dados da categoria
     * @return resposta HTTP 200 com a categoria atualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id,
                                              @Valid @RequestBody CategoryDTO dto) {

        // Converte o DTO para entidade
        Category entity = CategoryMapper.toEntity(dto);

        // Atualiza a categoria na camada de serviço
        entity = categoryService.update(id, entity);

        // Retorna a categoria atualizada em formato DTO
        return ResponseEntity.ok(CategoryMapper.toDTO(entity));
    }
}