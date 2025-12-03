package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("POUPANCA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContaPoupancaEntity extends ContaEntity {

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal rendimento;

    @Override
    public String getTipoConta() {
        return "POUPANCA";
    }

    public void aplicarRendimento() {
        BigDecimal ganho = this.getSaldo().multiply(this.rendimento);
        this.setSaldo(this.getSaldo().add(ganho));
    }
}
