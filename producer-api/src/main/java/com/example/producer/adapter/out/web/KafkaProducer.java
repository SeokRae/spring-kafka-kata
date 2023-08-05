package com.example.producer.adapter.out.web;

import com.example.producer.core.props.TopicProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TopicProperties topicProperties;

    public void create(String message) {
        log.info("{}", topicProperties);
        kafkaTemplate.send(topicProperties.getTopic(), message);
    }
}
