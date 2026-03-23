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

import com.projetospringboot.projeto.dto.OrderDTO;
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
     * Injeção de dependência por construtor.
     *
     * @param service serviço de pedidos
     */
    public OrderController(OrderService service) {
        this.service = service;
    }

    // ===================== GET ALL =====================

    /**
     * Busca todos os pedidos cadastrados.
     *
     * @return resposta HTTP 200 com a lista de pedidos
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {

        // Retorna todos os pedidos já convertidos para DTO
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

        // Busca pedido pelo ID
        return ResponseEntity.ok(service.findById(id));
    }

    // ===================== INSERT =====================

    /**
     * Cadastra um novo pedido.
     *
     * Recebe os dados do pedido no corpo da requisição,
     * salva no banco e retorna o recurso criado.
     *
     * @param obj pedido recebido no corpo da requisição
     * @return resposta HTTP 201 com o pedido criado
     */
    @PostMapping
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody Order obj) {

        // Salva o pedido e recebe o DTO de resposta
        OrderDTO dto = service.insert(obj);

        // Monta a URI do recurso criado: /orders/{id}
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        // Retorna HTTP 201 (Created)
        return ResponseEntity.created(uri).body(dto);
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

        // Solicita exclusão do pedido
        service.delete(id);

        // Retorna HTTP 204 (No Content)
        return ResponseEntity.noContent().build();
    }

    // ===================== UPDATE =====================

    /**
     * Atualiza um pedido existente.
     *
     * @param id identificador do pedido
     * @param obj novos dados do pedido
     * @return resposta HTTP 200 com o pedido atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @Valid @RequestBody Order obj) {

        // Atualiza o pedido e retorna o DTO atualizado
        return ResponseEntity.ok(service.update(id, obj));
    }
}