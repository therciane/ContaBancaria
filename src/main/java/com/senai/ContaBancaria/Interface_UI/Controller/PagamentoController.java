package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.PagamentoDTO;
import com.senai.ContaBancaria.Application.Service.PagamentoAppService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoAppService appService;

    // REALIZAR PAGAMENTO
    @PostMapping
    public ResponseEntity<PagamentoDTO> realizarPagamento(
            @Valid @RequestBody PagamentoDTO dto
    ) {
        PagamentoDTO realizado = appService.realizarPagamento(dto);
        return ResponseEntity.ok(realizado);
    }

    // LISTAR TODOS OS PAGAMENTOS
    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> listarPagamentos() {
        return ResponseEntity.ok(appService.listarPagamentos());
    }

    // LISTAR APENAS BOLETOS
    @GetMapping("/boletos")
    public ResponseEntity<PagamentoDTO> pagarBoleto(@PathVariable String id) {
        return ResponseEntity.ok(appService.pagarBoleto(id));
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> buscarPagamentoPorId(@PathVariable String id) {
        return ResponseEntity.ok(appService.buscarPorId(id));
    }
}


