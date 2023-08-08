package com.example.producer.adapter.out.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class KafkaCustomPartitionerProducerTest {

    @Autowired
    private KafkaCustomPartitionerProducer kafkaCustomPartitionerProducer;

    @DisplayName("동기 요청 메타 데이터 확인 테스트")
    @Test
    void sendProducerWithSync() throws InterruptedException {
        String string = LocalDateTime.now().atOffset(ZoneOffset.UTC).toString();
        int threadCount = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for(int i = 0 ; i < threadCount ; i++) {
            int count = i;
            executorService.execute(() -> {
                kafkaCustomPartitionerProducer.sendWithSync(string, "sync data-" + count);
                countDownLatch.countDown();
            });
        }

        Thread.sleep(Duration.ofSeconds(3).toMillis());
    }
}