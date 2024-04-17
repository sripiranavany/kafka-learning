
### TO start the kafka
```
# To start the kafka
kafka-server-start.sh /hms/installs/kafka/kafka_2.13-3.7.0/config/kraft/server.properties
```

### Kafka Topics
```
# To Create topics
kafka-topics.sh --bootstrap-server localhost:9092 --topic <topic_name> --create

# To Create topic with custom partitioning
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic <topic_name> --partitions 5

# To Create topic with replication-factor (in localhost only one broker so 1)
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic <topic_name> replication-factor 1

# To list the topics
kafka-topics.sh --bootstrap-server localhost:9092 --list

# To describe the topic
kafka-topics.sh --bootstrap-server localhost:9092 --topic <topic_name> --describe

# To delete the topic
kafka-topics.sh --bootstrap-server localhost:9092 --topic <topic_name> --delete
```

### Kafka Producer and consumers
```
# Produce to topic
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic <topic_name>

# Produce to a non existing topic
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic <topic_name>

# Produce with key value
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --property parse.key=true --property key.separator=:

# Produce with partitioner
kafka-console-producer.sh --bootstrap-server localhost:9092 --producer-property partitioner.class=org.apache.kafka.clients.producer.RoundRobinPartitioner --topic second_topic

# To consume
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic second_topic

# Consume all message from beginnig
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic second_topic --from-beginning

# Consume with additioinal informations
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic second_topic --formatter kafka.tools.DefaultMessageFormatter --property print.timestamp=true --property print.key=true --property print.value=true --property print.partition=true --from-beginning

# Consume with group
kafka-console-consumer.sh --bootstrap-server localhost:9092 --formatter kafka.tools.DefaultMessageFormatter --property print.timestamp=true --property print.key=true --property print.value=true --property print.partition=true --topic third_topic --group my-first-application

# Consumer group
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-application --describe

# To reset the offset
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --to-earliest --topic third_topic --dry-run
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --to-earliest --topic third_topic --execute
```

