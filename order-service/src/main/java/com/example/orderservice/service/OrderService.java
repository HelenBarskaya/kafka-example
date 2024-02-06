package com.example.orderservice.service;

import com.example.orderservice.dto.Score;
import com.example.orderservice.kafka.Producer;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class OrderService {

    private final Producer producer;

    public OrderService(Producer producer) {
        this.producer = producer;
    }

    public String saveOrder(Order order) {
        BigDecimal totalCost = order.getProducts().stream()
                .map(Product::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Score score = new Score(order.getId(), totalCost);

        return producer.sendScore(score);
    }
}
