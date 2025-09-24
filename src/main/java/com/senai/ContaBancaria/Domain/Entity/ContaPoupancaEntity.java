package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@DiscriminatorValue("POUPANCA") //Valor que identifica a classe
//@NoArgsConstructor

public class ContaPoupancaEntity extends ContaEntity {
    @Column(nullable = false, precision = 10, scale = 4)
    private long rendimento;

    @Override
    public String getTipoConta() {
        return "POUPANCA";
    }
}
