package com.senai.ContaBancaria.Domain.Service;

import com.senai.ContaBancaria.Application.DTO.TaxaDTO;
import com.senai.ContaBancaria.Domain.Entity.ContaEntity;
import com.senai.ContaBancaria.Domain.Entity.PagamentoEntity;
import com.senai.ContaBancaria.Domain.Entity.TaxaEntity;
import com.senai.ContaBancaria.Domain.Enum.StatusPagamento;
import com.senai.ContaBancaria.Domain.Exceptions.SaldoInsuficienteException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagamentoDomainService {

    private final ContaEntity conta;
    private final PagamentoEntity pagamento;
    List<TaxaEntity> taxas;

    public PagamentoDomainService(ContaEntity conta, PagamentoEntity pagamento) {
        this.conta = conta;
        this.pagamento = pagamento;
    }


    public PagamentoEntity realizarPagamento(TaxaDTO dto) {

        BigDecimal valorOriginal = pagamento.getValorPago();
        BigDecimal totalTaxas = calcularTotalTaxa(valorOriginal, taxas);
        BigDecimal totalDebitar = valorOriginal.add(totalTaxas);

        // saldo < totalDebitar ?
        if (conta.getSaldo().compareTo(totalDebitar) < 0) {
            pagamento.setStatusPagamento(StatusPagamento.SALDO_INSUFICIENTE);
            throw new SaldoInsuficienteException();
        }

        // debita o saldo corretamente
        conta.setSaldo(conta.getSaldo().subtract(totalDebitar));

        pagamento.setValorPago(totalDebitar);
        pagamento.setStatusPagamento(StatusPagamento.SUCESSO);
        pagamento.setData(LocalDateTime.now());

        return pagamento;
    }

    public BigDecimal calcularTotalTaxa(BigDecimal valor, List<TaxaEntity> taxas) {

        if (taxas == null || taxas.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (TaxaEntity taxa : taxas) {
            BigDecimal percentual = taxa.getPercentual() != null
                    ? taxa.getPercentual()
                    : BigDecimal.ZERO;

            BigDecimal valorFixo = taxa.getValorFixo() != null
                    ? taxa.getValorFixo()
                    : BigDecimal.ZERO;

            BigDecimal valorPercentual =
                    valor.multiply(percentual).divide(new BigDecimal("100"));

            BigDecimal taxaCalculada = valorPercentual.add(valorFixo);

            total = total.add(taxaCalculada);
        }

        return total;
    }
}


//fará aplicação da Taxa, as suas regras de negócio