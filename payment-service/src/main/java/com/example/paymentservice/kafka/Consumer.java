package com.example.paymentservice.kafka;

import com.example.paymentservice.dto.ScoreDto;
import com.example.paymentservice.model.Score;
import com.example.paymentservice.model.Status;
import com.example.paymentservice.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    private static final String JSON_WAS_NOT_PROCESSED = "Ошибка сериализации объекта";
    private static final String MESSAGE_RECEIVED = "Сообщение успешно получено";
    private static final String SCORE_SUCCESSFULLY_SAVED = "Счет на оплату успешно сохранен";

    private static final String topicName = "${topics.orders}";

    PaymentRepository paymentRepository;

    ObjectMapper objectMapper;

    public Consumer(PaymentRepository paymentRepository, ObjectMapper objectMapper) {
        this.paymentRepository = paymentRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = topicName, groupId = "paymentsGroup")
    public void listenMessages(String data){
        ScoreDto dto;
        try {
            dto = objectMapper.readValue(data, ScoreDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(JSON_WAS_NOT_PROCESSED);
        }
        log.info(MESSAGE_RECEIVED);
        paymentRepository.save(new Score(dto.getId(), dto.getTotalCost(), Status.CREATED));
        log.info(SCORE_SUCCESSFULLY_SAVED);
    }
}
