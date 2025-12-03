package com.senai.ContaBancaria.Domain.Entity;

import com.senai.ContaBancaria.Domain.Exceptions.SaldoInsuficienteException;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CORRENTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContaCorrenteEntity extends ContaEntity{

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal limite;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal taxa;

    @Override
    public String getTipoConta() {
        return "CORRENTE";
    }

    @Override
    public void sacar(BigDecimal valor) {
        validarValorMaiorQueZero(valor, "saque");

        // taxa é percentual decimal: ex 0.02 = 2%
        BigDecimal custoSaque = valor.multiply(this.taxa);
        BigDecimal totalSaque = valor.add(custoSaque);

        // saldo negativo permitido até o limite
        if (this.getSaldo().add(this.limite).compareTo(totalSaque) < 0) {
            throw new SaldoInsuficienteException();
        }

        // desconta o valor e a taxa
        this.setSaldo(this.getSaldo().subtract(totalSaque));
    }
}
