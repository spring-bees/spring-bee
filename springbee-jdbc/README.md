```properties
# 数据源配置
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.url=jdbc:h2:mem:masterdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.datasource.platform=h2
spring.datasource.hikari.connectionTimeout=30000
spring.datasource.hikari.idleTimeout=600000
spring.datasource.hikari.maxLifetime=2000000
spring.datasource.hikari.minimumIdle=5
spring.datasource.hikari.maximumPoolSize=20
spring.datasource.hikari.connection-test-query=SELECT 1
spring.datasource.hikari.poolName=default

# 动态数据源
spring.dynamicdatasource.slave.type=com.zaxxer.hikari.HikariDataSource
spring.dynamicdatasource.slave.url=jdbc:h2:mem:slavedb
spring.dynamicdatasource.slave.driverClassName=org.h2.Driver
spring.dynamicdatasource.slave.username=sa
spring.dynamicdatasource.slave.password=password
spring.dynamicdatasource.slave.platform=h2
spring.dynamicdatasource.slave.hikari.connectionTimeout=30000
spring.dynamicdatasource.slave.hikari.idleTimeout=600000
spring.dynamicdatasource.slave.hikari.maxLifetime=2000000
spring.dynamicdatasource.slave.hikari.minimumIdle=5
spring.dynamicdatasource.slave.hikari.maximumPoolSize=20
spring.dynamicdatasource.slave.hikari.connection-test-query=SELECT 1
spring.dynamicdatasource.slave.hikari.poolName=slave

# 数据库版本管理
spring.flyway.locations=classpath:/db/migration/{vendor}
spring.flyway.poolName=master

# 敏感SQL指令保护
springbee.datasource.sql.protection.enabled=true
springbee.datasource.sql.protection.keywords=drop,truncate
```