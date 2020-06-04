package ${package};

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DemoApplicationTest {
	
  @Autowired
  DemoApplication application;

  @Test
  public void testHello() {
	assertTrue(("Hello world!".equals(application.hello("world"))));
  }

}
