package com.example.orderservice.service;

import com.example.orderservice.dto.Score;
import com.example.orderservice.kafka.Producer;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.Product;
import com.example.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
public class OrderService {

    private final Producer producer;
    private final OrderRepository orderRepository;

    public OrderService(Producer producer, OrderRepository orderRepository) {
        this.producer = producer;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public String saveOrder(Order order) {
        BigDecimal totalCost = order.getProducts().stream()
                .map(Product::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Score score = new Score(order.getId(), totalCost);

        orderRepository.save(order);

        return producer.sendScore(score);
    }
}
