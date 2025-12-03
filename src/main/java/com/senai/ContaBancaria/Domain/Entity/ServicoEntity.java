package com.senai.ContaBancaria.Domain.Entity;

import com.senai.ContaBancaria.Domain.Exceptions.ValidacaoException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "servico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String tipoConta;

    @Column(nullable = false, precision = 20, scale = 2)
    private BigDecimal saldo;

    public void validar() {
        if (this.saldo.compareTo(new BigDecimal("50.00")) < 0) {
            throw new ValidacaoException("Preço mínimo do serviço deve ser R$ 50,00");
        }
    }
}
