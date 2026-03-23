package com.projetospringboot.projeto.entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum OrderStatus {

    WAITING_PAYMENT(1),
    PAID(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELED(5);

    //  código persistido no banco
    private final int code;

    //  cache para busca rápida (melhor performance)
    private static final Map<Integer, OrderStatus> MAP = new HashMap<>();

    //  bloco estático (executado uma vez)
    static {
        for (OrderStatus status : values()) {
            MAP.put(status.getCode(), status);
        }
    }

    private OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    // ===================== CONVERSÃO =====================

    public static OrderStatus valueOf(int code) {
        OrderStatus status = MAP.get(code);

        if (status == null) {
            throw new IllegalArgumentException("Código de status inválido: " + code);
        }

        return status;
    }
}