package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.GerenteEnity;
import com.senai.ContaBancaria.Domain.Enum.Role;
import lombok.Builder;

@Builder
public record GerenteDTO(
        String id,
        String nomeCompleto,
        String cpf,
        String email,
        String senha,
        Boolean ativo,
        Role role
) {

    public static GerenteDTO fromEntity(GerenteEnity gerente) {
        return GerenteDTO.builder()
                .id(gerente.getId())
                .nomeCompleto(gerente.getNomeCompleto())
                .cpf(gerente.getCpf())
                .email(gerente.getEmail())
                .ativo(gerente.isAtivo())
                .role(gerente.getRole())
                .build();
    }

    public GerenteEnity toEntity() {
        return GerenteEnity.builder()
                .id(this.id)
                .nomeCompleto(this.nomeCompleto)
                .cpf(this.cpf)
                .email(this.email)
                .senha(this.senha)
                .ativo(this.ativo != null ? this.ativo : true)
                .role(this.role != null ? this.role : Role.PROFESSOR)
                .build();
    }

}
