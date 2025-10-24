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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Serviços", description = "Gerenciamento de serviços da Conta Bancária")
@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoAppService service;

    public ServicoController(ServicoAppService service) {
        this.service = service;
    }

    @Operation(
            summary = "Cadastrar um novo Cliente",
            description = "Adiciona um novo serviço à base de dados após validações de saldo",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ServicoDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                          "Número da Conta": "123456",
                                          "tipo de conta": "Conta Poupança",
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
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "saldo inválido", value = "\"saldo mínimo do serviço deve ser R$ 1,00 R$\""),
                                            @ExampleObject(name = "Conta Inexistente", value = "\"Sua Conta não existe, por favor entrar em contato com o suporte \""),
                                            @ExampleObject(name = "Conta Duplicada", value = "\"Sua foi duplicada\""),
                                            @ExampleObject(name = "", value = "\".\"")
                                    }
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ServicoDTO> criar(@Valid @org.springframework.web.bind.annotation.RequestBody ServicoDTO dto) {
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
    public ResponseEntity<List<ServicoDTO>>listar() {
        return ResponseEntity
                .ok(service.listar());

    }

    @Operation(
            summary = "Buscar serviço por ID",
            description = "Retorna um serviço existente a partir do seu ID",
            parameters = {
                    @Parameter(name = "id", description = "ID da conta a ser buscado", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Serviço encontrado"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Serviço não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Conta com ID sfrtg456 não encontrado.\"")
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity
                .ok(service.buscarPorId(id));
    }

    @Operation(
            summary = "Atualizar um serviço",
            description = "Atualiza os dados de um serviço existente com novas informações",
            parameters = {
                    @Parameter(name = "id", description = "ID do serviço a ser atualizado", example = "1")
            },
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ServicoDTO.class),
                            examples = @ExampleObject(name = "Exemplo de atualização", value = """
                        {
                          "descricao": "Revisão completa",
                          "saldo": 200.0
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "saldo inválido", value = "\"Preço mínimo do serviço deve ser R$ 1,00 R$\""),
                                            @ExampleObject(name = "Numero da Conta", value = "\"Numero da conta invalido\"")
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Serviço não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Serviço com ID 99 não encontrado.\"")
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ServicoDTO> atualizar(@PathVariable Long id, @Valid @org.springframework.web.bind.annotation.RequestBody ServicoDTO dto) {
        return ResponseEntity
                .ok(service.atualizar(id, dto));
    }

    @Operation(
            summary = "Deletar um serviço",
            description = "Remove sua conta através do numero da conta",
            parameters = {
                    @Parameter(name = "id", description = "ID da conta a ser deletada", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Serviço removido com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Serviço não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Serviço com ID 99 não encontrado.\"")
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}

