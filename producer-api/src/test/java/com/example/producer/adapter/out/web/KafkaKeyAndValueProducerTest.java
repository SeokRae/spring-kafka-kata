package com.example.producer.adapter.out.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaKeyAndValueProducerTest {

    @Autowired
    private KafkaKeyAndValueProducer kafkaProducer;

    @DisplayName("카프카 KafkaKeyAndValueProducer 전송 및 검증 테스트")
    @Test
    void kafkaKeyValue() {
        kafkaProducer.createKeyValueMessage("testKey", "testMessage");
    }

}