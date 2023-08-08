package com.example.producer.core.kafka.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

@Slf4j
public class ProducerCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if (exception != null) {
            log.error("Failed to send message to kafka topic: {}", exception.getMessage(), exception);
        } else {
            log.info("Successfully sent message to kafka topic: {}", metadata.toString());
        }
    }
}
