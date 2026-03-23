package com.projetospringboot.projeto.entity.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum que representa os possíveis estados de um pedido.
 *
 * Cada status possui um código inteiro que é persistido no banco,
 * permitindo melhor controle e independência do nome do enum.
 */
public enum OrderStatus {

    WAITING_PAYMENT(1),
    PAID(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELED(5);

    /**
     * Código numérico persistido no banco de dados.
     */
    private final int code;

    /**
     * Cache estático para conversão rápida de código -> enum.
     */
    private static final Map<Integer, OrderStatus> MAP = new HashMap<>();

    // ===================== BLOCO ESTÁTICO =====================

    /**
     * Inicializa o mapa de conversão.
     *
     * Executado uma única vez na carga da classe.
     */
    static {
        for (OrderStatus status : values()) {
            MAP.put(status.getCode(), status);
        }
    }

    // ===================== CONSTRUTOR =====================

    private OrderStatus(int code) {
        this.code = code;
    }

    // ===================== GETTER =====================

    public int getCode() {
        return code;
    }

    // ===================== CONVERSÃO =====================

    /**
     * Converte um código inteiro para o enum correspondente.
     *
     * @param code código do status
     * @return status correspondente
     * @throws IllegalArgumentException se o código for inválido
     */
    public static OrderStatus valueOf(int code) {

        OrderStatus status = MAP.get(code);

        if (status == null) {
            throw new IllegalArgumentException("Código de status inválido: " + code);
        }

        return status;
    }
}