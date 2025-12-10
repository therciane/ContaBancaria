package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.PagamentoDTO;
import com.senai.ContaBancaria.Application.DTO.PagamentoMapper;
import com.senai.ContaBancaria.Application.DTO.ServicoDTO;
import com.senai.ContaBancaria.Domain.Entity.PagamentoEntity;
import com.senai.ContaBancaria.Domain.Exceptions.EntidadeNaoEncontradaException;
import com.senai.ContaBancaria.Domain.Repository.PagamentoRepository;
import com.senai.ContaBancaria.Domain.Repository.ServicoRepository;
import com.senai.ContaBancaria.Domain.Service.PagamentoDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PagamentoAppService {

    private final PagamentoRepository pagamentoRepository;
    private final PagamentoDomainService pagamentoDomainService;
    private final PagamentoMapper mapper;

    @Transactional(readOnly = true)
    public List<PagamentoDTO> listarPagamentos() {
        return pagamentoRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PagamentoDTO buscarPorId(String id) {
        var pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pagamento"));

        return mapper.toDTO(pagamento);
    }

    public PagamentoDTO realizarPagamento(PagamentoEntity p) {
        var pagamento = pagamentoDomainService.realizarPagamento(
                p, p.getTaxas()// taxas vêm do DTO, mas idealmente você buscaria do banco
        );

        return mapper.toDTO(pagamento);
    }
}

