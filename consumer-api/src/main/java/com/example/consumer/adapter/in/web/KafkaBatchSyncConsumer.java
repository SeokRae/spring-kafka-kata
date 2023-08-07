package com.example.consumer.adapter.in.web;

import com.example.consumer.core.props.KafkaProperties;
import com.example.consumer.core.props.TopicProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
@Component
public class KafkaBatchSyncConsumer {

    private final KafkaConsumer<String, String> consumer;

    public KafkaBatchSyncConsumer(KafkaProperties kafkaProperties, TopicProperties topicProperties) {
        Properties configs = new Properties();

        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getDomain());
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, topicProperties.getGroupId());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        this.consumer = new KafkaConsumer<>(configs);

        consume(topicProperties.getTopic());
    }

    public void consume(String topic) {
        consumer.subscribe(Collections.singletonList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                log.info("record: {}", record);
            }
            /* poll() 메서드로 받은 모든 레코드 동기 오프셋 처리 */
            consumer.commitSync();
        }
    }

}
