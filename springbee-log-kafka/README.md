## 依赖

```xml
<dependency>
    <groupId>org.springbee</groupId>
    <artifactId>springbee-log-kafka</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <scope>runtime</scope>
</dependency>
```

## 配置文件

```xml
[src/main/resources/logback.xml]
<configuration>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- This is the kafkaAppender -->
    <appender name="kafkaAppender" class="org.springbee.log.kafka.KafkaAppender">
            <encoder>
                <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
            </encoder>
            <topic>logs</topic>
            <keyingStrategy class="org.springbee.log.kafka.keying.NoKeyKeyingStrategy" />
            <deliveryStrategy class="org.springbee.log.kafka.delivery.AsynchronousDeliveryStrategy" />
            
            <!-- Optional parameter to use a fixed partition -->
            <!-- <partition>0</partition> -->
            
            <!-- Optional parameter to include log timestamps into the kafka message -->
            <!-- <appendTimestamp>true</appendTimestamp> -->

            <!-- each <producerConfig> translates to regular kafka-client config (format: key=value) -->
            <!-- producer configs are documented here: https://kafka.apache.org/documentation.html#newproducerconfigs -->
            <!-- bootstrap.servers is the only mandatory producerConfig -->
            <producerConfig>bootstrap.servers=localhost:9092</producerConfig>

            <!-- this is the fallback appender if kafka is not available. -->
            <appender-ref ref="STDOUT" />
        </appender>

    <root level="info">
        <appender-ref ref="kafkaAppender" />
    </root>
</configuration>
```

## 感谢

springbee-log-kafka 是从 [logback-kafka-appender](https://github.com/danielwegener/logback-kafka-appender) 移植过来的