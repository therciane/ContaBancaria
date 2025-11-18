package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.PagamentoDTO;
import com.senai.ContaBancaria.Application.Service.PagamentoAppService;
import com.senai.ContaBancaria.Domain.Entity.PagamentoEntity;
import com.senai.ContaBancaria.Domain.Service.PagamentoDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoAppService appService;
    private final PagamentoDomainService domainService;

    public ResponseEntity<PagamentoDTO> realizarPagamento(){

    }

    public ResponseEntity<PagamentoDTO> efetivarPagamento(@RequestBody PagamentoEntity dto){
        return ResponseEntity.ok(domainService.pagamentoEfetivado());
    }

    public ResponseEntity<PagamentoEntity> buscarPagamentoPorId(@RequestBody PagamentoDTO dto){

    }

    public ResponseEntity<List<PagamentoDTO>> listarPagamentos(){
        return ResponseEntity.ok(appService.listarPagamentos());
    }

}
