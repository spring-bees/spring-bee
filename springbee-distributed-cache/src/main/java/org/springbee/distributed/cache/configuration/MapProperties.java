package org.springbee.distributed.cache.configuration;

import com.hazelcast.config.EvictionPolicy;
import lombok.Data;

@Data
public class MapProperties {

  private EvictionPolicy evictionPolicy = EvictionPolicy.LRU;
  private int timeToLiveSeconds = Integer.MAX_VALUE;
  private int maxIdleSeconds = Integer.MAX_VALUE;
  private int maxSize = Integer.MAX_VALUE;
}
