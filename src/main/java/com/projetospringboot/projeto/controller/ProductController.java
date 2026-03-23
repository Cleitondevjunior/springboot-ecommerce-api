package com.projetospringboot.projeto.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetospringboot.projeto.dto.ProductDTO;
import com.projetospringboot.projeto.entity.Product;
import com.projetospringboot.projeto.mapper.ProductMapper;
import com.projetospringboot.projeto.services.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductMapper mapper;

    // ===================== GET ALL =====================

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<Product> list = service.findAll();

        List<ProductDTO> dtoList = list.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    // ===================== GET BY ID =====================

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        Product obj = service.findById(id);
        return ResponseEntity.ok(mapper.toDTO(obj));
    }

    // ===================== INSERT =====================

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto) {

        Product entity = mapper.toEntity(dto);
        entity = service.insert(entity);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();

        return ResponseEntity.created(uri).body(mapper.toDTO(entity));
    }

    // ===================== DELETE =====================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ===================== UPDATE =====================

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody ProductDTO dto) {

        Product entity = service.findById(id);
        mapper.copyToEntity(dto, entity);
        entity = service.update(id, entity);

        return ResponseEntity.ok(mapper.toDTO(entity));
    }
}