package com.senai.ContaBancaria.Infrastructure.Rest;

public interface MqttGateway {
    void publish(String topic, String payload);
}
