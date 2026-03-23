package com.projetospringboot.projeto.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.projetospringboot.projeto.dto.OrderDTO;
import com.projetospringboot.projeto.dto.OrderItemDTO;
import com.projetospringboot.projeto.entity.Order;
import com.projetospringboot.projeto.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    // ===================== CONSTRUTOR =====================
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // ===================== GET ALL =====================
    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ===================== GET BY ID =====================
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado. ID: " + id));

        return toDTO(order);
    }

    // ===================== INSERT =====================
    public OrderDTO insert(Order obj) {
        Order saved = orderRepository.save(obj);
        return toDTO(saved);
    }

    // ===================== DELETE =====================
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Pedido não encontrado. ID: " + id);
        }
        orderRepository.deleteById(id);
    }

    // ===================== UPDATE =====================
    public OrderDTO update(Long id, Order obj) {
        try {
            Order entity = orderRepository.getReferenceById(id);
            updateData(entity, obj);
            Order updated = orderRepository.save(entity);
            return toDTO(updated);

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Id não encontrado: " + id);
        }
    }

    // ===================== ENTITY → DTO =====================
    private OrderDTO toDTO(Order order) {

        // 🔹 converter itens
        List<OrderItemDTO> items = order.getItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getSubTotal(),
                        item.getDiscount()
                ))
                .collect(Collectors.toList());

        OrderDTO dto = new OrderDTO();

        dto.setId(order.getId());

        //  evitar NullPointer
        if (order.getClient() != null) {
            dto.setClientId(order.getClient().getId());
            dto.setClientName(order.getClient().getName());
        }

        dto.setMoment(order.getMoment());

        if (order.getOrderStatus() != null) {
            dto.setStatus(order.getOrderStatus().name());
        }

        //  CAMPOS CALCULADOS CORRETOS
        dto.setSubtotal(order.getItemsTotal());     // soma dos itens
        dto.setDesconto(order.getOrderDiscount());  // desconto geral
        dto.setTotal(order.getTotal());             // total final

        dto.setItems(items);

        return dto;
    }

    // ===================== UPDATE AUX =====================
    private void updateData(Order entity, Order obj) {
        entity.setMoment(obj.getMoment());
        entity.setOrderStatus(obj.getOrderStatus());
        entity.setClient(obj.getClient());
    }
}