package com.example.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.example.producer.core.props")
public class ProducerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApiApplication.class, args);
    }

}
