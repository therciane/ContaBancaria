package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.PagamentoDTO;
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

    private final PagamentoDTO dto;
    private final PagamentoRepository repository;

    public PagamentoAppService (PagamentoRepository repository, PagamentoDTO pgdto) {
        this.repository = repository;
        this.dto = pgdto;
    }

    public PagamentoDTO realizarPagamento(){

    }

    public PagamentoDTO pagamentoEfetivado(){

    }

    @GetMapping
    public List<PagamentoDTO> listarPagamentos(){
        return repository.findAll()
                .stream()
                .map(dto::fromEntity)
                .toList();
    }

}
