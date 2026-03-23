package com.projetospringboot.projeto.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
 * Controller responsável por expor os endpoints REST da entidade User.
 *
 * Aqui ficam as rotas da API relacionadas ao recurso /users.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * Serviço responsável pelas regras de negócio dos usuários.
     */
    private final UserService service;

    /**
     * Mapper responsável por converter Entity <-> DTO.
     */
    private final UserMapper mapper;

    /**
     * Injeção de dependência por construtor.
     * Essa abordagem é mais profissional do que usar @Autowired no atributo.
     *
     * @param service serviço de usuário
     * @param mapper mapper de usuário
     */
    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // ===================== GET ALL =====================

    /**
     * Busca todos os usuários cadastrados.
     *
     * @return resposta HTTP 200 com a lista de usuários
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {

        // Busca todos os usuários na camada de serviço
        List<UserDTO> dtoList = service.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        // Retorna HTTP 200 (OK)
        return ResponseEntity.ok(dtoList);
    }

    // ===================== GET BY ID =====================

    /**
     * Busca um usuário pelo ID.
     *
     * @param id identificador do usuário
     * @return resposta HTTP 200 com o usuário encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {

        // Busca o usuário pelo ID
        User user = service.findById(id);

        // Converte a entidade para DTO e retorna na resposta
        return ResponseEntity.ok(mapper.toDTO(user));
    }

    // ===================== INSERT =====================

    /**
     * Cadastra um novo usuário.
     *
     * Usa UserCreateDTO para receber somente os dados necessários
     * para criação, incluindo senha.
     *
     * @param dto dados recebidos no corpo da requisição
     * @return resposta HTTP 201 com o usuário criado e a URI do recurso
     */
    @PostMapping
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserCreateDTO dto) {

        // Converte o DTO de criação para entidade
        User entity = mapper.toEntity(dto);

        // Salva o novo usuário
        entity = service.insert(entity);

        // Monta a URI do recurso criado: /users/{id}
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
     * Remove um usuário pelo ID.
     *
     * @param id identificador do usuário
     * @return resposta HTTP 204 sem conteúdo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        // Solicita exclusão na camada de serviço
        service.delete(id);

        // Retorna HTTP 204 (No Content)
        return ResponseEntity.noContent().build();
    }

    // ===================== UPDATE =====================

    /**
     * Atualiza os dados de um usuário existente.
     *
     * Usa UserUpdateDTO para permitir atualização controlada
     * e evitar expor dados indevidos.
     *
     * @param id identificador do usuário
     * @param dto novos dados recebidos na requisição
     * @return resposta HTTP 200 com o usuário atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id,
                                          @Valid @RequestBody UserUpdateDTO dto) {

        // Busca a entidade existente no banco
        User entity = service.findById(id);

        // Atualiza somente os campos permitidos
        mapper.updateEntity(entity, dto);

        // Persiste a atualização corretamente pela regra de update
        entity = service.update(id, entity);

        // Retorna HTTP 200 com o usuário atualizado
        return ResponseEntity.ok(mapper.toDTO(entity));
    }
}