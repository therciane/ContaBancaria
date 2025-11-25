package com.senai.ContaBancaria.Domain.Service;

import com.senai.ContaBancaria.Application.DTO.PagamentoDTO;
import com.senai.ContaBancaria.Application.DTO.TaxaDTO;
import com.senai.ContaBancaria.Domain.Entity.ContaEntity;
import com.senai.ContaBancaria.Domain.Entity.PagamentoEntity;
import com.senai.ContaBancaria.Domain.Entity.TaxaEntity;
import com.senai.ContaBancaria.Domain.Enum.StatusPagamento;
import com.senai.ContaBancaria.Domain.Exceptions.SaldoInsuficienteException;
import com.senai.ContaBancaria.Domain.Repository.ContaRepository;
import com.senai.ContaBancaria.Domain.Repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoDomainService {

    private final ContaRepository contaRepository;
    private final PagamentoRepository pagamentoRepository;

    public PagamentoEntity realizarPagamento(PagamentoDTO dto, List<TaxaEntity> taxas) {

        // 1 — Buscar conta
        ContaEntity conta = contaRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        // 2 — Calcular total
        BigDecimal totalTaxas = calcularTotalTaxa(conta.getSaldo(), taxas);
        BigDecimal totalDebitar = conta.getSaldo().add(totalTaxas);

        // 3 — Validar saldo
        if (conta.getSaldo().compareTo(totalDebitar) < 0) {
            PagamentoEntity pagamentoFalho = new PagamentoEntity();
            pagamentoFalho.setStatusPagamento(StatusPagamento.SALDO_INSUFICIENTE);
            return pagamentoFalho;
        }

        // 4 — Debitar
        conta.setSaldo(conta.getSaldo().subtract(totalDebitar));
        contaRepository.save(conta);

        // 5 — Criar pagamento
        PagamentoEntity pagamento = new PagamentoEntity();
        pagamento.setConta(conta);
        pagamento.setValorPago(totalDebitar);
        pagamento.setData(LocalDateTime.now());
        pagamento.setStatusPagamento(StatusPagamento.SUCESSO);
        pagamento.setTaxas(taxas);

        return pagamentoRepository.save(pagamento);
    }

    public BigDecimal calcularTotalTaxa(BigDecimal valor, List<TaxaEntity> taxas) {

        if (taxas == null || taxas.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (TaxaEntity taxa : taxas) {

            BigDecimal percentual = taxa.getPercentual() != null ? taxa.getPercentual() : BigDecimal.ZERO;
            BigDecimal valorFixo = taxa.getValorFixo() != null ? taxa.getValorFixo() : BigDecimal.ZERO;

            BigDecimal valorPercentual = valor.multiply(percentual).divide(new BigDecimal("100"));

            total = total.add(valorPercentual.add(valorFixo));
        }

        return total;
    }
}



//fará aplicação da Taxa, as suas regras de negócio