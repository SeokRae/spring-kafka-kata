package com.example.producer.adapter.out.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaProducerTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @DisplayName("카프카 프로듀서 레코드 전송 및 검증 테스트")
    @Test
    void kafkaProducerTest() {

        kafkaProducer.createDefaultMessage("test");
    }

    @DisplayName("카프카 프로듀서 키-값 레코드 전송 및 검증 테스트")
    @Test
    void kafkaKeyValue() {
        kafkaProducer.createKeyValueMessage("testKey", "testMessage");
    }

    @DisplayName("카프카 프로듀서 레코드 전송 및 검증 테스트")
    @Test
    void kafkaRecord() {
        kafkaProducer.createRecord("testKey", "testMessage");
    }
}