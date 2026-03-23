package com.projetospringboot.projeto.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===================== 404 =====================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado",
                ex.getMessage(),
                request
        );
    }

    // ===================== 400 (VALIDAÇÃO) =====================
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
        body.put("message", "Campos inválidos");
        body.put("fields", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // ===================== 409 (INTEGRIDADE) =====================
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDatabase(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.CONFLICT,
                "Erro de integridade",
                "Violação de regra no banco de dados",
                request
        );
    }

    // ===================== 500 (GENÉRICO) =====================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno",
                ex.getMessage(),
                request
        );
    }

    // ===================== MÉTODOS AUXILIARES =====================

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

    private Map<String, Object> baseError(HttpStatus status, HttpServletRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        body.put("path", request.getRequestURI());

        return body;
    }
}