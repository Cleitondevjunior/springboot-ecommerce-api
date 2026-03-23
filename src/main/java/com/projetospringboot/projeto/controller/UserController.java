package com.projetospringboot.projeto.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projetospringboot.projeto.dto.UserDTO;
import com.projetospringboot.projeto.entity.User;
import com.projetospringboot.projeto.mapper.UserMapper;
import com.projetospringboot.projeto.services.UserService;

import jakarta.validation.Valid;

/**
 * Controller responsável por expor os endpoints REST da entidade User.
 * 
 * Aqui definimos as rotas da API relacionadas a usuários.
 */
@RestController
@RequestMapping("/users") // Endpoint base: /users
public class UserController {

    /**
     * Camada de serviço responsável pelas regras de negócio.
     */
    @Autowired
    private UserService service;

    /**
     * Mapper responsável por converter Entity <-> DTO.
     */
    @Autowired
    private UserMapper mapper;

    // ===================== GET ALL =====================

    /**
     * Busca todos os usuários cadastrados.
     * 
     * @return lista de usuários (DTO)
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {

        // Busca lista de entidades no banco
        List<User> list = service.findAll();

        // Converte lista de entidades para DTO
        List<UserDTO> dtoList = list.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        // Retorna resposta HTTP 200 (OK)
        return ResponseEntity.ok(dtoList);
    }

    // ===================== GET BY ID =====================

    /**
     * Busca um usuário pelo ID.
     * 
     * @param id identificador do usuário
     * @return usuário encontrado (DTO)
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {

        // Busca usuário no banco
        User obj = service.findById(id);

        // Retorna resposta HTTP 200 com o usuário convertido em DTO
        return ResponseEntity.ok(mapper.toDTO(obj));
    }

    // ===================== INSERT =====================

    /**
     * Insere um novo usuário.
     * 
     * @param dto dados do usuário enviados no corpo da requisição
     * @return usuário criado (DTO) + URI do recurso criado
     */
    @PostMapping
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserDTO dto) {

        // Converte DTO para entidade
        User entity = mapper.toEntity(dto);

        // Salva no banco
        entity = service.insert(entity);

        // Cria URI do novo recurso (boa prática REST)
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();

        // Retorna HTTP 201 (Created) com localização do recurso
        return ResponseEntity.created(uri).body(mapper.toDTO(entity));
    }

    // ===================== DELETE =====================

    /**
     * Deleta um usuário pelo ID.
     * 
     * @param id identificador do usuário
     * @return resposta HTTP 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        // Remove usuário do banco
        service.delete(id);

        // Retorna HTTP 204 (sem conteúdo)
        return ResponseEntity.noContent().build();
    }

    // ===================== UPDATE =====================

    /**
     * Atualiza um usuário existente.
     * 
     * @param id identificador do usuário
     * @param dto novos dados do usuário
     * @return usuário atualizado (DTO)
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {

        // Converte DTO para entidade
        User entity = mapper.toEntity(dto);

        // Atualiza no banco
        entity = service.update(id, entity);

        // Retorna HTTP 200 com o usuário atualizado
        return ResponseEntity.ok(mapper.toDTO(entity));
    }
}