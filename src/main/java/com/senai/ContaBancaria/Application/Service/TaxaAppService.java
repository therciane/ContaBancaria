package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.TaxaDTO;
import com.senai.ContaBancaria.Domain.Entity.TaxaEntity;
import com.senai.ContaBancaria.Domain.Exceptions.EntidadeNaoEncontradaException;
import com.senai.ContaBancaria.Domain.Repository.TaxaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TaxaAppService {

    private final TaxaRepository repository;

    @Transactional
    public TaxaDTO salvar(TaxaDTO dto) {
        var entity = dto.toEntity();
        var salvo = repository.save(entity);
        return TaxaDTO.fromEntity(salvo);
    }

    @Transactional(readOnly = true)
    public List<TaxaDTO> listar() {
        return repository.findAll()
                .stream()
                .map(TaxaDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaxaDTO buscarPorId(UUID id) {
        var taxa = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Taxa"));

        return TaxaDTO.fromEntity(taxa);
    }

    @Transactional
    public TaxaDTO atualizar(UUID id, TaxaDTO dto) {
        var existente = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Taxa"));

        existente.setDescricao(dto.descricao());
        existente.setPercentual(dto.percentual());
        existente.setValorFixo(dto.valorFixo());

        var salvo = repository.save(existente);
        return TaxaDTO.fromEntity(salvo);
    }

    @Transactional
    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Taxa");
        }
        repository.deleteById(id);
    }
}
