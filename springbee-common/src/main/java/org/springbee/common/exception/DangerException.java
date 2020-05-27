package org.springbee.common.exception;

/**
 * Dangerous operation Exception.
 */
public class DangerException extends Exception {

  public DangerException(final String errorMessage, final Throwable err) {
    super(errorMessage, err);
  }

  public DangerException(final String errorMessage) {
    super(errorMessage);
  }

  public DangerException() {
    super();
  }
}