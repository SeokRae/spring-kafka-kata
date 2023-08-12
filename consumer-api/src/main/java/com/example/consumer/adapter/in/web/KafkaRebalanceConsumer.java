package com.example.consumer.adapter.in.web;

import com.example.consumer.core.props.KafkaProperties;
import com.example.consumer.core.props.TopicProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Slf4j
@Component
@Profile("rebalance")
public class KafkaRebalanceConsumer {

    private final KafkaConsumer<String, String> consumer;

    public KafkaRebalanceConsumer(KafkaProperties kafkaProperties, TopicProperties topicProperties) {
        Properties configs = new Properties();

        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getDomain());
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, topicProperties.getGroupId());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        /* 리밸런스 발생 시 수동 커밋 설정 */
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        this.consumer = new KafkaConsumer<>(configs);

        /* 컨슈머에 할당된 파티션 확인 방법 */
        Set<TopicPartition> assignment = consumer.assignment();
        log.info("assignment: {}", assignment);

        this.subscribe(topicProperties.getTopic());
    }

    public void subscribe(String topic) {
        Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
        /* subscribe 메서드에 Listener 설정 추가 */
        this.consumer.subscribe(Collections.singleton(topic), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                log.warn("Partitions revoked: {}", partitions);
                /* 리밸런스가 발새하면 가장 마지막으로 처리 완료한 레코드를 기준으로 커밋을 실시, 데이터 처리의 중복 방지 */
                consumer.commitSync(currentOffsets);
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                log.warn("Partitions assigned: {}", partitions);
            }
        });

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

            for (ConsumerRecord<String, String> record : records) {
                /* 레코드의 데이터 처리가 끝나면 레코드가 속한 토픽, 파티션, 오프셋에 관한정보를 HashMap에 저장 */
                currentOffsets.put(
                        new TopicPartition(record.topic(), record.partition()),
                        /* 오프셋 지정 커밋시 사용 */
                        new OffsetAndMetadata(
                                /* 컨슈머 재시작 시 파티션에서 가장 마지막으로 커밋된 오프셋부터 레코드 읽기에 offset + 1 설정 */
                                record.offset() + 1
                                , null)
                );
                log.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
            }
        }
    }
}