package com.sripiranavan.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDemo.class);

    public static void main(String[] args) {

        String groupId = "my-java-application";
        String topic = "demo_java";

        Properties properties = new Properties();

        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

//        Create Consumer Properties
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", groupId);
        properties.setProperty("auto.offset.reset", "earliest");

//        Create the Consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

//        Subscribe to the topic
        consumer.subscribe(Arrays.asList(topic));

//        Poll for data
        while (true) {
            LOGGER.info("Polling for data");
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

            records.forEach(record -> {
                LOGGER.info("Key: " + record.key() + ", Value: " + record.value());
                LOGGER.info("Partition: " + record.partition() + ", Offset: " + record.offset());
            });
        }

    }
}
