package com.example.orderservice.kafka;

import com.example.orderservice.dto.Score;
import com.example.orderservice.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Producer {

    private static final String JSON_WAS_NOT_PROCESSED = "Ошибка сериализации объекта";
    private static final String MESSAGE_SEND = "Сообщение успешно отправлено";

    @Value("${topics.orders}")
    private String topicName;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }


    public String sendScore(Score score){
        String message;

        try {
            message = objectMapper.writeValueAsString(score);
            log.info("Объект преобразован в сообщение {}", message);
        } catch (JsonProcessingException e) {
            return JSON_WAS_NOT_PROCESSED;
        }

        kafkaTemplate.send(topicName, message);
        return MESSAGE_SEND;
    }
}
