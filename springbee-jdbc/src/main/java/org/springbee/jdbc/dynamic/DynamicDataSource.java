package org.springbee.jdbc.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * @author zhanglei
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

  @Nullable
  @Override
  protected Object determineCurrentLookupKey() {
    return DataSourceContextHolder.get();
  }
}