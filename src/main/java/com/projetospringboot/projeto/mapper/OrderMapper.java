package com.projetospringboot.projeto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.projetospringboot.projeto.dto.OrderDTO;
import com.projetospringboot.projeto.dto.OrderItemDTO;
import com.projetospringboot.projeto.entity.Order;
import com.projetospringboot.projeto.entity.OrderItem;
import com.projetospringboot.projeto.entity.User;

@Component
public class OrderMapper {

    // ===================== ENTITY → DTO =====================
    public OrderDTO toDTO(Order entity) {

        if (entity == null) return null;

        Long clientId = null;
        String clientName = null;

        if (entity.getClient() != null) {
            clientId = entity.getClient().getId();
            clientName = entity.getClient().getName();
        }

        List<OrderItemDTO> items = entity.getItems().stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());

        OrderDTO dto = new OrderDTO();

        dto.setId(entity.getId());
        dto.setClientId(clientId);
        dto.setClientName(clientName);
        dto.setMoment(entity.getMoment());
        dto.setStatus(entity.getOrderStatus() != null ? entity.getOrderStatus().name() : null);

        //  CAMPOS Subtotal,Desconto, total CORRETOS
        dto.setSubtotal(entity.getItemsTotal());
        dto.setDesconto(entity.getOrderDiscount());
        dto.setTotal(entity.getTotal());

        dto.setItems(items);

        return dto;
    }

    // ===================== ITEM → DTO =====================
    private OrderItemDTO toItemDTO(OrderItem item) {
        return new OrderItemDTO(
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getSubTotal(),
                item.getDiscount()
        );
    }

    // ===================== DTO → ENTITY =====================
    public Order toEntity(OrderDTO dto) {

        if (dto == null) return null;

        Order entity = new Order();

        entity.setId(dto.getId());
        entity.setMoment(dto.getMoment());

        if (dto.getStatus() != null) {
            entity.setOrderStatus(
                com.projetospringboot.projeto.entity.enums.OrderStatus.valueOf(dto.getStatus())
            );
        }

        if (dto.getClientId() != null) {
            User user = new User();
            user.setId(dto.getClientId());
            entity.setClient(user);
        }

        return entity;
    }
}