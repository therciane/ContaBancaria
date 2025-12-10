package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.GerenteDTO;
import com.senai.ContaBancaria.Application.Service.GerenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gerentes")
@RequiredArgsConstructor
public class GerenteController {

    private final GerenteService service;

    @GetMapping
    public ResponseEntity<List<GerenteDTO>> listarTodosGerentes() {
        return ResponseEntity.ok(service.listarTodosGerentes());
    }

    @PostMapping
    public ResponseEntity<GerenteDTO> cadastrarGerente(@Valid @RequestBody GerenteDTO dto) {
        GerenteDTO gerenteCriado = service.cadastrarGerente(dto);

        return ResponseEntity.created(
                URI.create("/api/gerentes/" + gerenteCriado.id())
        ).body(gerenteCriado);
    }
}

