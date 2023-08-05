package com.example.producer.adapter.out.web;

import com.example.producer.adapter.out.web.KafkaProducer;
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

        kafkaProducer.create("test");
    }
}