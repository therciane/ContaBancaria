package com.senai.ContaBancaria.Application.DTO;

public record AuthDTO() {

    public record LoginRequest(
            String email,
            String senha
    ) { }

    public record TokenResponse(
            String token
    ) { }
}

