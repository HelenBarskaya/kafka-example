package com.example.paymentservice.repository;

import com.example.paymentservice.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Score, UUID> {

}
