package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.PagamentoDTO;
import com.senai.ContaBancaria.Application.Service.PagamentoAppService;
import com.senai.ContaBancaria.Domain.Entity.PagamentoEntity;
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

    @PostMapping
    public ResponseEntity<PagamentoEntity> realizarPagamento(@RequestBody PagamentoDTO dto){
        return ResponseEntity.ok(domainService.listarPagamentos(dto));
    }

    @GetMapping("/efetivar")
    public ResponseEntity<PagamentoEntity> efetivarPagamento(@RequestBody PagamentoDTO dto){
        return ResponseEntity.ok(domainService.pagamentoEfetivado(dto));
    }

    @GetMapping("/calcularSaldo")
    public ResponseEntity<PagamentoEntity> calcularSaldoPosPagamento(@RequestBody PagamentoDTO dto){
        return ResponseEntity.ok(domainService.calcularValorFinalDebitado(dto));
    }

    @PostMapping("/cancelar")
    public ResponseEntity<PagamentoEntity> cancelarPagamentoSaldoInsuficiente(@RequestBody PagamentoDTO dto){
        return ResponseEntity.ok(domainService.cancelarFaturaSaldoInsuficiente(dto));
    }

    @GetMapping("/boletos")
    public ResponseEntity<List <PagamentoEntity>> listarBoletos (@RequestBody PagamentoDTO dto){
        return ResponseEntity.ok(appService.listarBoletosAPagar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoEntity> buscarPagamentoPorId(@RequestBody PagamentoDTO dto, @PathVariable String id){
        return ResponseEntity.ok(appService.listarPagamentos(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<PagamentoEntity>> listarPagamentos(){
        return ResponseEntity.ok(appService.listarPagamentos(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        appService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
