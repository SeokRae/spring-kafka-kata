package com.example.admin.broker;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ConfigEntry;
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

    private final AdminClient adminClient;

    public KafkaBrokerAdmin() {
        Properties configs = new Properties();

        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        this.adminClient = AdminClient.create(configs);
        log.info("kafka admin adminClient : {}", adminClient);
        retrieveBroker();
    }

    public void retrieveBroker() {
        log.info("Get Broker Information");

        try {
            for(Node node : adminClient.describeCluster().nodes().get()) {
                log.info("node : {}", node);

                ConfigResource configResource = new ConfigResource(ConfigResource.Type.BROKER, node.idString());
                DescribeConfigsResult describeConfigsResult = adminClient.describeConfigs(Collections.singleton(configResource));

                describeConfigsResult.all().get().forEach((configResource1, config) -> {
                    config.entries().forEach(entry -> log.info("entry :: [{}]: [{}]", entry.name(), entry.value()));
                });
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
