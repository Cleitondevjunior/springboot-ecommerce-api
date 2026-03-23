package com.projetospringboot.projeto.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.projetospringboot.projeto.dto.UserDTO;
import com.projetospringboot.projeto.entity.User;
import com.projetospringboot.projeto.mapper.UserMapper;
import com.projetospringboot.projeto.services.UserService;

import jakarta.validation.Valid;

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
        List<User> list = service.findAll();

        List<UserDTO> dtoList = list.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    // ===================== GET BY ID =====================

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User obj = service.findById(id);
        return ResponseEntity.ok(mapper.toDTO(obj));
    }

    // ===================== INSERT =====================

    @PostMapping
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserDTO dto) {

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

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {

        User entity = mapper.toEntity(dto);
        entity = service.update(id, entity);

        return ResponseEntity.ok(mapper.toDTO(entity));
    }
}