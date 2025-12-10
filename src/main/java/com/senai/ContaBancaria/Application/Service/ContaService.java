package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.ContaAutualizacao;
import com.senai.ContaBancaria.Application.DTO.ContaResumoDTO;
import com.senai.ContaBancaria.Application.DTO.TransferenciaDTO;
import com.senai.ContaBancaria.Domain.Entity.ContaCorrenteEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaPoupancaEntity;
import com.senai.ContaBancaria.Domain.Exceptions.EntidadeNaoEncontradaException;
import com.senai.ContaBancaria.Domain.Exceptions.RendimentoInvalidoException;
import com.senai.ContaBancaria.Domain.Exceptions.TransferenciaMesmaContaException;
import com.senai.ContaBancaria.Domain.Repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;

//Service so faz chamada de metodo
@Service
@RequiredArgsConstructor
@Transactional
public class ContaService {

    private final ContaRepository repository;

    @Transactional(readOnly = true)
    public List<ContaResumoDTO> listarTodasContas() {
        return repository.findAllByAtivoTrue().stream()
                .map(ContaResumoDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContaResumoDTO buscarContaPorNumero(String numero) {
        return ContaResumoDTO.fromEntity(
                repository.findByNumeroAndAtivoTrue(numero)
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("Conta"))
        );
    }

    @Transactional
    public ContaResumoDTO atualizarConta(String numeroDaConta, ContaAutualizacao dto) {

        var conta = buscarContaAtiva(numeroDaConta);

        conta.setSaldo(dto.saldo());

        if (conta instanceof ContaPoupancaEntity poupanca) {
            if (dto.rendimento() != null)
                poupanca.setRendimento(dto.rendimento());

        } else if (conta instanceof ContaCorrenteEntity corrente) {

            if (dto.limite() != null)
                corrente.setLimite(dto.limite());

            if (dto.taxa() != null)
                corrente.setTaxa(dto.taxa());
        }

        return ContaResumoDTO.fromEntity(repository.save(conta));
    }

    @Transactional
    public void deletarConta(String numeroDaConta) {
        var conta = buscarContaAtiva(numeroDaConta);
        conta.setAtivo(false);
        repository.save(conta);
    }

    @Transactional
    public ContaResumoDTO sacar(String numeroDaConta, BigDecimal valor) {
        var conta = buscarContaAtiva(numeroDaConta);
        conta.sacar(valor);
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }

    @Transactional
    public ContaResumoDTO depositar(String numeroDaConta, BigDecimal valor) {
        var conta = buscarContaAtiva(numeroDaConta);
        conta.depositar(valor);
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }

    @Transactional
    public ContaResumoDTO transferir(String numeroDaConta, TransferenciaDTO dto) {

        if (numeroDaConta.equals(dto.contaDestino()))
            throw new TransferenciaMesmaContaException();

        var origem = buscarContaAtiva(numeroDaConta);
        var destino = buscarContaAtiva(dto.contaDestino());

        origem.transferir(dto.valor(), destino);

        // apenas 1 transação já persiste tudo
        repository.save(destino);
        repository.save(origem);

        return ContaResumoDTO.fromEntity(origem);
    }

    @Transactional
    public ContaResumoDTO aplicarRendimento(String numeroDaConta) {
        var conta = buscarContaAtiva(numeroDaConta);

        if (conta instanceof ContaPoupancaEntity poupanca) {
            poupanca.aplicarRendimento();
            return ContaResumoDTO.fromEntity(repository.save(conta));
        }

        throw new RendimentoInvalidoException();
    }

    private ContaEntity buscarContaAtiva(String numero) {
        return repository.findByNumeroAndAtivoTrue(numero)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Conta"));
    }
}
