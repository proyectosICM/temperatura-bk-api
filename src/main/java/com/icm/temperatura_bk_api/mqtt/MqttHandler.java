package com.icm.temperatura_bk_api.mqtt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MqttHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Procesa un mensaje JSON recibido desde MQTT.
     * Esta función debe ser personalizada para procesar datos según tu dominio.
     *
     * @param payload mensaje JSON recibido vía MQTT
     */
    public void processJsonPayload(String payload) {
        try {
            JsonNode jsonNode = objectMapper.readTree(payload);

            // Aquí puedes mapear el JSON a un DTO específico si lo necesitas
            // Ejemplo:
            // MyCustomDTO data = objectMapper.treeToValue(jsonNode, MyCustomDTO.class);

            // Lógica de procesamiento genérica o personalizada
            // processHandlers(jsonNode);

        } catch (IOException e) {
            System.err.println("❌ Error procesando el JSON MQTT: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ejecuta una acción capturando cualquier excepción para no interrumpir el flujo general.
     *
     * @param action     Acción a ejecutar
     * @param actionName Nombre descriptivo de la acción (para logs)
     */
    private void executeSafely(Runnable action, String actionName) {
        try {
            action.run();
        } catch (Exception e) {
            System.err.println("❌ Error en " + actionName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ejemplo de cómo podrías estructurar las llamadas a distintas tareas relacionadas al payload.
     * Personalízalo con los handlers reales que necesites.
     */
    private void processHandlers(JsonNode jsonNode) {
        executeSafely(() -> {
            // lógica 1
        }, "handler1");

        executeSafely(() -> {
            // lógica 2
        }, "handler2");

        // Agrega más handlers según necesidad
    }

    /**
     * Ejemplo de cómo podrías publicar datos si usas un publisher MQTT
     */
    private void publishData(JsonNode jsonNode) {
        executeSafely(() -> {
            // mqttPublisher.publish("topic", jsonNode.toString());
        }, "publishData");
    }
}
