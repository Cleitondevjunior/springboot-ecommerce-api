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

import com.projetospringboot.projeto.dto.OrderCreateDTO;
import com.projetospringboot.projeto.dto.OrderDTO;
import com.projetospringboot.projeto.dto.OrderUpdateDTO;
import com.projetospringboot.projeto.mapper.OrderMapper;
import com.projetospringboot.projeto.entity.Order;
import com.projetospringboot.projeto.services.OrderService;

import jakarta.validation.Valid;

/**
 * Controller responsável por expor os endpoints REST da entidade Order.
 *
 * Aqui ficam as rotas relacionadas aos pedidos da aplicação.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    /**
     * Serviço responsável pelas regras de negócio dos pedidos.
     */
    private final OrderService service;

    /**
     * Mapper responsável por converter DTOs e entidades de pedido.
     */
    private final OrderMapper mapper;

    /**
     * Injeção de dependência por construtor.
     *
     * @param service serviço de pedidos
     * @param mapper mapper de pedidos
     */
    public OrderController(OrderService service, OrderMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // ===================== GET ALL =====================

    /**
     * Busca todos os pedidos cadastrados.
     *
     * @return resposta HTTP 200 com a lista de pedidos
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // ===================== GET BY ID =====================

    /**
     * Busca um pedido pelo ID.
     *
     * @param id identificador do pedido
     * @return resposta HTTP 200 com o pedido encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // ===================== INSERT =====================

    /**
     * Cadastra um novo pedido.
     *
     * Recebe um DTO de criação, converte para entidade
     * e retorna o pedido criado.
     *
     * @param dto dados de criação do pedido
     * @return resposta HTTP 201 com o pedido criado
     */
    @PostMapping
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderCreateDTO dto) {

        Order entity = mapper.toEntity(dto);

        OrderDTO savedDto = service.insert(entity);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedDto);
    }

    // ===================== DELETE =====================

    /**
     * Remove um pedido pelo ID.
     *
     * @param id identificador do pedido
     * @return resposta HTTP 204 sem conteúdo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ===================== UPDATE =====================

    /**
     * Atualiza um pedido existente.
     *
     * Recebe um DTO de atualização e altera apenas os campos permitidos.
     *
     * @param id identificador do pedido
     * @param dto dados para atualização
     * @return resposta HTTP 200 com o pedido atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id,
                                           @Valid @RequestBody OrderUpdateDTO dto) {

        Order entity = service.findEntityById(id);

        mapper.updateEntity(entity, dto);

        OrderDTO updatedDto = service.update(id, entity);

        return ResponseEntity.ok(updatedDto);
    }
}