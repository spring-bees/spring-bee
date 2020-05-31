package org.springbee.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(properties = {
    "server.host=0.0.0.0",
    "logging.sensitive[0].regex=(\\\\d{3})\\\\d{4}(\\\\d{4})",
    "logging.sensitive[0].replacement=$1****$2"
})
public class LoggingApplicationTest {

  Exception ex = new Exception("Oops");

  @Autowired
  LoggingSensitiveProperties loggingSensitiveProperties;

  @Test
  public void testSensitivePhone() {
    String original = "my number is 18610099300";
    String expected = "my number is 186****9300";
    LoggingEvent le = new LoggingEvent(
        ch.qos.logback.core.pattern.FormattingConverter.class.getName(), (Logger) log,
        Level.INFO, original, ex, null);
    ConversionAdapter converter = new ConversionAdapter();
    converter.setLoggingSensitiveProperties(loggingSensitiveProperties);

    StringBuilder buf = new StringBuilder();
    converter.write(buf, le);
    assertEquals(expected, buf.toString());
  }
}