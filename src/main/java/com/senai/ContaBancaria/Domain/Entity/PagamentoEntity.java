package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@Table (name = "pgamento")
@DiscriminatorValue("PAGAMENTO")

public class PagamentoEntity extends ContaEntity {
    @NotNull
    @Column (nullable = false, length = 48) //tamanho de um codigo de barras de um boleto comum no Brasil
    private String boleto;

    @Column (name = "valor_pago", precision = 15, scale = 2)
    @Positive
    @NotNull
    private BigDecimal valorPago;

    @Column (name = "data_pagamento")
    @NotNull
    private LocalDate dataPagamento;

    @Column (nullable = false)
    private Boolean status;

    @Override
    public String getTipoConta() {
        return "PAGAMENTO";
    }
}
