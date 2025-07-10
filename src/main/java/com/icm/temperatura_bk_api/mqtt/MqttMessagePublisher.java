package com.icm.temperatura_bk_api.mqtt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
public class MqttMessagePublisher {

    private final IMqttClient mqttClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MqttMessagePublisher(IMqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public void publishText(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(1);
            mqttMessage.setRetained(true);
            mqttClient.publish(topic, mqttMessage);
        } catch (MqttException e) {
            System.err.println("❌ Error sending text message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void publishJson(String topic, JsonNode jsonData) {
        try {
            String payload = objectMapper.writeValueAsString(jsonData);
            MqttMessage mqttMessage = new MqttMessage(payload.getBytes());
            mqttMessage.setQos(1);
            mqttMessage.setRetained(true);
            mqttClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            System.err.println("❌ Error sending JSON message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void publishWithAdditionalFields(String topic, JsonNode jsonData, Long id, String label) {
        try {
            ObjectNode enrichedJson = (ObjectNode) jsonData;
            enrichedJson.put("id", id);
            enrichedJson.put("label", label);

            String payload = objectMapper.writeValueAsString(enrichedJson);
            MqttMessage mqttMessage = new MqttMessage(payload.getBytes());
            mqttMessage.setQos(1);
            mqttMessage.setRetained(true);
            mqttClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            System.err.println("❌ Error publishing enriched JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
