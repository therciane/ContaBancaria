package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaCorrenteEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaPoupancaEntity;
import com.senai.ContaBancaria.Domain.Exceptions.TipoDeContaInvalidaException;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ContaResumoDTO(
        @NotNull(message = "Este espaço é obrigatório")
        @Size (min = 8, max = 12, message = "O numero da conta, deve possuir entre 8 e 12 números.")
        @Pattern(regexp = "\\d+", message = "O número da conta deve conter apenas dígitos numéricos.")
        String numeroConta,

        @NotBlank(message = "O tipo da sua conta é obrigatório")
        String tipoConta,

        @NotNull(message = "O saldo não pode ser negativo e/ou ficar em branco")
        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal saldo
) {

    public ContaEntity toEntity(ClienteEntity cliente) {
        if ("CORRENTE".equalsIgnoreCase(tipoConta)) {
            return ContaCorrenteEntity.builder()
                    .numero(this.numeroConta)
                    .saldo(this.saldo)
                    .ativo(true)
                    .cliente(cliente)
                    .limite(new BigDecimal("500.00"))
                    .taxa(new BigDecimal("0.05"))
                    .build();

        } else if ("POUPANCA".equalsIgnoreCase(tipoConta)) {
            return ContaPoupancaEntity.builder()
                    .numero(this.numeroConta)
                    .saldo(this.saldo)
                    .ativo(true)
                    .cliente(cliente)
                    .rendimento(new BigDecimal("0.01"))
                    .build();
        }
        throw new TipoDeContaInvalidaException();
    }

    public static ContaResumoDTO fromEntity(ContaEntity c) {
        return new ContaResumoDTO(
                c.getNumero(),
                c.getTipoConta(),
                c.getSaldo()
        );
    }
}
