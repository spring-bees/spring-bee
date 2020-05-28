package org.springbee.log.kafka;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Collections;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springbee.log.kafka.util.TestKafka;

@SuppressWarnings("unchecked")
public class ITLogbackIntegrationTest {

  private static final Charset UTF8 = Charset.forName("UTF-8");
  private TestKafka kafka;
  private org.slf4j.Logger logger;

  @BeforeEach
  public void beforeLogSystemInit() throws IOException, InterruptedException {
    kafka = TestKafka.createTestKafka(Collections.singletonList(9092));
    logger = LoggerFactory.getLogger("LogbackIntegrationIT");

  }

  @AfterEach
  public void tearDown() {
    kafka.shutdown();
    kafka.awaitShutdown();
  }

  @Test
  public void testLogging() {

    for (int i = 0; i < 1000; ++i) {
      logger.info("message" + (i));
    }

    final KafkaConsumer<byte[], byte[]> client = kafka.createClient();
    client.assign(Collections.singletonList(new TopicPartition("logs", 0)));
    client.seekToBeginning(Collections.singletonList(new TopicPartition("logs", 0)));

    int no = 0;

    ConsumerRecords<byte[], byte[]> poll = client.poll(Duration.ofMillis(1000));
    while (!poll.isEmpty()) {
      for (ConsumerRecord<byte[], byte[]> consumerRecord : poll) {
        final String messageFromKafka = new String(consumerRecord.value(), UTF8);
        assertThat(messageFromKafka, Matchers.equalTo("message" + no));
        ++no;
      }
      poll = client.poll(Duration.ofMillis(1000));
    }

    assertEquals(1000, no);

  }

}
