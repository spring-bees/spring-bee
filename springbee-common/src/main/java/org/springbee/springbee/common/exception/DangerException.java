package org.springbee.springbee.common.exception;

/**
 * Dangerous operation Exception.
 */
public class DangerException extends Exception {

  public DangerException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }

  public DangerException(String errorMessage) {
    super(errorMessage);
  }

  public DangerException() {
    super();
  }
}