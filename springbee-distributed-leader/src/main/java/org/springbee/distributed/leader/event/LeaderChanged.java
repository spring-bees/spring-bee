package org.springbee.distributed.leader.event;

import akka.cluster.ClusterEvent;

public class LeaderChanged implements Event {

  public final ClusterEvent.LeaderChanged leaderChanged;

  public LeaderChanged(ClusterEvent.LeaderChanged leaderChanged) {
    this.leaderChanged = leaderChanged;
  }
}
