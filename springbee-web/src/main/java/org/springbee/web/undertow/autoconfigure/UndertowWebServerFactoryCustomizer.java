package org.springbee.web.undertow.autoconfigure;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

public class UndertowWebServerFactoryCustomizer implements
    WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

  int bufferPool;
  String serverHost;
  int serverSecondaryPort;

  public UndertowWebServerFactoryCustomizer(int bufferPool, String serverHost,
      int serverSecondaryPort) {
    this.bufferPool = bufferPool;
    this.serverHost = serverHost;
    this.serverSecondaryPort = serverSecondaryPort;
  }

  @Override
  public void customize(UndertowServletWebServerFactory factory) {
    factory.addDeploymentInfoCustomizers(deploymentInfo -> {
      WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
      webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, this.bufferPool));
      deploymentInfo
          .addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo",
              webSocketDeploymentInfo);
    });
    if (this.serverSecondaryPort > 0) {
      factory.getBuilderCustomizers().add(builder -> {
        builder.addHttpListener(this.serverSecondaryPort, this.serverHost);
      });
    }
  }
}