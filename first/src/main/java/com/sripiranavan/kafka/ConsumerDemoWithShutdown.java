package com.sripiranavan.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemoWithShutdown {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDemoWithShutdown.class);

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

//        Get the reference to the main thread
        final Thread mainThread = Thread.currentThread();

//        Adding the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                LOGGER.info("Detected a shutdown, let's exist by calling consumer.wakeup()");
                consumer.wakeup();
//                join the main thread to allow the execution of the code in the main thread
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    LOGGER.error("Application got interrupted", e);
                }
            }
        });

        try {
            //        Subscribe to the topic
            consumer.subscribe(Arrays.asList(topic));

//        Poll for data
            while (true) {
//                LOGGER.info("Polling for data");
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                records.forEach(record -> {
                    LOGGER.info("Key: " + record.key() + ", Value: " + record.value());
                    LOGGER.info("Partition: " + record.partition() + ", Offset: " + record.offset());
                });
            }
        } catch (WakeupException e) {
            LOGGER.info("Consumer is starting to shutdown");
        } catch (Exception e) {
            LOGGER.error("Error while consuming", e);
        } finally {
            consumer.close(); // Close the consumer, this will also commit the offsets
            LOGGER.info("Consumer is currently gracefully shutting down");
        }

    }
}
