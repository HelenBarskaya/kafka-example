package com.example.orderservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Outbox {
    @Id
    private UUID id;

    @Column(name = "aggregatetype")
    private String aggregateType;

    @Column(name = "aggregateid")
    private String aggregateId;

    private String type;

    private String payload;
}
