package com.example.producer.adapter.out.web;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
public class KafkaRecordProducerTest {

    @Autowired
    private KafkaRecordProducer kafkaRecordProducer;

    @DisplayName("카프카 Record 전송 테스트")
    @Test
    void sample() {
        // given
        ProducerRecord<String, String> record = new ProducerRecord<>("testMessage", "test", "test-1");

        // when
        kafkaRecordProducer.createRecord(record);

    }
}
