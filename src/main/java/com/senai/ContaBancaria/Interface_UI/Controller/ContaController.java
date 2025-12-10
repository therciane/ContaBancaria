package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.ContaAutualizacao;
import com.senai.ContaBancaria.Application.DTO.ContaResumoDTO;
import com.senai.ContaBancaria.Application.DTO.TransferenciaDTO;
import com.senai.ContaBancaria.Application.DTO.ValorSaqueDepositoDTO;
import com.senai.ContaBancaria.Application.Service.ContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService service;

    @GetMapping
    public ResponseEntity<List<ContaResumoDTO>> listarTodasContas() {
        return ResponseEntity.ok(service.listarTodasContas());
    }

    @GetMapping("/{numeroDaConta}")
    public ResponseEntity<ContaResumoDTO> buscarContaPorNumero(@PathVariable String numeroDaConta) {
        return ResponseEntity.ok(service.buscarContaPorNumero(numeroDaConta));
    }

    @PutMapping("/{numeroDaConta}")
    public ResponseEntity<ContaResumoDTO> atualizarConta(
            @PathVariable String numeroDaConta,
            @Valid @RequestBody ContaAutualizacao dto
    ) {
        return ResponseEntity.ok(service.atualizarConta(numeroDaConta, dto));
    }

    @DeleteMapping("/{numeroDaConta}")
    public ResponseEntity<Void> deletarConta(@PathVariable String numeroDaConta) {
        service.deletarConta(numeroDaConta);
        return ResponseEntity.noContent().build(); // 204
    }

    @PostMapping("/{numeroDaConta}/sacar")
    public ResponseEntity<ContaResumoDTO> sacar(
            @PathVariable String numeroDaConta,
            @Valid @RequestBody ValorSaqueDepositoDTO dto
    ) {
        return ResponseEntity.ok(service.sacar(numeroDaConta, dto.valor()));
    }

    @PostMapping("/{numeroDaConta}/depositar")
    public ResponseEntity<ContaResumoDTO> depositar(
            @PathVariable String numeroDaConta,
            @Valid @RequestBody ValorSaqueDepositoDTO dto
    ) {
        return ResponseEntity.ok(service.depositar(numeroDaConta, dto.valor()));
    }

    @PostMapping("/{numeroDaConta}/transferir")
    public ResponseEntity<ContaResumoDTO> transferir(
            @PathVariable String numeroDaConta,
            @Valid @RequestBody TransferenciaDTO dto
    ) {
        return ResponseEntity.ok(service.transferir(numeroDaConta, dto));
    }
}
