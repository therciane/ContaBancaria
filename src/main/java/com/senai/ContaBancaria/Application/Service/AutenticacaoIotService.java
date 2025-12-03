package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.CodigoAutenticacaoDTO;
import com.senai.ContaBancaria.Domain.Entity.CodigoAutenticacaoEntity;
import com.senai.ContaBancaria.Infrastructure.Rest.MqttGateway;
import com.senai.ContaBancaria.Domain.Exceptions.AutenticacaoIoTExpiradaException;
import com.senai.ContaBancaria.Domain.Repository.CodigoAutenticacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutenticacaoIotService {
    private final MqttGateway mqttGateway; // interface que você cria pra publicar MQTT
    private final CodigoAutenticacaoRepository repository;

    public void solicitarAutenticacao(UUID clienteId) {
        CodigoAutenticacaoEntity codigo = gerarOuSalvarCodigo(clienteId);

        mqttGateway.publish(
                "banco/autenticacao/" + clienteId,
                """
                {
                    "codigo": "%s",
                    "expiraEm": "%s"
                }
                """.formatted(codigo.getCodigo(), codigo.getExpiracao().toString())
        );
    }

    public CodigoAutenticacaoDTO receberValidacao(UUID clienteId, String codigoRecebido, Boolean confirmado) {
        CodigoAutenticacaoEntity codigo = repository.findByClienteIdAndValidadoIsFalse(clienteId)
                .orElseThrow(AutenticacaoIoTExpiradaException::new);

        boolean expirado = codigo.getExpiracao().isBefore(LocalDateTime.now());

        if (expirado || !confirmado || !codigo.getCodigo().equals(codigoRecebido)) {
            throw new AutenticacaoIoTExpiradaException();
        }

        codigo.setValidado(true);
        repository.save(codigo);
        return new CodigoAutenticacaoDTO(codigo.getId(), codigo.getCodigo(), codigo.getExpiracao(), codigo.getValidado());
    }

    private CodigoAutenticacaoEntity gerarOuSalvarCodigo(UUID clienteId){
        CodigoAutenticacaoEntity entity = new CodigoAutenticacaoEntity();
        entity.setCodigo(gerarCodigoAleatorio());
        entity.setExpiracao(LocalDateTime.now().plusMinutes(1));
        entity.setValidado(false);
        return repository.save(entity);
    }

    private String gerarCodigoAleatorio(){
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
