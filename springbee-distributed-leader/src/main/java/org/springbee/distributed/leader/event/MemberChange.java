package org.springbee.distributed.leader.event;

import akka.cluster.ClusterEvent;

public class MemberChange implements Event {

  public final ClusterEvent.MemberEvent memberEvent;

  public MemberChange(ClusterEvent.MemberEvent memberEvent) {
    this.memberEvent = memberEvent;
  }
}
