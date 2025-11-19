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

@Service
@RequiredArgsConstructor
@Transactional

public class TaxaAppService {

    private final TaxaRepository repository;

    public TaxaAppService(TaxaRepository repository, TaxaDTO dto) {
        this.repository = repository;
    }

    public TaxaDTO salvar(TaxaDTO dto) {
        TaxaEntity entity = dto.toEntity();
        TaxaEntity saved = repository.save(entity);
        return dto.fromEntity(saved);
    }


    public List<TaxaDTO> listar() {
        return repository.findAll()
                .stream()
                .map(TaxaDTO::fromEntity)
                .toList();
    }

    public TaxaDTO buscarPorId(String id) {
        return TaxaDTO.fromEntity(
                repository.findById(id)
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("Taxa com ID " + id + " não encontrada."))
        );
    }

    public TaxaDTO atualizar(String id, BigDecimal valor, TaxaDTO dtoAtualizado) {
        TaxaEntity existente = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Taxa não encontrada"));

        existente.setPercentual(dtoAtualizado.percentual());
        existente.setValorFixo(dtoAtualizado.valorFixo());
        existente.setDescricao(dtoAtualizado.descricao());

        TaxaEntity salvo = repository.save(existente);

        return TaxaDTO.fromEntity(salvo);
    }

    public void deletar(String id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Taxa com ID " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }

}
