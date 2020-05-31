package org.springbee.common.spring;

import org.springframework.context.ApplicationContext;

public class ContextWrapper {

  private static ApplicationContext context;

  public synchronized static void setApplicationContext(ApplicationContext applicationContext) {
    ContextWrapper.context = applicationContext;
  }

  public static ApplicationContext getContext() {
    return context;
  }

}