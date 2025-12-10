package com.senai.ContaBancaria.Domain.Service;

import com.senai.ContaBancaria.Application.DTO.PagamentoDTO;
import com.senai.ContaBancaria.Domain.Entity.CodigoAutenticacaoEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaEntity;
import com.senai.ContaBancaria.Domain.Entity.PagamentoEntity;
import com.senai.ContaBancaria.Domain.Entity.TaxaEntity;
import com.senai.ContaBancaria.Domain.Enum.StatusPagamento;
import com.senai.ContaBancaria.Domain.Exceptions.*;
import com.senai.ContaBancaria.Domain.Repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagamentoDomainService {

    ContaRepository repository;
    PagamentoEntity pagamentos;

    public PagamentoEntity processarPagamento(
            ContaEntity conta,
            String boleto,
            BigDecimal valorBoleto,
            List<TaxaEntity> taxas,
            CodigoAutenticacaoEntity codigoAutenticacao
    ) {

        validarConta(conta);
        validarBoleto(boleto, valorBoleto);
        validarCodigoIoT(codigoAutenticacao);

        BigDecimal valorFinal = calcularValorFinal(valorBoleto, taxas);

        StatusPagamento status = validarSaldo(conta, valorFinal);

        PagamentoEntity pagamento = PagamentoEntity.builder()
                .conta(conta)
                .boleto(boleto)
                .valorPago(valorBoleto)
                .dataPagamento(LocalDateTime.now())
                .status(status)
                .taxas(taxas)
                .build();

        if (status == StatusPagamento.SUCESSO) {
            conta.sacar(valorFinal);
        }

        return pagamento;
    }

    private void validarConta(ContaEntity conta) {
        if (conta == null || !conta.isAtivo()) {
            throw new EntidadeNaoEncontradaException("Conta");
        }
    }

    private void validarBoleto(String boleto, BigDecimal valor) {
        if (boleto == null || boleto.isBlank()) {
            throw new ValidacaoException("Boleto inválido.");
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValoresNegativosException("pagamento");
        }
    }

    private void validarCodigoIoT(CodigoAutenticacaoEntity codigo) {
        if (codigo == null) {
            throw new AutenticacaoIoTExpiradaException();
        }

        if (codigo.getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new AutenticacaoIoTExpiradaException();
        }

        if (!codigo.getValidado()) {
            throw new AutenticacaoIoTExpiradaException();
        }
    }

    private StatusPagamento validarSaldo(ContaEntity conta, BigDecimal valorFinal) {
        if (conta.getSaldo().compareTo(valorFinal) < 0) {
            return StatusPagamento.SALDO_INSUFICIENTE;
        }
        return StatusPagamento.SUCESSO;
    }

    public BigDecimal calcularValorFinal(BigDecimal valorBoleto, List<TaxaEntity> taxas) {

        BigDecimal totalTaxas = BigDecimal.ZERO;

        for (TaxaEntity taxa : taxas) {

            BigDecimal percentual = taxa.getPercentual() != null
                    ? valorBoleto.multiply(taxa.getPercentual())
                    : BigDecimal.ZERO;

            BigDecimal valorFixo = taxa.getValorFixo() != null
                    ? taxa.getValorFixo()
                    : BigDecimal.ZERO;

            totalTaxas = totalTaxas.add(percentual).add(valorFixo);
        }

        return valorBoleto.add(totalTaxas);
    }

    public PagamentoEntity realizarPagamento(PagamentoDTO pagamento, List<TaxaEntity> taxas) {
        ContaEntity conta = pagamento.conta();

        // Calcula total a pagar (valor boleto + taxas)
        BigDecimal totalTaxas = calcularValorFinal(pagamento.valorPago(), taxas);
        BigDecimal totalDebitar = pagamento.valorPago().add(totalTaxas);

        // saldo insuficiente → marca falha
        if (conta.getSaldo().compareTo(totalDebitar) < 0) {
            pagamentos.setStatus(StatusPagamento.SALDO_INSUFICIENTE);
            return pagamentos;
        }

        // Debita da conta
        conta.setSaldo(conta.getSaldo().subtract(totalDebitar));
        repository.save(conta);

        // Atualiza pagamento
        pagamentos.setStatus(StatusPagamento.SUCESSO);
        pagamentos.setDataPagamento(LocalDateTime.now());
        pagamentos.setTaxas(taxas);

        return pagamentos;
    }

}