package com.senai.ContaBancaria.Infrastructure.Rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.ContaBancaria.Application.Service.AutenticacaoIotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class IoTMqttListener {
    private final AutenticacaoIotService authService;

    @MqttSubscriber(topic = "banco/validacao/+")
    public void onValidacao(String topic, String payload) throws JsonProcessingException {
        String clienteId = topic.split("/")[2];

        JsonNode json = new ObjectMapper().readTree(payload);

        String codigo = json.get("codigo").asText();
        boolean confirmado = json.get("confirmado").asBoolean();

        authService.receberValidacao(UUID.fromString(clienteId), codigo, confirmado);
    }
}
