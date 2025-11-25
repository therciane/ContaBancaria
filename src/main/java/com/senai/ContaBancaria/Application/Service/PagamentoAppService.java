package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.PagamentoDTO;
import com.senai.ContaBancaria.Application.DTO.PagamentoMapper;
import com.senai.ContaBancaria.Application.DTO.ServicoDTO;
import com.senai.ContaBancaria.Domain.Entity.PagamentoEntity;
import com.senai.ContaBancaria.Domain.Repository.PagamentoRepository;
import com.senai.ContaBancaria.Domain.Repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class PagamentoAppService {

    private final PagamentoRepository repository;
    private final PagamentoMapper mapper;

    @GetMapping
    public List<PagamentoDTO> listarPagamentos(){
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List <PagamentoDTO> listarBoletosAPagar(){
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public PagamentoDTO buscarPorId(String id) {
        return mapper.toDTO(
                repository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Pagamento com ID " + id + " não encontrado."))
        );
    }
}
