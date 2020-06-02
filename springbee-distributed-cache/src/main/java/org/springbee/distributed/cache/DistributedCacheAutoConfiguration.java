package org.springbee.distributed.cache;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.ListConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.config.ReliableTopicConfig;
import com.hazelcast.core.HazelcastInstance;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springbee.distributed.cache.configuration.DistributedConfigProperties;
import org.springbee.distributed.cache.configuration.ListProperties;
import org.springbee.distributed.cache.configuration.MapProperties;
import org.springbee.distributed.cache.configuration.QueueProperties;
import org.springbee.distributed.cache.configuration.TopicProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Slf4j
@Import(DistributedConfigProperties.class)
public class DistributedCacheAutoConfiguration {

  @Bean
  public Config config(DistributedConfigProperties properties) {
    Config config = new Config();
    config.setInstanceName(properties.getCluster());
    config.setProperty("hazelcast.socket.bind.any", "false");
    config.setProperty("hazelcast.http.healthcheck.enabled", "true");
    config.setProperty("hazelcast.local.localAddress", properties.getHost());
    if (properties.getMembers().length > 2) {
      config.getCPSubsystemConfig().setCPMemberCount(properties.getMembers().length);
    }

    //NetworkConfig
    NetworkConfig network = new NetworkConfig();
    network.getJoin().getMulticastConfig().setEnabled(false);
    network.getJoin().getAwsConfig().setEnabled(false);
    network.getJoin().getTcpIpConfig().setEnabled(true);
    network.getInterfaces().addInterface(properties.getHost());
    for (String member : properties.getMembers()) {
      network.getJoin().getTcpIpConfig().addMember(member);
    }

    network.setPortCount(100);
    network.setPortAutoIncrement(true);
    if (properties.getPort() > 0) {
      network.setPort(properties.getPort());
    }

    config.setNetworkConfig(network);

    //Map
    if (!properties.getMap().isEmpty()) {
      Iterator<String> it = properties.getMap().keySet().iterator();
      while (it.hasNext()) {
        String cacheName = it.next();
        MapProperties cacheProp = properties.getMap().get(cacheName);
        EvictionConfig evictionConfig = new EvictionConfig();
        evictionConfig.setSize(cacheProp.getMaxSize()).setEvictionPolicy(EvictionPolicy.LFU)
            .setMaxSizePolicy(MaxSizePolicy.PER_NODE);
        MapConfig map = new MapConfig()
            .setName(cacheName)
            .setStatisticsEnabled(true)
            .setEvictionConfig(evictionConfig)
            .setMaxIdleSeconds(cacheProp.getMaxIdleSeconds())
            .setTimeToLiveSeconds(cacheProp.getTimeToLiveSeconds());
        config.addMapConfig(map);
      }
    }

    //Queue
    if (!properties.getQueue().isEmpty()) {
      Iterator<String> it = properties.getQueue().keySet().iterator();
      while (it.hasNext()) {
        String cacheName = it.next();
        QueueProperties cacheProp = properties.getQueue().get(cacheName);
        QueueConfig queue = new QueueConfig()
            .setName(cacheName)
            .setMaxSize(cacheProp.getMaxSize())
            .setBackupCount(cacheProp.getBackupCount())
            .setAsyncBackupCount(cacheProp.getAsyncBackupCount())
            .setStatisticsEnabled(true);
        config.addQueueConfig(queue);
      }
    }

    //List
    if (!properties.getList().isEmpty()) {
      Iterator<String> it = properties.getList().keySet().iterator();
      while (it.hasNext()) {
        String cacheName = it.next();
        ListProperties cacheProp = properties.getList().get(cacheName);
        ListConfig list = new ListConfig()
            .setName(cacheName)
            .setMaxSize(cacheProp.getMaxSize())
            .setBackupCount(cacheProp.getBackupCount())
            .setAsyncBackupCount(cacheProp.getAsyncBackupCount())
            .setStatisticsEnabled(true);
        config.addListConfig(list);
      }
    }

    //Topic
    if (!properties.getTopic().isEmpty()) {
      Iterator<String> it = properties.getTopic().keySet().iterator();
      while (it.hasNext()) {
        String cacheName = it.next();
        TopicProperties cacheProp = properties.getTopic().get(cacheName);
        ReliableTopicConfig topic = new ReliableTopicConfig()
            .setName(cacheName)
            .setTopicOverloadPolicy(cacheProp.getTopicOverloadPolicy())
            .setReadBatchSize(cacheProp.getReadBatchSize())
            .setStatisticsEnabled(true);
        config.addReliableTopicConfig(topic);
        config.getRingbufferConfig(cacheName).setCapacity(cacheProp.getCapacity())
            .setTimeToLiveSeconds(cacheProp.getTimeToLiveSeconds());
      }
    }

    return config;
  }

  @Bean
  public DistributedCacheFactory distributedCacheFactory(HazelcastInstance hazelcastInstance) {
    return new DistributedCacheFactory(hazelcastInstance);
  }

}
