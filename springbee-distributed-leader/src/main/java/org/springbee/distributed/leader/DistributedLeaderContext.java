package org.springbee.distributed.leader;

import akka.actor.Address;
import akka.cluster.Member;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistributedLeaderContext {

  private static final DistributedLeaderContext instance = new DistributedLeaderContext();
  private volatile boolean isLeader = false;
  private Map<Address, Member> members = new HashMap<>();

  public static DistributedLeaderContext getInstance() {
    return instance;
  }

  public boolean isLeader() {
    return isLeader;
  }

  public void setLeader(Address address, boolean leader) {
    isLeader = leader;
    if (this.isLeader) {
      log.info("I'm leader {}", address);
    } else {
      log.info("I'm follower {}", address);
    }
  }

  public void putMember(Member member) {
    members.put(member.address(), member);
  }

  public void removeMember(Member member) {
    members.remove(member.address());
  }
}
