package com.icm.temperatura_bk_api.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * Componente responsable de suscribirse a topics MQTT y procesar mensajes recibidos.
 * Utiliza la librería Eclipse Paho para comunicación MQTT.
 */
@Component
@RequiredArgsConstructor
public class MqttSubscriber {

    private final IMqttClient mqttClient;
    private final MqttHandler mqttHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        // Lista de topics a los que deseas suscribirte automáticamente al iniciar
        String[] topics = {"example/topic"};
        subscribeToTopics(topics);
    }

    /**
     * Suscribe a un solo topic.
     *
     * @param topic nombre del topic
     */
    public void subscribeToTopic(String topic) {
        try {
            mqttClient.subscribe(topic, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());
                    System.out.println("✅ Mensaje recibido en el topic [" + topic + "]: " + payload);
                    mqttHandler.processJsonPayload(payload);
                }
            });
        } catch (MqttException e) {
            System.err.println("❌ Error al suscribirse al topic " + topic + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Suscribe a múltiples topics.
     *
     * @param topics array de topics
     */
    public void subscribeToTopics(String[] topics) {
        for (String topic : topics) {
            subscribeToTopic(topic);
        }
    }
}
