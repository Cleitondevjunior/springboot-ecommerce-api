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

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    // ===================== GET ALL =====================
    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // ===================== GET BY ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // ===================== INSERT =====================
    @PostMapping
    public ResponseEntity<OrderDTO> insert(@RequestBody Order obj) {

        OrderDTO dto = service.insert(obj);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    // ===================== DELETE =====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ===================== UPDATE =====================
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody Order obj) {
        return ResponseEntity.ok(service.update(id, obj));
    }
}