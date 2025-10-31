package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@Table(name = "taxa")
@DiscriminatorValue("TAXA")

public class TaxaEntity extends ContaEntity {

    @NotBlank
    @Column (name = "descricao", nullable = false, length = 100)
    private String descricao;

    @DecimalMin("0.0")
    @NotNull
    @Column (name = "percentual", precision = 5, scale = 2)
    private BigDecimal percentual;

    @Column(name = "valor_fixo", precision = 10, scale = 2)
    private BigDecimal valorFixo = BigDecimal.valueOf(0.12);

    @Override
    public String getTipoConta() {
        return "TAXA";
    }
}
