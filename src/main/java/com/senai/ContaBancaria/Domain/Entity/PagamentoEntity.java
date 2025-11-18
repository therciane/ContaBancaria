package com.senai.ContaBancaria.Domain.Entity;

import com.senai.ContaBancaria.Domain.Enum.StatusPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@SuperBuilder
@Table (name = "pagamento")
@DiscriminatorValue("PAGAMENTO")
public class PagamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @ManyToOne
    private ContaEntity conta;

    @NotNull
    @Column (nullable = false, length = 48) //tamanho de um codigo de barras de um boleto comum no Brasil
    private String boleto;

    @Column (name = "valor_pago", precision = 15, scale = 2)
    @Positive
    @NotNull
    private BigDecimal valorPago;

    @Column (name = "data_pagamento")
    @NotNull
    private LocalDateTime data;

    @Column (nullable = false)
    private StatusPagamento statusPagamento; //criar ENUM

    @ManyToMany(fetch = FetchType.LAZY)
    private String TaxaEntity;

}
