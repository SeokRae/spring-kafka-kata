package com.example.producer.adapter.out.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaTopicAndValueProducerTest {

    @Autowired
    private KafkaTopicAndValueProducer kafkaProducer;

    @DisplayName("카프카 KafkaTopicAndValue 전송 및 검증 테스트")
    @Test
    void kafkaProducerTest() {

        kafkaProducer.createDefaultMessage("test");
    }

}