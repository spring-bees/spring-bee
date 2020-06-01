# Roadmap

### 1. DB

- [x] 数据源配置
- [x] 多数据源配置
- [x] 数据库版本管理
- [x] 敏感SQL指令保护

### 2. Logging

- [x] 日志控制台输出
- [x] 日志Kafka输出
- [x] 日志脱敏配置
- [x] 动态修改日志级别

### 3. Web

- [x] 支持 SSL PKCS12 证书
- [x] 多端口监听配置
- [x] 内置 Undertow
- [ ] XSS防护
- [ ] 访问限速
- [ ] 集成 OpenAPI 3.0

### 4. Distributed

- [ ] 基于DB分布式锁 
- [ ] 基于Redis分布式锁
- [ ] 基于Mongo分布式锁
- [ ] 基于Zookeeper分布式锁
- [ ] 分布式缓存
- [ ] 分布式事务集成 ServiceComb Pack

### 4. Tools

- [ ] maven-archetype-springbee

### 4. Build

- [x] 编译时静态代码检查
- [x] 编译时代码风格检查
- [x] 编译时单元测试覆盖率检查
- [ ] 编译时生成Docker镜像