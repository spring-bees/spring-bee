![build](https://github.com/coolbeevip/spring-bee/workflows/build/badge.svg)

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