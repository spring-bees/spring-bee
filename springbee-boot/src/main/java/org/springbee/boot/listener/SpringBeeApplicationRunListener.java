package org.springbee.boot.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.util.ResourceUtils;

/**
 * @author zhanglei
 */
@Slf4j
public class SpringBeeApplicationRunListener implements SpringApplicationRunListener {

  public SpringBeeApplicationRunListener(SpringApplication application, String[] args) {
  }

  @Override
  public void environmentPrepared(ConfigurableEnvironment environment) {
    Properties properties = new Properties();
    try {
      File file = ResourceUtils.getFile("classpath:springbee-default.properties");
      try (InputStream in = new FileInputStream(file)) {
        properties.load(in);
      }
      environment.getPropertySources()
          .addFirst(new PropertiesPropertySource("springbee-default", properties));
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }
//
//  @Override
//  public void contextPrepared(ConfigurableApplicationContext context) {
//    log.info("contextPrepared...");
//  }
//
//  @Override
//  public void contextLoaded(ConfigurableApplicationContext context) {
//    log.info("contextLoaded...");
//  }
//
//  @Override
//  public void started(ConfigurableApplicationContext context) {
//    log.info("started...");
//  }
//
//  @Override
//  public void running(ConfigurableApplicationContext context) {
//    log.info("running...");
//  }
//
//  @Override
//  public void failed(ConfigurableApplicationContext context, Throwable exception) {
//    log.info("failed...");
//  }
}