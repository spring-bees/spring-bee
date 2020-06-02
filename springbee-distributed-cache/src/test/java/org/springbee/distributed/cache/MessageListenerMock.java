package org.springbee.distributed.cache;

import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;

public class MessageListenerMock<T> implements MessageListener<String> {

  private String message;

  @Override
  public void onMessage(Message<String> message) {
    this.message = message.getMessageObject();
  }

  public String getMessage() {
    return message;
  }
}