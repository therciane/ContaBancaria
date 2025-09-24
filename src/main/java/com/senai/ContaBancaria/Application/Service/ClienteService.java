package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.ClienteCadastroDTO;
import com.senai.ContaBancaria.Application.DTO.ClienteResponseDTO;
import com.senai.ContaBancaria.Domain.Repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//Isso que é um bean de serviço
//Responsável pela lógica de negócio
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    //Criar clente
    public ClienteResponseDTO registrarClienteOuAnexarConta(ClienteCadastroDTO dto) {

        //var cria variavel sem ter que definir o tipo
        var cliente = repository.findByCpfAndAtivoTrue(dto.cpf()).orElseGet(() -> repository.save(dto.toEntity()));

        var contas = cliente.getConta();
        var novaConta = dto.contaDTO().toEntity(cliente);

        boolean jaPossuiContaDoTipo = contas.stream() //stream transforma a lista em um fluxo de dados
                .anyMatch(c -> c.getClass().equals(novaConta.getClass()) && c.isAtivo()); //anyMach verifica se algum elemento do fluxo satisfaz a condição
        //Isso é polimorfismo
        if (jaPossuiContaDoTipo) {
            throw new RuntimeException("Cliente já possui uma conta ativa do tipo " + dto.contaDTO().tipoConta()); //Lança uma exceção. Funciona quando não precisa ser tratada
        }
        cliente.getConta().add(novaConta);
        repository.save(cliente);
        return ClienteResponseDTO.fromEntity(repository.save(cliente));

        return null;
    }


}
