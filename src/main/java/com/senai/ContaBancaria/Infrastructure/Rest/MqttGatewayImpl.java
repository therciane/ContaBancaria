package com.senai.ContaBancaria.Infrastructure.Rest;

import lombok.RequiredArgsConstructor;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MqttGatewayImpl implements MqttGateway {

    private final MqttPahoMessageHandler mqttHandler;

    @Override
    public void publish(String topic, String payload) {
        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader(MqttHeaders.TOPIC, topic)
                .build();

        mqttHandler.handleMessage(message);
    }
}