package com.example.producer.adapter.out.web;

import com.example.producer.core.kafka.callback.ProducerCallback;
import com.example.producer.core.props.KafkaProperties;
import com.example.producer.core.props.TopicProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class KafkaAsyncCallbackProducer {

    private final KafkaProducer<String, String> producer;
    private final TopicProperties topicProperties;

    public KafkaAsyncCallbackProducer(KafkaProperties kafkaProperties, TopicProperties topicProperties) {

        Properties configs = new Properties();

        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getDomain());
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        this.producer = new KafkaProducer<>(configs);
        this.topicProperties = topicProperties;
    }

    public void sendAsyncCallback(String key, String value) {

        ProducerRecord<String, String> record = new ProducerRecord<>(topicProperties.getTopic(), key, value);
        try {
            RecordMetadata metadata = producer.send(record, new ProducerCallback()).get();

            log.info("metadata: {}", metadata);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
