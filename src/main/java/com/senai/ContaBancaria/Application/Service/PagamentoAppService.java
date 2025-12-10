package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.PagamentoDTO;
import com.senai.ContaBancaria.Application.DTO.PagamentoMapper;
import com.senai.ContaBancaria.Application.DTO.ServicoDTO;
import com.senai.ContaBancaria.Domain.Entity.PagamentoEntity;
import com.senai.ContaBancaria.Domain.Enum.StatusPagamento;
import com.senai.ContaBancaria.Domain.Exceptions.EntidadeNaoEncontradaException;
import com.senai.ContaBancaria.Domain.Repository.PagamentoRepository;
import com.senai.ContaBancaria.Domain.Repository.ServicoRepository;
import com.senai.ContaBancaria.Domain.Service.PagamentoDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
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

    public PagamentoDTO realizarPagamento(PagamentoDTO p) {
        var pagamento = pagamentoDomainService.realizarPagamento(
                p, p.taxas()// taxas vêm do DTO, mas idealmente você buscaria do banco
        );

        return mapper.toDTO(pagamento);
    }

    public PagamentoDTO pagarBoleto(String id) {

        // buscar pagamento pendente
        var pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pagamento"));

        // Já foi pago?
        if (pagamento.getStatus() == StatusPagamento.SUCESSO) {
            return mapper.toDTO(pagamento);
        }

        // Está vencido?
        if (pagamento.getDataPagamento().isBefore(LocalDateTime.now().minusDays(1))) {
            pagamento.setStatus(StatusPagamento.BOLETO_VENCIDO);
            pagamentoRepository.save(pagamento);
            return mapper.toDTO(pagamento);
        }

        // Chama o domain service para tentar pagar
        PagamentoEntity resultado = pagamentoDomainService.realizarPagamento(
                PagamentoDTO.fromEntity(pagamento),
                pagamento.getTaxas()
        );

        // Atualiza status conforme resultado
        pagamento.setStatus(resultado.getStatus());
        pagamento.setDataPagamento(LocalDateTime.now());

        pagamentoRepository.save(pagamento);

        return mapper.toDTO(pagamento);
    }

}

