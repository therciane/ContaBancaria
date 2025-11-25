package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.PagamentoDTO;
import com.senai.ContaBancaria.Application.DTO.TaxaDTO;
import com.senai.ContaBancaria.Application.Service.PagamentoAppService;
import com.senai.ContaBancaria.Domain.Entity.PagamentoEntity;
import com.senai.ContaBancaria.Domain.Entity.TaxaEntity;
import com.senai.ContaBancaria.Domain.Service.PagamentoDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoAppService appService;
    private final PagamentoDomainService domainService;

    // REALIZAR PAGAMENTO
    @PostMapping
    public ResponseEntity<PagamentoEntity> realizarPagamento(@PathVariable PagamentoDTO dto, @RequestBody List<TaxaEntity> taxas) {
        return ResponseEntity.ok(domainService.realizarPagamento(dto, taxas));
    }

    // LISTAR TODOS OS PAGAMENTOS
    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> listarPagamentos() {
        return ResponseEntity.ok(appService.listarPagamentos());
    }

    //Acha o boleto e lista ele
    @GetMapping("/boletos")
    public ResponseEntity<List<PagamentoDTO>> listarBoletosAPagar() {
        return ResponseEntity.ok(appService.listarBoletosAPagar());
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> buscarPagamentoPorId(@PathVariable String id) {
        return ResponseEntity.ok(appService.buscarPorId(id));
    }
}

