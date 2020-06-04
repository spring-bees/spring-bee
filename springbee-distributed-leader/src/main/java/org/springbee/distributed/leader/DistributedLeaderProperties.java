package org.springbee.distributed.leader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "springbee.distributed.leader")
public class DistributedLeaderProperties {

  private String host;
  private int port;
  private String[] seedNodes;

  public void setHost(String host) {
    this.host = host;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setSeedNodes(String[] seedNodes) {
    this.seedNodes = seedNodes.clone();
  }

  public Map<String, Object> convertAkkaConfig(
      DistributedLeaderProperties distributedLeaderProperties) {
    final Map<String, Object> propertyMap = new HashMap<>();
    final List<String> seedNodes = new ArrayList<>();
    final List<String> loggers = new ArrayList<>();
    // actor
    propertyMap.put("akka.actor.provider", "cluster");
    // remote
    propertyMap.put("akka.remote.artery.transport", "tcp");
    propertyMap.put("akka.remote.artery.canonical.hostname", distributedLeaderProperties.host);
    propertyMap.put("akka.remote.artery.canonical.port", distributedLeaderProperties.port);
    for (String node : distributedLeaderProperties.seedNodes) {
      seedNodes.add("akka://springbee-akka-cluster@" + node);
    }
    // cluster
    propertyMap.put("akka.cluster.data-center", "springbee-distributed-leader");
    propertyMap.put("akka.cluster.auto-down-unreachable-after", "off");
    propertyMap.put("akka.cluster.seed-nodes", seedNodes);
    // logger
    propertyMap.put("akka.loggers", loggers);
    return Collections.unmodifiableMap(propertyMap);
  }
}