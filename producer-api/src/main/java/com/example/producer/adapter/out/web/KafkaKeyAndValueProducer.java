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
public class KafkaKeyAndValueProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TopicProperties topicProperties;

    /* topic, key, value */
    public void createKeyValueMessage(String key, String message) {
        log.info("key: {}, value: {}", key, message);
        kafkaTemplate.send(topicProperties.getTopic(), key, message);
    }

}
