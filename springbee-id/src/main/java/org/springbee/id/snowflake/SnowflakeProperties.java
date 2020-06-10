package org.springbee.id.snowflake;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "springbee.id.snowflake")
public class SnowflakeProperties {

  public static final String PREFIX = "springbee.id.snowflake";

  private long startTime = -1;

  private int dataCenterId = -1;

  private int machineId = -1;

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getDataCenterId() {
    return dataCenterId;
  }

  public void setDataCenterId(int dataCenterId) {
    this.dataCenterId = dataCenterId;
  }

  public int getMachineId() {
    return machineId;
  }

  public void setMachineId(int machineId) {
    this.machineId = machineId;
  }
}