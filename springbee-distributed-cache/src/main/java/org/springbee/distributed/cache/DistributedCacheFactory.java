package org.springbee.distributed.cache;

import com.hazelcast.core.HazelcastInstance;
import java.util.HashSet;
import org.springbee.distributed.cache.domain.DistributedList;
import org.springbee.distributed.cache.domain.DistributedMap;
import org.springbee.distributed.cache.domain.DistributedQueue;
import org.springbee.distributed.cache.domain.DistributedTopic;

public class DistributedCacheFactory {

  private HazelcastInstance hazelcastInstance;

  public DistributedCacheFactory(HazelcastInstance hazelcastInstance) {
    this.hazelcastInstance = hazelcastInstance;
  }

  public <K, V> DistributedMap<K, V> makeDistributedMap(String name) {
    return DistributedMap.<K, V>builder()
        .name(name)
        .hazelcastInstance(hazelcastInstance)
        .build();
  }

  public <E> DistributedQueue<E> makeDistributedQueue(String name) {
    return DistributedQueue.<E>builder()
        .name(name)
        .hazelcastInstance(hazelcastInstance)
        .build();
  }

  public <E> DistributedList<E> makeDistributedList(String name) {
    return DistributedList.<E>builder()
        .name(name)
        .hazelcastInstance(hazelcastInstance)
        .build();
  }

  public <T> DistributedTopic<T> makeDistributedTopic(String name) {
    return DistributedTopic.<T>builder()
        .name(name)
        .listenerNames(new HashSet<>())
        .hazelcastInstance(hazelcastInstance)
        .build();
  }
}