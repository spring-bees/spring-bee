package org.springbee.logging.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springbee.common.spring.ContextWrapper;
import org.springbee.logging.LoggingSensitiveProperties;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

@Slf4j
@SuppressWarnings("unchecked")
public class LoggingAutoConfiguration implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Bean
  public ContextWrapper contextWrapper() {
    ContextWrapper contextWrapper = new ContextWrapper();
    contextWrapper.setApplicationContext(this.applicationContext);
    return contextWrapper;
  }

  @Bean(name = "loggingSensitiveProperties")
  public LoggingSensitiveProperties loggingSensitiveProperties() {
    LoggingSensitiveProperties loggingSensitiveProperties = Binder
        .get(this.applicationContext.getEnvironment())
        .bind("logging", LoggingSensitiveProperties.class).orElse(null);
    return loggingSensitiveProperties;
  }
}