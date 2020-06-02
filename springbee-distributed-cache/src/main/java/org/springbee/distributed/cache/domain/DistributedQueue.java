package org.springbee.distributed.cache.domain;

import com.hazelcast.core.HazelcastInstance;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.Builder;
import lombok.SneakyThrows;

@Builder
public class DistributedQueue<E> {

  private final HazelcastInstance hazelcastInstance;
  private String name;

  @SneakyThrows
  public void put(E value) {
    hazelcastInstance.getQueue(name).put(value);
  }

  @SneakyThrows
  public void put(E value, long timeoutSeconds) {
    boolean success = hazelcastInstance.getQueue(name)
        .offer(value, timeoutSeconds, TimeUnit.SECONDS);
    if (!success) {
      throw new TimeoutException("timeout " + timeoutSeconds + "s");
    }
  }

  public E poll() {
    return hazelcastInstance.<E>getQueue(name).poll();
  }

  @SneakyThrows
  public E poll(long timeoutSeconds) {
    return hazelcastInstance.<E>getQueue(name).poll(timeoutSeconds, TimeUnit.SECONDS);
  }

  @SneakyThrows
  public E take() {
    return hazelcastInstance.<E>getQueue(name).take();
  }

  public void destroy() {
    hazelcastInstance.getQueue(name).destroy();
  }

  public void clear() {
    hazelcastInstance.getQueue(name).clear();
  }
}
