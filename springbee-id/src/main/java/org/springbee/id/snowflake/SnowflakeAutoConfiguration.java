package org.springbee.id.snowflake;

import org.springbee.id.IdGenerator;
import org.springframework.context.annotation.Bean;

public class SnowflakeAutoConfiguration {

  @Bean
  public IdGenerator snowflake(SnowflakeProperties snowflakeProperties) {
    return new Snowflake(snowflakeProperties);
  }
}