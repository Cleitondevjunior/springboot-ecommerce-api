package com.projetospringboot.projeto.dto;

import java.io.Serializable;

/**
 * DTO de resposta de autenticação.
 */
public class TokenDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accessToken;
    private String tokenType;

    public TokenDTO() {
    }

    public TokenDTO(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}