package com.example.orderservice.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${topics.orders}")
    private String topicName;

    @Bean
    public NewTopic orderTopic(){
        return TopicBuilder
                .name(topicName)
                .build();
    }
}
