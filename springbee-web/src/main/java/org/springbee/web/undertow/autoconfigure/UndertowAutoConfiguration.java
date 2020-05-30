package org.springbee.web.undertow.autoconfigure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class UndertowAutoConfiguration {

  @Bean
  public UndertowWebServerFactoryCustomizer customizationBean(
      @Value("${server.undertow.buffer-pool:1024}") int bufferPool,
      @Value("${server.host:0.0.0.0}") String serverHost,
      @Value("${server.secondary.port:0}") int serverSecondaryPort) {
    return new UndertowWebServerFactoryCustomizer(bufferPool, serverHost, serverSecondaryPort);
  }
}