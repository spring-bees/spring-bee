package org.springbee.common.exception;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

/**
 * @author zhanglei
 */
public class ExceptionTest {

  @Test
  public void testDangerException() {
    assertThatThrownBy(() -> {
      throw new DangerException();
    }).isInstanceOf(DangerException.class);
    assertThatThrownBy(() -> {
      throw new DangerException("Oops");
    }).hasMessage("Oops");
    assertThatThrownBy(() -> {
      throw new DangerException("Oops", new RuntimeException());
    }).hasMessage("Oops");
  }
}