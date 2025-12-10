package com.senai.ContaBancaria.Interface_UI.Controller;


import com.senai.ContaBancaria.Application.DTO.DispositivoIotDTO;
import com.senai.ContaBancaria.Application.Service.DispositivoIotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/iot")
@RequiredArgsConstructor
public class DispositivoIotController {

    private final DispositivoIotService service;

    @PostMapping
    public ResponseEntity<DispositivoIotDTO> cadastrar(
            @Valid @RequestBody DispositivoIotDTO dto
    ) {
        var novo = service.salvar(dto);
        return ResponseEntity.created(URI.create("/api/iot/" + novo.id())).body(novo);
    }

    @GetMapping
    public ResponseEntity<List<DispositivoIotDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DispositivoIotDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DispositivoIotDTO> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody DispositivoIotDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

