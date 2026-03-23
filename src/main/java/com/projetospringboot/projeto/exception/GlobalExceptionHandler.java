package com.projetospringboot.projeto.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Handler global para tratamento de exceções da API.
 *
 * Responsável por padronizar as respostas de erro em formato JSON.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===================== 404 - RECURSO NÃO ENCONTRADO =====================

    /**
     * Trata exceções de recurso não encontrado customizadas.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado",
                ex.getMessage(),
                request
        );
    }

    /**
     * Trata exceções de entidade não encontrada do JPA.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(
            EntityNotFoundException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.NOT_FOUND,
                "Entidade não encontrada",
                ex.getMessage(),
                request
        );
    }

    // ===================== 400 - ERRO DE VALIDAÇÃO =====================

    /**
     * Trata erros de validação do Bean Validation (@Valid).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> body = baseError(HttpStatus.BAD_REQUEST, request);
        body.put("error", "Erro de validação");
        body.put("message", "Um ou mais campos estão inválidos");
        body.put("fields", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * Trata erros de regra de negócio simples, como:
     * - nome duplicado
     * - email já cadastrado
     * - argumentos inválidos
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Argumento inválido",
                ex.getMessage(),
                request
        );
    }

    // ===================== 409 - CONFLITO / INTEGRIDADE =====================

    /**
     * Trata violações de integridade no banco de dados.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.CONFLICT,
                "Erro de integridade",
                "Violação de regra de integridade no banco de dados",
                request
        );
    }

    // ===================== 500 - ERRO INTERNO =====================

    /**
     * Trata qualquer exceção não mapeada explicitamente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno do servidor",
                ex.getMessage(),
                request
        );
    }

    // ===================== MÉTODOS AUXILIARES =====================

    /**
     * Monta a estrutura padrão de erro da API.
     */
    private ResponseEntity<Map<String, Object>> buildError(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request) {

        Map<String, Object> body = baseError(status, request);
        body.put("error", error);
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }

    /**
     * Cria a base comum de todas as respostas de erro.
     */
    private Map<String, Object> baseError(HttpStatus status, HttpServletRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        body.put("path", request.getRequestURI());

        return body;
    }
}