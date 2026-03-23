package com.projetospringboot.projeto.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetospringboot.projeto.dto.CategoryDTO;
import com.projetospringboot.projeto.entity.Category;
import com.projetospringboot.projeto.mapper.CategoryMapper;
import com.projetospringboot.projeto.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // Injeção por construtor (melhor prática)
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ===================== BUSCAR TODAS =====================
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<Category> list = categoryService.findAll();
        return ResponseEntity.ok(CategoryMapper.toDTOList(list));
    }

    // ===================== BUSCAR POR ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        Category entity = categoryService.findById(id);
        return ResponseEntity.ok(CategoryMapper.toDTO(entity));
    }

    // ===================== INSERIR =====================
    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryDTO dto) {

        Category entity = CategoryMapper.toEntity(dto);
        entity = categoryService.insert(entity);

        URI uri = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();

        return ResponseEntity.created(uri).body(CategoryMapper.toDTO(entity));
    }

    // ===================== DELETAR =====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ===================== ATUALIZAR =====================
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id,
                                              @Valid @RequestBody CategoryDTO dto) {

        Category entity = CategoryMapper.toEntity(dto);
        entity = categoryService.update(id, entity);

        return ResponseEntity.ok(CategoryMapper.toDTO(entity));
    }
}