package org.springbee.distributed.cache.configuration;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "springbee.distributed.cache")
@Getter
@Setter
public class DistributedConfigProperties {

  @SuppressFBWarnings
  private String[] members;
  private String cluster;
  private int port = 0;
  private String host;
  private Map<String, MapProperties> map = new HashMap<>();
  private Map<String, QueueProperties> queue = new HashMap<>();
  private Map<String, ListProperties> list = new HashMap<>();
  private Map<String, TopicProperties> topic = new HashMap<>();
}
