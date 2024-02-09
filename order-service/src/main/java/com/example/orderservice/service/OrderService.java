package com.example.orderservice.service;

import com.example.orderservice.dto.Score;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.Outbox;
import com.example.orderservice.model.Product;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    ObjectMapper mapper = new ObjectMapper();

    public OrderService(OrderRepository orderRepository, OutboxRepository outboxRepository) {
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    public String saveOrder(Order order) throws JsonProcessingException {
        BigDecimal totalCost = order.getProducts().stream()
                .map(Product::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Score score = new Score(order.getId(), totalCost);

        orderRepository.save(order);
        outboxRepository.save(new Outbox(
                UUID.randomUUID(),
                "order",
                order.getId().toString(),
                "created",
                mapper.writeValueAsString(score)));

        return "Заказ сохранен в базу данных";
    }
}
