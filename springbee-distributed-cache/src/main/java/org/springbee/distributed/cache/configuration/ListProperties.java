package org.springbee.distributed.cache.configuration;

import lombok.Data;

@Data
public class ListProperties {

  private int backupCount = 1;
  private int asyncBackupCount = 0;
  private int maxSize = Integer.MAX_VALUE;
}
