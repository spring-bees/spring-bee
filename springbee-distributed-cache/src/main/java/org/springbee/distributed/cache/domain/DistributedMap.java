package org.springbee.distributed.cache.domain;

import com.hazelcast.core.HazelcastInstance;
import lombok.Builder;

@Builder
public class DistributedMap<K, V> {

  private final HazelcastInstance hazelcastInstance;
  private String name;

  public void put(K key, V value) {
    hazelcastInstance.getMap(name).put(key, value);
  }

  public V get(K key) {
    return hazelcastInstance.<K, V>getMap(name).get(key);
  }

  public void destroy() {
    hazelcastInstance.getMap(name).destroy();
  }

  public void clear() {
    hazelcastInstance.getMap(name).clear();
  }
}
