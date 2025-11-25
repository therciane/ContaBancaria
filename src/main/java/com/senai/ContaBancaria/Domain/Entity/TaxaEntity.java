package com.senai.ContaBancaria.Domain.Entity;

import com.senai.ContaBancaria.Domain.Enum.Descricao;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@DiscriminatorValue("TAXA")

public class TaxaEntity extends ContaEntity {

    @NotBlank
    @Column (name = "descricao", nullable = false, length = 100)
    private Descricao descricao;

    @DecimalMin("3.0")
    @NotNull
    @Column (name = "percentual", precision = 5, scale = 2)
    private BigDecimal percentual;

    @Column(name = "valor_fixo", precision = 10, scale = 2)
    private BigDecimal valorFixo;

    @ManyToMany(mappedBy = "taxas")
    private List<PagamentoEntity> pagamentos = new ArrayList<>();

    @Override
    public String getTipoConta() {
        return "TAXA";
    }
}
