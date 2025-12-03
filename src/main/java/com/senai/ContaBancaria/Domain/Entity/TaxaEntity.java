package com.senai.ContaBancaria.Domain.Entity;

import com.senai.ContaBancaria.Domain.Enum.Descricao;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "taxa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String descricao;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal percentual;

    @Column(name = "valor_fixo", precision = 10, scale = 2)
    private BigDecimal valorFixo;

    @ManyToMany(mappedBy = "taxas")
    private Set<PagamentoEntity> pagamentos = new HashSet<>();
}
