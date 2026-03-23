package com.projetospringboot.projeto.exception;

/**
 * Exceção lançada quando um recurso não é encontrado no sistema.
 *
 * Usada principalmente nas camadas de serviço.
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Construtor padrão com ID do recurso.
     *
     * @param id identificador do recurso
     */
    public ResourceNotFoundException(Object id) {
        super(buildMessage(id));
    }

    /**
     * Construtor com mensagem personalizada.
     *
     * @param message mensagem de erro
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Construtor com mensagem e causa.
     *
     * @param message mensagem de erro
     * @param cause exceção original
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // ===================== MÉTODO AUXILIAR =====================

    /**
     * Gera a mensagem padrão da exceção.
     *
     * @param id identificador do recurso
     * @return mensagem formatada
     */
    private static String buildMessage(Object id) {
        return "Recurso não encontrado. ID: " + id;
    }
}