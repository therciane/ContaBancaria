package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Ela é uma herança, que usamos quando haverá uma tabela. Parrecido com a MappedSuperClass
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING, length = 20) //Discrimina o tipo de classe que estu salvando
@Table(
        name = "conta",
        uniqueConstraints = { @UniqueConstraint(name = "uk_conta_numero", columnNames = "numero"),
        @UniqueConstraint(name = "uk_cliente_tipo", columnNames = {"cliente_id", "tipo_conta"})
        }
)
@SuperBuilder //para heranças

public abstract class ContaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String numeroConta; //Associação de informações.

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal saldo; //BigDecimal para números decimais, como dinheiro

    @Column(nullable = false)
    private boolean ativo;

    @ManyToOne(fetch = FetchType.LAZY) // Busca preguiçosa, quando achar tal coisa at
    @JoinColumn (
            name = "cliente_id",
            foreignKey = @ForeignKey(name = "fk_conta_cliente")
    )
    private ClienteEntity cliente;

    public abstract String getTipoConta();

    public void sacar(BigDecimal valor) {
        //Aqui fala do valor do saque
        validarValorMaiorQueZero(valor);
        //Aqui fala do saldo, assim não deixando o saldo ficar negativo
        if (this.saldo.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para saque.");
        }
        this.saldo = this.saldo.subtract(valor); //substrai o valor do saldo
    }

    public void depositar(BigDecimal valor){
        validarValorMaiorQueZero(valor);
        this.saldo = this.saldo.add(valor);
    }

    //protecded para que só possa ser usado dentro do pacote e por subclasses da herança
    protected static void validarValorMaiorQueZero(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da operação deve ser maior que zero.");
        }
    }

    public void transferir(BigDecimal valor, ContaEntity contaDestino) {
        if (this.id.equals(contaDestino.getId()))
            throw new IllegalArgumentException("Não é possível transferir para a mesma conta.");
        this.sacar(valor);
        contaDestino.depositar(valor);
    }
}