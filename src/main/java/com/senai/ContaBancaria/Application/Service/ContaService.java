package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.ContaAutualizacao;
import com.senai.ContaBancaria.Application.DTO.ContaResumoDTO;
import com.senai.ContaBancaria.Application.DTO.TransferenciaDTO;
import com.senai.ContaBancaria.Application.DTO.ValorSaqueDepositoDTO;
import com.senai.ContaBancaria.Domain.Entity.ContaCorrenteEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaPoupancaEntity;
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
        return repository.findAllByAtivaTrue().stream()
                .map(ContaResumoDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContaResumoDTO buscarContaPorNumero(String numero) {
        return ContaResumoDTO.fromEntity(
                repository.findByNumeroAndAtivaTrue(numero)
                        .orElseThrow(() -> new RuntimeException("Conta não encontrada"))
        );
    }

    public ContaResumoDTO atualizarConta(String numeroDaConta, ContaAutualizacao dto) {
        var conta = buscaContaAtivaPorNumero(numeroDaConta);

        if (conta instanceof ContaPoupancaEntity poupanca){
            poupanca.setRendimento(dto.rendimento());
        } else if (conta instanceof ContaCorrenteEntity corrente) {
            corrente.setLimite(dto.limite());
            corrente.setTaxa(dto.taxa());

        }

        conta.setSaldo(dto.saldo());

        return ContaResumoDTO.fromEntity(repository.save(conta));
    }

    public void deletarConta(String numeroDaConta) {
        var conta = buscaContaAtivaPorNumero(numeroDaConta);
        conta.setAtivo(false);
        repository.save(conta);
    }

    //Metodo para evitar repetição de código = clean code
    private ContaEntity buscaContaAtivaPorNumero(String numeroDaConta) {
        var conta = repository.findByNumeroAndAtivaTrue(numeroDaConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        return conta;
    }

    public ContaResumoDTO sacar(String numeroDaConta, BigDecimal valor) {
        var conta = buscaContaAtivaPorNumero(numeroDaConta);
        conta.sacar(valor);

        return ContaResumoDTO.fromEntity(repository.save(conta));
    }

    public ContaResumoDTO depositar(String numeroDaConta, ValorSaqueDepositoDTO dto) {
        var conta = buscaContaAtivaPorNumero(numeroDaConta);
        conta.depositar(dto);

        return ContaResumoDTO.fromEntity(repository.save(conta));
    }

    public ContaResumoDTO transferir(String numeroDaConta, TransferenciaDTO dto) {

        var contaOrigem = buscaContaAtivaPorNumero(numeroDaConta);
        var contaDestino = buscaContaAtivaPorNumero(dto.contaDestino());

        contaOrigem.transferir(dto.valor(), contaDestino);
        repository.save(contaDestino);
        return ContaResumoDTO.fromEntity(repository.save(contaOrigem));
    }


}