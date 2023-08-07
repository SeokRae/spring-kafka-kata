package com.example.producer.core.kafka.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CustomPartitioner implements Partitioner {

    @Override
    public int partition(
            String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster
    ) {
        if(keyBytes == null) {
            throw new InvalidRecordException("Key must not be null");
        }
        if("test".equals(key)) {
            return 0;
        }

        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();

        return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
    }

    @Override
    public void close() {}

    @Override
    public void configure(Map<String, ?> configs) {}
}
