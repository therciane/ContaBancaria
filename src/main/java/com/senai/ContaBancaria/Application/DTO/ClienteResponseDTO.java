package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ClienteResponseDTO(

        String id,

        @NotBlank(message = "Este espaço não pode ficar em branco")
        @Size(max = 80, message = "O nome não pode ultrapassar 80 caracteres")
        @Column(nullable = false, length = 80)
        String nomeCompleto,

        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve estar no formato 000.000.000-00")
        @Column(nullable = false, unique = true, length = 14)
        String cpf,

        @Valid
        @NotEmpty(message = "O usuário precisa de pelo menos uma conta")
        List<ContaResumoDTO> contas
) {
    public static ClienteResponseDTO fromEntity(ClienteEntity cliente) {
        List<ContaResumoDTO> contas = cliente.getContas().stream()
                .map(ContaResumoDTO::fromEntity)
                .toList();

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNomeCompleto(),
                cliente.getCpf(),
                contas
        );
    }
}
