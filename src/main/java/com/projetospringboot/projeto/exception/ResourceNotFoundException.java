package com.projetospringboot.projeto.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    //  construtor padrão com ID
    public ResourceNotFoundException(Object id) {
        super(buildMessage(id));
    }

    //  construtor customizado
    public ResourceNotFoundException(String message) {
        super(message);
    }

    //  método auxiliar (clean code)
    private static String buildMessage(Object id) {
        return "Recurso não encontrado. ID: " + id;
    }
}