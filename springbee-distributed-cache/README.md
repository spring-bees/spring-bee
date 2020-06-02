# 分布式缓存

在集群部署时，支持集群实例间的分布式 Map, List, Queue, Topic

## 依赖

```xml
<dependency>
  <groupId>org.springbee</groupId>
  <artifactId>springbee-distributed-cache</artifactId>
</dependency>
```

## 配置

```properties
# 网络配置
springbee.distributed.cache.cluster=my_cluster
springbee.distributed.cache.port=0
springbee.distributed.cache.host=0.0.0.0
springbee.distributed.cache.members=192.168.1.1,192.168.1.2

# Queue 缓存配置
springbee.distributed.cache.queue.my_queue.maxSize=10
springbee.distributed.cache.queue.my_queue.backupCount=1
springbee.distributed.cache.queue.my_queue.asyncBackupCount=0

# List 缓存配置
springbee.distributed.cache.list.my_list.maxSize=10
springbee.distributed.cache.list.my_list.backupCount=1
springbee.distributed.cache.list.my_list.asyncBackupCount=0

# Map 缓存配置
springbee.distributed.cache.map.my_map.evictionPolicy=LRU
springbee.distributed.cache.map.my_map.timeToLiveSeconds=5
springbee.distributed.cache.map.my_map.maxIdleSeconds=2
springbee.distributed.cache.map.my_map.maxSize=10

```

## 代码 

```java
@Autowired
private DistributedCacheFactory distributedCacheFactory;

@Test
public void test(){
  DistributedQueue<String> queue = distributedCacheFactory.makeDistributedQueue("my_queue");
  queue.put("value", Duration.of(2000, ChronoUnit.MICROS).getSeconds());
  assertThat(queue.poll(), equalTo("value"));
}
```