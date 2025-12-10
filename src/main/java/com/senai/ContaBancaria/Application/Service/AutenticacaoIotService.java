package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.CodigoAutenticacaoDTO;
import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Entity.CodigoAutenticacaoEntity;
import com.senai.ContaBancaria.Domain.Exceptions.EntidadeNaoEncontradaException;
import com.senai.ContaBancaria.Domain.Repository.ClienteRepository;
import com.senai.ContaBancaria.Infrastructure.Rest.MqttGateway;
import com.senai.ContaBancaria.Domain.Exceptions.AutenticacaoIoTExpiradaException;
import com.senai.ContaBancaria.Domain.Repository.CodigoAutenticacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AutenticacaoIotService {

    private final MqttGateway mqttGateway;
    private final CodigoAutenticacaoRepository repository;
    private final ClienteRepository clienteRepository;

    public void solicitarAutenticacao(String id) {

        ClienteEntity cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente"));

        CodigoAutenticacaoEntity codigo = gerarOuRenovarCodigo(cliente);

        mqttGateway.publish(
                "banco/autenticacao/" + id,
                """
                {
                  "codigo": "%s",
                  "expiracao": "%s"
                }
                """.formatted(codigo.getCodigo(), codigo.getExpiraEm())
        );
    }

    public CodigoAutenticacaoDTO receberValidacao(String clienteId, String codigoRecebido, Boolean confirmado) {

        CodigoAutenticacaoEntity codigo = repository.findByClienteIdAndValidadoIsFalse(clienteId)
                .orElseThrow(AutenticacaoIoTExpiradaException::new);

        if (codigo.getExpiraEm().isBefore(LocalDateTime.now()))
            throw new AutenticacaoIoTExpiradaException();

        if (!confirmado || !codigo.getCodigo().equals(codigoRecebido))
            throw new AutenticacaoIoTExpiradaException();

        codigo.setValidado(true);
        repository.save(codigo);

        return CodigoAutenticacaoDTO.fromEntity(codigo);
    }

    private CodigoAutenticacaoEntity gerarOuRenovarCodigo(ClienteEntity cliente) {

        // tenta reaproveitar código não validado
        CodigoAutenticacaoEntity existente = repository.findByClienteIdAndValidadoIsFalse(cliente.getId())
                .orElse(null);

        if (existente != null) {
            existente.setCodigo(gerarCodigoAleatorio());
            existente.setExpiraEm(LocalDateTime.now().plusMinutes(1));
            return repository.save(existente);
        }

        // ou cria novo
        CodigoAutenticacaoEntity novo = CodigoAutenticacaoEntity.builder()
                .codigo(gerarCodigoAleatorio())
                .expiraEm(LocalDateTime.now().plusMinutes(1))
                .validado(false)
                .cliente(cliente)
                .build();

        return repository.save(novo);
    }

    private String gerarCodigoAleatorio() {
        SecureRandom random = new SecureRandom();
        int value = random.nextInt(900000) + 100000;
        return String.valueOf(value);
    }
}

