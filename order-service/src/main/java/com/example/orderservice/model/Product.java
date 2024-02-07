package com.example.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    UUID id;

    String name;

    BigDecimal amount;

    long quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    public BigDecimal getTotalCost() {
        return amount.multiply(new BigDecimal(quantity));
    }
}
