package org.springbee.distributed.cache.domain;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.MessageListener;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;

@Builder
public class DistributedTopic<T> {

  private final HazelcastInstance hazelcastInstance;
  private String name;
  private Set<UUID> listenerNames;

  public void publish(T value) {
    hazelcastInstance.getTopic(name).publish(value);
  }

  public UUID addListener(MessageListener<T> listener) {
    ITopic<T> topic = hazelcastInstance.getTopic(name);
    UUID listenerName = topic.addMessageListener(listener);
    listenerNames.add(listenerName);
    return listenerName;
  }

  public boolean removeListener(UUID listenerName) {
    return hazelcastInstance.getTopic(name).removeMessageListener(listenerName);
  }

  public void removeAllListener() {
    for (UUID listenerName : listenerNames) {
      hazelcastInstance.getTopic(name).removeMessageListener(listenerName);
    }
  }

  public Set<UUID> getListenerNames() {
    return listenerNames;
  }

}
