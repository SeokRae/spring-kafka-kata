package com.example.admin.topic;

import com.example.admin.core.props.KafkaProperties;
import com.example.admin.core.props.TopicProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class KafkaTopicAdmin {

    private final AdminClient admin;

    public KafkaTopicAdmin(KafkaProperties kafkaProperties, TopicProperties topicProperties) {
        Properties configs = new Properties();

        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getDomain());

        this.admin = AdminClient.create(configs);
        log.info("kafka admin adminClient : {}", admin);
        retrieveTopics(topicProperties.getTopic());
    }

    public void retrieveTopics(String topic) {
        log.info("Get Topic Information");

        try {
            for(Node node : admin.describeCluster().nodes().get()) {
                log.info("node : {}", node);

                Map<String, TopicDescription> topicInformation = admin.describeTopics(Collections.singletonList(topic))
                        .allTopicNames() /* 3.1.0 버전 이후로 all() 대신 allTopicNames() 사용 */
                        .get();
                topicInformation.forEach(
                        (key, value) -> {
                            log.info("key : {}, value : {}", key, value);
                            log.info("topic : {}, partition : {}, replicas : {}", value.name(), value.partitions().size(), value.partitions().get(0).replicas().size());
                        });
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            admin.close();
        }
    }
}
