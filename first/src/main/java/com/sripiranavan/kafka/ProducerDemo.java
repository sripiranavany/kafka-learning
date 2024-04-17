package com.sripiranavan.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerDemo.class);
    public static void main(String[] args) {
//        Create Producer Properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

//        Set Producer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

//        Create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

//        Create a Producer Record
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java", "hello world");

//        Send Data
        producer.send(producerRecord);

//        Tell the producer to send all and block until all messages are sent
        producer.flush();

//        flush and close the producer
        producer.close();

    }
}
