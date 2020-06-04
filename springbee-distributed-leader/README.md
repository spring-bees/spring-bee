应用集群部署时，有一些业务只能在一个节点上执行。那么就可以通过此依赖实现集群中 leader 节点的判断，控制一些业务逻辑只在集群中的一个节点上运行

依赖

```xml
<dependencies>
  <dependency>
    <artifactId>springbee-boot</artifactId>
    <groupId>org.springbee</groupId>
  </dependency>
  <dependency>
    <artifactId>springbee-common</artifactId>
    <groupId>org.springbee</groupId>
  </dependency>
  <dependency>
    <artifactId>springbee-distributed-leader</artifactId>
    <groupId>org.springbee</groupId>
  </dependency>
</dependencies>
```

配置

```properties
springbee.distributed.leader.enabled=true
springbee.distributed.leader.port=2551
springbee.distributed.leader.seed-nodes=["0.0.0.0:2551"]
```

判断本实例是否是 Leader

```java
if (DistributedLeaderContext.getInstance().isLeader()) {
  // 处理 leader 节点才能执行的业务逻辑
}
```
