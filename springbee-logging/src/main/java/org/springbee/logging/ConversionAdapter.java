package org.springbee.logging;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.Iterator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springbee.common.spring.ContextWrapper;

@Slf4j
public class ConversionAdapter extends MessageConverter {

  @Setter
  private LoggingSensitiveProperties loggingSensitiveProperties;

  @Override
  public String convert(ILoggingEvent event) {
    if (loggingSensitiveProperties == null) {
      if (ContextWrapper.getContext() != null) {
        if (ContextWrapper.getContext().containsBean("loggingSensitiveProperties")) {
          loggingSensitiveProperties = ContextWrapper.getContext()
              .getBean(LoggingSensitiveProperties.class);
        }
      }
    }
    if (loggingSensitiveProperties == null) {
      return event.getFormattedMessage();
    } else {
      String msg = event.getFormattedMessage();
      try {
        return sensitiveRegexReplacement(msg);
      } catch (Exception e) {
        log.error("sensitive regex replacement fail:", e);
        return msg;
      }
    }
  }

  private String sensitiveRegexReplacement(String msg) {
    Iterator<Integer> it = loggingSensitiveProperties.getSensitive().keySet().iterator();
    while (it.hasNext()) {
      RegexReplacement regexReplacement = loggingSensitiveProperties.getSensitive().get(it.next());
      msg = msg.replaceAll(regexReplacement.getRegex(), regexReplacement.getReplacement());
    }
    return msg;
  }
}