package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.ServicoDTO;
import com.senai.ContaBancaria.Domain.Entity.ServicoEntity;
import com.senai.ContaBancaria.Domain.Exceptions.EntidadeNaoEncontradaException;
import com.senai.ContaBancaria.Domain.Repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoAppService {

    private final ServicoRepository repository;

    public ServicoDTO salvar(ServicoDTO dto) {
        var servico = dto.toEntity();
        servico.validar();

        var salvo = repository.save(servico);
        return ServicoDTO.fromEntity(salvo);
    }

    public List<ServicoDTO> listar() {
        return repository.findAll()
                .stream()
                .map(ServicoDTO::fromEntity)
                .toList();
    }

    public ServicoDTO buscarPorId(String id) {
        var servico = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Serviço"));

        return ServicoDTO.fromEntity(servico);
    }

    public ServicoDTO atualizar(String id, ServicoDTO dto) {

        var existente = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Serviço"));

        // Atualiza apenas campos permitidos (evita recriar entidade do zero)
        existente.setTipoConta(dto.tipoConta());
        existente.setSaldo(dto.saldo());

        existente.validar();

        var atualizado = repository.save(existente);
        return ServicoDTO.fromEntity(atualizado);
    }

    public void deletar(String id) {
        var existe = repository.existsById(id);

        if (!existe) {
            throw new EntidadeNaoEncontradaException("Serviço");
        }

        repository.deleteById(id);
    }
}
