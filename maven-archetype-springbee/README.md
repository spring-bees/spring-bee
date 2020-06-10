# Springbee Archetype

Springbee Archetype将帮助你快速生成基于springbee的Spring Boot项目

## 前置条件

- JDK1.8
- Maven3

## 项目说明

- 基于Spring Boot 2.3
- 集成了静态代码检查及单元测试覆盖率

## 项目创建

基于脚本创建属于自己的springbee项目，如下4个变量根据实际情况填写

| 属性          | 说明               |
| ------------- | ------------------ |
| ${groupId}    | 项目组织唯一标识符 |
| ${artifactId} | 项目唯一标识符     |
| ${version}    | 项目版本号         |
| ${package}    | 项目包名称         |

```bash
mvn archetype:generate \
-DarchetypeGroupId=org.springbee \
-DarchetypeArtifactId=maven-archetype-springbee \
-DarchetypeVersion=0.0.1-SNAPSHOT \
-DinteractiveMode=false \
-DarchetypeCatalog=local \
-DgroupId=${groupId} \
-DartifactId=${artifactId} \
-Dversion=${version} \
-Dpackage=${package}
```

