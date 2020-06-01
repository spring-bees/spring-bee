
```properties
# 日志输出路径，默认 logs
logging.file.path=logs
# 日志文件名，默认 springbee.log
logging.file.name=springbee.log

# 日志写入 kafka topic 名称
logging.kafka.topic=springbee-topic
# 日志写入 kafka topic 分区
logging.kafka.partition=0
# 日志写入 kafka 地址
logging.kafka.bootstrap.servers=localhost:9092

# 日志写入文件/kafka， 默认不开启
spring.profiles.active=logfile,logkafka 

# 日志脱敏
logging.sensitive[0].regex=(\\d{3})\\d{4}(\\d{4}) # 手机号
logging.sensitive[0].replacement=$1****$2
```

运行时查看日志级别

```shell script
curl http://localhost:8088/actuator/loggers/org.springbee
```

运行时修改日志级别

```shell script
curl -i -X POST -H 'Content-Type: application/json' -d '{"configuredLevel": "DEBUG"}' http://localhost:8088/actuator/loggers/org.springbee
```