package com.example.producer.adapter.out.web;

import com.example.producer.core.props.TopicProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TopicProperties topicProperties;

    public void createDefaultMessage(String message) {
        log.info("key: {}, value: {}", topicProperties.getTopic(), message);
        kafkaTemplate.send(topicProperties.getTopic(), message);
    }

    /* topic, key, value */
    public void createKeyValueMessage(String key, String message) {
        log.info("key: {}, value: {}", key, message);
        kafkaTemplate.send(topicProperties.getTopic(), key, message);
    }

    /* topic, record */
    public void createRecord(String key, String message) {
        log.info("key: {}, value: {}", key, message);

        ProducerRecord<String, String> record = new ProducerRecord<>(
                topicProperties.getTopic(),
                key,
                message
        );
        kafkaTemplate.send(record);
    }
}
