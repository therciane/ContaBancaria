package com.senai.ContaBancaria.Infrastructure.Rest;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {
    void publish(String topic, String payload);
}