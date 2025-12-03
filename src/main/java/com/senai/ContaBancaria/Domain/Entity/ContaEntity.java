package com.senai.ContaBancaria.Domain.Entity;

import com.senai.ContaBancaria.Domain.Exceptions.SaldoInsuficienteException;
import com.senai.ContaBancaria.Domain.Exceptions.TransferenciaMesmaContaException;
import com.senai.ContaBancaria.Domain.Exceptions.ValoresNegativosException;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Ela é uma herança, que usamos quando haverá uma tabela. Parecido com a MappedSuperClass
@DiscriminatorColumn(
        name = "tipo_conta",
        discriminatorType = DiscriminatorType.STRING,
        length = 20
)
@Table(
        name = "conta",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_conta_numero",
                        columnNames = "numero"
                ),
                @UniqueConstraint(
                        name = "uk_cliente_tipo",
                        columnNames = {"cliente_id", "tipo_conta"}
                )
        }
)

public abstract class ContaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(nullable = false, precision = 20, scale = 2)
    private BigDecimal saldo;

    @Column(nullable = false)
    private boolean ativo;

    @ManyToOne(fetch = FetchType.LAZY) //Busca preguiçosa, só carrega o cliente quando for necessário
    @JoinColumn(
            name = "cliente_id",
            foreignKey = @ForeignKey(name = "fk_conta_cliente")
    )
    private ClienteEntity cliente;

    public abstract String getTipoConta();

    public void sacar(BigDecimal valor) {
        validarValorMaiorQueZero(valor, "saque");
        if (this.saldo.compareTo(valor) < 0) {
            throw new SaldoInsuficienteException();
        }
        this.saldo = this.saldo.subtract(valor);
    }

    public void depositar(BigDecimal valor) {
        validarValorMaiorQueZero(valor, "depósito");
        this.saldo = this.saldo.add(valor);
    }

    //protecded para que só possa ser usado dentro do pacote e por subclasses da herança
    protected static void validarValorMaiorQueZero(BigDecimal valor, String operacao) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValoresNegativosException(operacao);
        }
    }

    public void transferir(BigDecimal valor, ContaEntity contaDestino) {
        if (this.id.equals(contaDestino.getId()))
            throw new TransferenciaMesmaContaException();
        this.sacar(valor);
        contaDestino.depositar(valor);
    }
}