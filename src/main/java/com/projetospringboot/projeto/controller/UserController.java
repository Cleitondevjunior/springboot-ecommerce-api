package com.projetospringboot.projeto.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projetospringboot.projeto.dto.UserCreateDTO;
import com.projetospringboot.projeto.dto.UserDTO;
import com.projetospringboot.projeto.dto.UserUpdateDTO;
import com.projetospringboot.projeto.entity.User;
import com.projetospringboot.projeto.mapper.UserMapper;
import com.projetospringboot.projeto.services.UserService;

import jakarta.validation.Valid;

/**
 * Controller responsável pelos endpoints REST de usuários.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper mapper;

    // ===================== GET ALL =====================

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {

        List<UserDTO> dtoList = service.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    // ===================== GET BY ID =====================

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {

        User user = service.findById(id);

        return ResponseEntity.ok(mapper.toDTO(user));
    }

    // ===================== INSERT =====================

    /**
     * Agora usando DTO correto para criação
     */
    @PostMapping
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserCreateDTO dto) {

        User entity = mapper.toEntity(dto);

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

    /**
     * Agora usando DTO correto para atualização parcial
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id,
                                          @Valid @RequestBody UserUpdateDTO dto) {

        User entity = service.findById(id);

        // Atualiza somente campos permitidos
        mapper.updateEntity(entity, dto);

        entity = service.insert(entity); // save/update

        return ResponseEntity.ok(mapper.toDTO(entity));
    }
}