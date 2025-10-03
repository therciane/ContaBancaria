package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@DiscriminatorValue("POUPANCA") //Valor que identifica a classe
//@NoArgsConstructor

public class ContaPoupancaEntity extends ContaEntity {
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal rendimento;

    @Override
    public String getTipoConta() {
        return "POUPANCA";
    }

    public void aplicarRendimento() {
        var ganho = getSaldo().multiply(rendimento);
        setSaldo(getSaldo().add(ganho));
    }
}
