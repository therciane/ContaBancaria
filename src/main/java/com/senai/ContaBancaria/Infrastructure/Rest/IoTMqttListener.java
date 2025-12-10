package com.senai.ContaBancaria.Infrastructure.Rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import com.senai.ContaBancaria.Application.Service.AutenticacaoIotService;

@Component
@RequiredArgsConstructor
public class IoTMqttListener {

    private final AutenticacaoIotService authService;
    private final ObjectMapper mapper = new ObjectMapper();

    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public void handleMessage(Message<String> message) throws Exception {

        String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
        String payload = message.getPayload();

        String clienteId = topic.split("/")[2];

        JsonNode json = mapper.readTree(payload);

        String codigo = json.get("codigo").asText();
        boolean confirmado = json.get("confirmado").asBoolean();

        authService.receberValidacao(
                (clienteId),
                codigo,
                confirmado
        );
    }
}


