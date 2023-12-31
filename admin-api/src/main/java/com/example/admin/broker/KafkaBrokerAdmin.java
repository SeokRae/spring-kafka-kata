package com.example.admin.broker;

import com.example.admin.core.props.KafkaProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class KafkaBrokerAdmin {

    private final AdminClient admin;

    public KafkaBrokerAdmin(KafkaProperties kafkaProperties) {
        Properties configs = new Properties();

        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getDomain());

        this.admin = AdminClient.create(configs);
        log.info("kafka admin adminClient : {}", admin);
        retrieveBroker();
    }

    public void retrieveBroker() {
        log.info("Get Broker Information");

        try {
            for(Node node : admin.describeCluster().nodes().get()) {
                log.info("node : {}", node);

                ConfigResource configResource = new ConfigResource(ConfigResource.Type.BROKER, node.idString());
                DescribeConfigsResult describeConfigsResult = admin.describeConfigs(Collections.singleton(configResource));

                describeConfigsResult.all().get().forEach((configResource1, config) -> {
                    config.entries().forEach(entry -> log.info("entry :: [{}]: [{}]", entry.name(), entry.value()));
                });
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            admin.close();
        }
    }
}
