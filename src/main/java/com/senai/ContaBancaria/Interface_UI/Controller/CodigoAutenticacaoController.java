package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.CodigoAutenticacaoDTO;
import com.senai.ContaBancaria.Application.Service.AutenticacaoIotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/autenticacao")
@RequiredArgsConstructor
public class CodigoAutenticacaoController {

    private final AutenticacaoIotService service;

    @PostMapping("/{id}")
    public ResponseEntity<Void> solicitarCodigoAutenticacao(@PathVariable String id) {

        service.solicitarAutenticacao(id);

        return ResponseEntity.accepted().build(); // 202 - código IoT enviado
    }

    @PostMapping("/{id}/validar")
    public ResponseEntity<CodigoAutenticacaoDTO> receberValidacao(
            @PathVariable String id,
            @RequestParam String recebido,
            @RequestParam Boolean valido
    ) {
        var resposta = service.receberValidacao(id, recebido, valido);

        return ResponseEntity.ok(resposta); // 200 - validado com sucesso
    }
}