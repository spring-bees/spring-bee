package org.springbee.jdbc.dynamic;

/**
 * @author zhanglei
 */
public final class DataSourceContextHolder {

  private static final ThreadLocal<String> DATA_SOURCE_THREAD_LOCAL = new ThreadLocal<>();

  public static String get() {
    return DATA_SOURCE_THREAD_LOCAL.get();
  }

  public static void set(String key) {
    DATA_SOURCE_THREAD_LOCAL.set(key);
  }

  public static void remove() {
    DATA_SOURCE_THREAD_LOCAL.remove();
  }

}