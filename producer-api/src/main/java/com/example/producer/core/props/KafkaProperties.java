package com.example.producer.core.props;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Slf4j
@Getter
@ToString
@ConfigurationProperties("kafka.config")
public class KafkaProperties {

    private final String ip;
    private final String port;
    private final String domain;

    @ConstructorBinding
    public KafkaProperties(String ip, String port) {
        this.ip = ip;
        this.port = port;
        this.domain = ip + ":" + port;
        log.info("{}", this);
    }
}
