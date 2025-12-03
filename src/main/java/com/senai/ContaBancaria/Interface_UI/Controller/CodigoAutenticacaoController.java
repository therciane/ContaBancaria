package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.CodigoAutenticacaoDTO;
import com.senai.ContaBancaria.Application.Service.AutenticacaoIotService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class CodigoAutenticacaoController {

    private AutenticacaoIotService service;

    @PostMapping("/{id}")
    public ResponseEntity<CodigoAutenticacaoDTO> solicitarCodigoAutenticacao(@PathVariable UUID id) {
        service.solicitarAutenticacao(id);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/validar")
    public ResponseEntity<CodigoAutenticacaoDTO> receberValidacao(
            @PathVariable UUID id,
            @RequestParam String recebido,
            @RequestParam Boolean valido
    ) {
        return ResponseEntity.ok(service.receberValidacao(id, recebido, valido));
    }

}
