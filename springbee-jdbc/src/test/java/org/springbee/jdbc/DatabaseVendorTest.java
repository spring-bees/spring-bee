package org.springbee.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springbee.jdbc.mapper.MasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhanglei
 */
@Slf4j
@SpringBootTest(properties = {
    "spring.datasource.type=com.zaxxer.hikari.HikariDataSource",
    "spring.datasource.url=jdbc:sqlite:memory:masterdb?cache=shared",
    "spring.datasource.driverClassName=org.sqlite.JDBC",
    "spring.datasource.username=sa",
    "spring.datasource.password=password",
    "spring.datasource.platform=sqlite"})
public class DatabaseVendorTest {

  @Autowired
  private MasterMapper masterMapper;

  @Test
  public void testNow() {
    masterMapper.getNow();
  }
}