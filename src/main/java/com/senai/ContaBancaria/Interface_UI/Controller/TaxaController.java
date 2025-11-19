package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.TaxaDTO;
import com.senai.ContaBancaria.Application.Service.TaxaAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/taxas")
@RequiredArgsConstructor
@Controller
public class TaxaController {

    private final TaxaAppService service;

    @PostMapping
    public ResponseEntity<TaxaDTO> criarSalvarTaxa(@RequestBody TaxaDTO dto){
        return ResponseEntity.ok(service.salvar(dto));
    }

    @GetMapping
    public ResponseEntity <List <TaxaDTO>> listarClientesAtivos(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxaDTO> buscar(@PathVariable String id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxaDTO> atualizar(@RequestBody TaxaDTO dto, @PathVariable String id, @PathVariable BigDecimal valor){
        return ResponseEntity.ok(service.atualizar(id, valor, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
