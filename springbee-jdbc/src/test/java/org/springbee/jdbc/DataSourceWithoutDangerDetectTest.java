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
@SpringBootTest(properties = {"springbee.datasource.sql.protection.enabled=false"})
public class DataSourceWithoutDangerDetectTest {

  @Autowired
  private MasterMapper masterMapper;

  @Test
  public void testWithoutSqlProtection() {
    masterMapper.dropTable();
  }
}