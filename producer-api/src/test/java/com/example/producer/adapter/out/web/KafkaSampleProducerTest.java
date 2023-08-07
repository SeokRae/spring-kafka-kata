package com.example.producer.adapter.out.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SpringBootTest
class KafkaSampleProducerTest {

    @Autowired
    private KafkaSampleProducer kafkaSampleProducer;

    @DisplayName("동기 요청 메타 데이터 확인 테스트")
    @Test
    void sendProducerWithSync() {
        String string = "test-" + LocalDateTime.now().atOffset(ZoneOffset.UTC);
        kafkaSampleProducer.sendWithSync(string, "sync data");
    }
}