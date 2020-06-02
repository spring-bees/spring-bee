package org.springbee.distributed.cache;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hazelcast.core.Hazelcast;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springbee.distributed.cache.domain.DistributedList;
import org.springbee.distributed.cache.domain.DistributedMap;
import org.springbee.distributed.cache.domain.DistributedQueue;
import org.springbee.distributed.cache.domain.DistributedTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "server.host=0.0.0.0",
    "springbee.distributed.cache.cluster=my_cluster",
    "springbee.distributed.cache.port=0",
    "springbee.distributed.cache.host=0.0.0.0",
    "springbee.distributed.cache.members=0.0.0.0",
    /* queue */
    "springbee.distributed.cache.queue.my_queue.maxSize=10",
    "springbee.distributed.cache.queue.my_queue.backupCount=1",
    "springbee.distributed.cache.queue.my_queue.asyncBackupCount=0",
    /* list */
    "springbee.distributed.cache.list.my_list.maxSize=10",
    "springbee.distributed.cache.list.my_list.backupCount=1",
    "springbee.distributed.cache.list.my_list.asyncBackupCount=0",
    /* map */
    "springbee.distributed.cache.map.my_map.evictionPolicy=LRU",
    "springbee.distributed.cache.map.my_map.timeToLiveSeconds=5",
    "springbee.distributed.cache.map.my_map.maxIdleSeconds=2",
    "springbee.distributed.cache.map.my_map.maxSize=10",
})
public class DistributedCacheTest {

  DistributedQueue<Integer> queue;
  DistributedMap<String, String> map;
  DistributedList<Integer> list;
  DistributedTopic<String> topic;
  @Autowired
  private DistributedCacheFactory distributedCacheFactory;

  @BeforeEach
  public void setup() {
    queue = distributedCacheFactory.makeDistributedQueue("my_queue");
    map = distributedCacheFactory.makeDistributedMap("my_map");
    list = distributedCacheFactory.makeDistributedList("my_list");
    topic = distributedCacheFactory.makeDistributedTopic("my_topic");
    queue.clear();
    map.clear();
    list.clear();
    topic.removeAllListener();
  }

  @Test
  public void testDistributedQueue() {
    int loop = 5;
    for (int i = 0; i < loop; i++) {
      queue.put(i, Duration.of(2000, ChronoUnit.MICROS).getSeconds());
    }
    for (int i = 0; i < loop; i++) {
      assertThat(queue.poll(), equalTo(i));
    }
    assertThat(queue.poll(), nullValue());
  }

  @Test
  public void testDistributedQueueMaxSize() {
    for (int i = 0; i < 10; i++) {
      queue.put(i, 2);
    }
    assertThrows(TimeoutException.class, () -> {
      queue.put(1, 2);
    });
  }

  @Test
  public void testDistributedMap() {
    String k = "key";
    String v = "value";
    map.put(k, v);
    assertThat(map.get(k), equalTo(v));
  }

  /**
   * 无论是否有访问，5秒以后驱逐(timeToLiveSeconds=5)
   */
  @Test
  public void testDistributedMapTimeToLiveSeconds() {
    String k = "key";
    String v = "value";
    map.put(k, v);
    assertThat(map.get(k), equalTo(v));
    await().atMost(6, SECONDS).until(() -> map.get("key") == null);
  }

  /**
   * 2秒没有访问立即过期(maxIdleSeconds=2) 5秒以后过期(timeToLiveSeconds=5) 两者任何一个条件满足立即驱逐
   */
  @SneakyThrows
  @Test
  public void testDistributedMapMaxIdleSeconds() {
    String k = "key";
    String v = "value";
    map.put(k, v);
    Thread.sleep(1000);
    assertThat(map.get(k), equalTo(v));
    Thread.sleep(1000);
    assertThat(map.get(k), equalTo(v));
    Thread.sleep(1000);
    assertThat(map.get(k), equalTo(v));
    await().atMost(3, SECONDS).until(() -> map.get("key") == null);
  }

  @Test
  public void testDistributedList() {
    list.add(1);
    assertThat(list.size(), equalTo(1));
    assertThat(list.get(0), equalTo(1));
    list.remove(0);
    assertThat(list.size(), equalTo(0));
  }

  @Test
  public void testDistributedListMaxSize() {
    for (int i = 0; i < 11; i++) {
      list.add(i);
    }
    assertThat(list.size(), equalTo(10));
  }

  @Test
  public void testDistributedTopic() {
    String message = "topic_message";
    MessageListenerMock<String> listener = new MessageListenerMock<String>();
    UUID id = topic.addListener(listener);
    assertThat(id, notNullValue());
    topic.publish(message);
    await().atMost(1, SECONDS).until(() -> listener.getMessage().equals(message));
    boolean result = topic.removeListener(id);
    assertTrue(result);
  }

  @Test
  public void testCluster() {
    DistributedCacheFactory factory1 = new DistributedCacheFactory(
        Hazelcast.newHazelcastInstance());
    DistributedCacheFactory factory2 = new DistributedCacheFactory(
        Hazelcast.newHazelcastInstance());
    DistributedQueue<String> queue1 = factory1.makeDistributedQueue("my_queue");
    DistributedQueue<String> queue2 = factory2.makeDistributedQueue("my_queue");
    queue1.put("queue_value1");
    assertThat(queue2.poll(), equalTo("queue_value1"));
  }
}