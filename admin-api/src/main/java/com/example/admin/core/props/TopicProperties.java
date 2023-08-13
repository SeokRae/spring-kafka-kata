package com.example.admin.core.props;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Slf4j
@Getter
@ToString
@ConfigurationProperties("topics")
public class TopicProperties {

    private final String topic;
    private final String groupId;

    @ConstructorBinding
    public TopicProperties(String topic, String groupId) {
        this.topic = topic;
        this.groupId = groupId;
        log.info("{}", this);
    }
}
