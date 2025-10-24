package com.senai.ContaBancaria.Domain.Entity;

import com.senai.ContaBancaria.Domain.Exceptions.ValidacaoException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String tipoConta;
    private Double saldo;

    public void validar() {
        if (this.getSaldo() < 50)
            throw new ValidacaoException("Preço mínimo do serviço deve ser R$ 50,00");
    }
}
