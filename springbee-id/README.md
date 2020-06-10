# 唯一序列号生成器

## 依赖

```xml
<dependency>
    <groupId>org.springbee</groupId>
    <artifactId>springbee-id</artifactId>
</dependency>
```

## 配置

```properties
# 定义起始时间（默认 2012-06-01 00:00）
springbee.id.snowflake.startTime=1338480000
# 数据中心ID，配置范围 0-31（默认自动生成）
springbee.id.snowflake.dataCenterId=-1
# 机器ID，配置范围 0-31（（默认自动生成）
springbee.id.snowflake.machineId=-1
```

## 编码

```java
@Autowired
IdGenerator idGenerator;

public void test(){
  System.out.println(idGenerator.genId());
}
```