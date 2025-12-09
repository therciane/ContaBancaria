package com.senai.ContaBancaria.Application.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Entity.CodigoAutenticacaoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CodigoAutenticacaoDTO(
        UUID id,

        @NotBlank
        String codigo,

        @NotNull
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm", timezone = "America/Sao_Paulo")
        LocalDateTime expiracao,

        @NotNull
        Boolean validado,

        String clienteId
) {

    public static CodigoAutenticacaoDTO fromEntity(CodigoAutenticacaoEntity entity) {
        return new CodigoAutenticacaoDTO(
                entity.getId(),
                entity.getCodigo(),
                entity.getExpiraEm(),
                entity.getValidado(),
                entity.getCliente().getId()
        );
    }

    public CodigoAutenticacaoEntity toEntity(ClienteEntity cliente) {
        return CodigoAutenticacaoEntity.builder()
                .codigo(this.codigo)
                .expiraEm(this.expiracao)
                .validado(this.validado)
                .cliente(cliente)
                .build();
    }
}

