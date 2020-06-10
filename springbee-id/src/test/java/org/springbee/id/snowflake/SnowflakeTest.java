package org.springbee.id.snowflake;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springbee.id.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(properties = {
    "springbee.id.snowflake.data=0",
    "springbee.id.snowflake.work=0"
})
public class SnowflakeTest {

  @Autowired
  IdGenerator idGenerator;

  @Test
  public void test(){
    await().atMost(1, SECONDS).until(() -> {
      int loop =0;
      while(loop++<100){
        log.info("{}", idGenerator.genId());
      }
      return true;
    });
  }
}