package com.example.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    UUID id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<Product> products;

    public void setProducts(List<Product> products) {
        this.products = products;
        products.forEach(it -> it.setOrder(this));
    }
}
