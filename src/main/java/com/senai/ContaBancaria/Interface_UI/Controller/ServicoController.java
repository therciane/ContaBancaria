package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.ServicoDTO;
import com.senai.ContaBancaria.Application.Service.ServicoAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Serviços", description = "Gerenciamento de serviços da Conta Bancária")
@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoAppService service;

    @Operation(
            summary = "Cadastrar um novo Serviço",
            description = "Adiciona um novo serviço à base de dados após validações.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ServicoDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo válido",
                                    value = """
                                        {
                                          "id": "12345",
                                          "tipoConta": "POUPANCA",
                                          "saldo": 500.0
                                        }
                                        """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ServicoDTO> criar(@Valid @RequestBody ServicoDTO dto) {
        return ResponseEntity
                .status(201)
                .body(service.salvar(dto));
    }

    @Operation(
            summary = "Listar todos os serviços",
            description = "Retorna todos os serviços cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(
            summary = "Buscar serviço por ID",
            description = "Retorna um serviço existente a partir do seu ID",
            parameters = {
                    @Parameter(name = "id", description = "ID do serviço a ser buscado", example = "1")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity
                .ok(service.buscarPorId(id));
    }

    @Operation(
            summary = "Atualizar um serviço",
            description = "Atualiza os dados de um serviço existente com novas informações.",
            parameters = {
                    @Parameter(name = "id", description = "ID do serviço a ser atualizado", example = "1")
            },
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ServicoDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de atualização",
                                    value = """
                                        {
                                          "id": "12345",
                                          "tipoConta": "CORRENTE",
                                          "saldo": 200.0
                                        }
                                        """
                            )
                    )
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<ServicoDTO> atualizar(
            @PathVariable String id,
            @Valid @RequestBody ServicoDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(
            summary = "Deletar um serviço",
            description = "Remove um serviço através do ID informado.",
            parameters = {
                    @Parameter(name = "id", description = "ID do serviço a ser deletado", example = "1")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}