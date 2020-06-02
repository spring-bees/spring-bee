package org.springbee.distributed.cache.domain;

import com.hazelcast.core.HazelcastInstance;
import java.util.Collection;
import lombok.Builder;

@Builder
public class DistributedList<E> {

  private final HazelcastInstance hazelcastInstance;
  private String name;

  public void add(E value) {
    hazelcastInstance.getList(name).add(value);
  }

  public void add(int index, E value) {
    hazelcastInstance.getList(name).add(index, value);
  }

  public void add(int index, Collection<E> c) {
    hazelcastInstance.getList(name).addAll(index, c);
  }

  public void add(Collection<E> c) {
    hazelcastInstance.getList(name).addAll(c);
  }

  public E get(int index) {
    return hazelcastInstance.<E>getList(name).get(index);
  }

  public void remove(E value) {
    hazelcastInstance.getList(name).remove(value);
  }

  public void remove(int index) {
    hazelcastInstance.getList(name).remove(index);
  }

  public void remove(Collection<E> c) {
    hazelcastInstance.getList(name).removeAll(c);
  }

  public int size() {
    return hazelcastInstance.getList(name).size();
  }

  public void destroy() {
    hazelcastInstance.getList(name).destroy();
  }

  public void clear() {
    hazelcastInstance.getList(name).clear();
  }

}
