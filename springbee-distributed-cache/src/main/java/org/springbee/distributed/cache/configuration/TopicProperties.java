package org.springbee.distributed.cache.configuration;

import com.hazelcast.topic.TopicOverloadPolicy;
import lombok.Data;

@Data
public class TopicProperties {

  private int readBatchSize = 10;
  private TopicOverloadPolicy topicOverloadPolicy = TopicOverloadPolicy.BLOCK;
  private int capacity = 10000;
  private int timeToLiveSeconds = 0;
}
