[![Build Status](https://travis-ci.org/spring-bees/spring-bee.svg?branch=master)](https://travis-ci.org/spring-bees/spring-bee.svg?branch=master) [![Coverage Status](https://coveralls.io/repos/github/spring-bees/spring-bee/badge.svg?branch=master)](https://coveralls.io/github/spring-bees/spring-bee?branch=master) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=spring-bees_spring-bee&metric=alert_status)](https://sonarcloud.io/dashboard?id=spring-bees_spring-bee) 

## 编译和运行代码

* 编译代码
   ```bash
      $ mvn clean install -DskipTests
   ```

* 编译代码、运行单元测试
   ```bash
      $ mvn clean install
   ```

* 编译代码、检查代码风格并且运行相关的单元测试. 
  ```bash
     $ mvn clean install checkstyle:check
  ```
  
## 路线图

[路线图](docs/roadmap.md)

## 参与贡献
* 代码风格

  [intellij java google style](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml) 

  [eclipse java google style](https://github.com/google/styleguide/blob/gh-pages/eclipse-java-google-style.xml)

* 静态代码检查

  [spotbugs](https://spotbugs.github.io/)

* 详情可浏览[代码提交指南](docs/contributing/submit-codes_zh.md)。  