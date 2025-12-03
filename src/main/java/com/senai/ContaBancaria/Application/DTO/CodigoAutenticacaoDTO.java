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
        Boolean validado

) {
    public static CodigoAutenticacaoDTO fromEntity(CodigoAutenticacaoEntity codAuth) {
        return new CodigoAutenticacaoDTO(
                codAuth.getId(),
                codAuth.getCodigo(),
                codAuth.getExpiracao(),
                codAuth.getValidado()
        );
    }

    public CodigoAutenticacaoEntity toEntity(ClienteEntity cliente){
        return CodigoAutenticacaoEntity.builder()
                .id(this.id)
                .codigo(this.codigo)
                .expiracao(this.expiracao)
                .validado(this.validado)
                .cliente(cliente)
                .build();
    }
}
