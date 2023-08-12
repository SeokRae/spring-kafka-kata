package com.example.consumer.adapter.in.web;

import com.example.consumer.core.props.KafkaProperties;
import com.example.consumer.core.props.TopicProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Slf4j
@Component
@Profile("shutdown-hook")
public class KafkaSyncOffsetCommitShutdownHookConsumer {

    private final KafkaConsumer<String, String> consumer;

    public KafkaSyncOffsetCommitShutdownHookConsumer(KafkaProperties kafkaProperties, TopicProperties topicProperties) {
        Properties configs = new Properties();

        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getDomain());
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, topicProperties.getGroupId());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        this.consumer = new KafkaConsumer<>(configs);

        /* 컨슈머에 할당된 파티션 확인 방법 */
        Set<TopicPartition> assignment = consumer.assignment();
        log.info("assignment: {}", assignment);

        this.consume(topicProperties.getTopic());
    }

    public void consume(String topic) {
        Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
        consumer.subscribe(Collections.singletonList(topic));
        try  {
            while (true) {

                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("record: {}", record);
                    /* 개별 단위 오프셋 */
                }
                consumer.commitSync(currentOffsets, null);
            }
        } catch (WakeupException e) {
            log.error("WakeupException: {}", e.getMessage());
            consumer.commitSync(currentOffsets);
        } finally {
            consumer.close();
        }
    }
}
