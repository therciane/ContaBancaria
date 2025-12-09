package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;

public record ClienteCadastroDTO(

        @NotBlank(message = "O nome não pode ficar em branco")
        @Size(max = 80, message = "O nome não pode ultrapassar 80 caracteres")
        String nomeCompleto,

        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
                message = "O CPF deve estar no formato 000.000.000-00")
        String cpf,

        @Valid
        ContaResumoDTO contaDTO
) {

    public ClienteEntity toEntity() {
        return ClienteEntity.builder()
                .ativo(true)
                .nomeCompleto(nomeCompleto)
                .cpf(limparCpf(cpf))
                .contas(new ArrayList<>()) // contas preenchidas no AppService
                .build();
    }

    private String limparCpf(String cpf) {
        return cpf.replaceAll("\\D", ""); // remove pontos e traços
    }
}

