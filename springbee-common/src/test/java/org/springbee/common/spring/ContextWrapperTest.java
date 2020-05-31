package org.springbee.common.spring;


import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class ContextWrapperTest {

  @Test
  public void testContextWrapper() {
    ApplicationContext context = new GenericApplicationContext();
    ContextWrapper contextWrapper = new ContextWrapper();
    contextWrapper.setApplicationContext(context);
    assertSame(contextWrapper.getContext(), context);
  }
}