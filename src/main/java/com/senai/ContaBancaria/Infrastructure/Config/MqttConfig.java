package com.senai.ContaBancaria.Infrastructure.Config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class MqttConfig {

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{"tcp://localhost:1883"});
        options.setCleanSession(true);
        return options;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory(MqttConnectOptions options) {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(options);
        return factory;
    }

    // Canal para ENVIAR mensagens
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    // Handler para ENVIAR mensagens via MQTT
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MqttPahoMessageHandler mqttOutbound(MqttPahoClientFactory factory) {
        MqttPahoMessageHandler handler =
                new MqttPahoMessageHandler("backend-publisher", factory);
        handler.setAsync(true);
        return handler;
    }

    // Canal para RECEBER mensagens
    @Bean
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }

    // Adapter que ESCUTA tópicos MQTT
    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttInbound(
            MqttPahoClientFactory factory
    ) {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        "backend-listener",
                        factory,
                        "banco/validacao/+"
                );

        adapter.setOutputChannel(mqttInboundChannel());
        adapter.setQos(1);

        return adapter;
    }
}


