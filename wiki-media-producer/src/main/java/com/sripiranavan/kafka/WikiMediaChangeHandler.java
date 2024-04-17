package com.sripiranavan.kafka;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiMediaChangeHandler implements BackgroundEventHandler {

    private KafkaProducer<String, String> kafkaProducer;
    private String topic;
    private static final Logger LOGGER = LoggerFactory.getLogger(WikiMediaChangeHandler.class);

    public WikiMediaChangeHandler(KafkaProducer<String, String> kafkaProducer, String topic) {
        this.kafkaProducer = kafkaProducer;
        this.topic = topic;
    }

    @Override
    public void onOpen() throws Exception {
//  Nothing here
    }

    @Override
    public void onClosed() throws Exception {
        kafkaProducer.close();
    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        LOGGER.info("{}", messageEvent.getData());
        kafkaProducer.send(new ProducerRecord<>(topic, messageEvent.getData()));
    }

    @Override
    public void onComment(String s) throws Exception {
//        Nothing  here
    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.error("Error in stream reading ", throwable);
    }
}
