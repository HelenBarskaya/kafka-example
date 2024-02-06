package com.example.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    UUID id;

    String name;

    BigDecimal amount;

    long quantity;

    public BigDecimal getTotalCost() {
        return amount.multiply(new BigDecimal(quantity));
    }
}
