package org.springbee.distributed.leader.event;

import akka.cluster.ClusterEvent;

public class ReachabilityChange implements Event {

  public final ClusterEvent.ReachabilityEvent reachabilityEvent;

  public ReachabilityChange(ClusterEvent.ReachabilityEvent reachabilityEvent) {
    this.reachabilityEvent = reachabilityEvent;
  }
}
