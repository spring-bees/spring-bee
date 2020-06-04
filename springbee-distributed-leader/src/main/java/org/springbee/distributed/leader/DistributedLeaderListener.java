package org.springbee.distributed.leader;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.cluster.ClusterEvent;
import akka.cluster.typed.Cluster;
import akka.cluster.typed.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springbee.distributed.leader.event.Event;
import org.springbee.distributed.leader.event.LeaderChanged;
import org.springbee.distributed.leader.event.MemberChange;
import org.springbee.distributed.leader.event.ReachabilityChange;

/**
 * 集群事件监听
 */
@Slf4j
public final class DistributedLeaderListener extends AbstractBehavior<Event> {

  private static DistributedLeaderContext distributedLeaderContext;

  DistributedLeaderListener(ActorContext<Event> context) {
    super(context);
    Cluster cluster = Cluster.get(context.getSystem());
    // 节点启停事件
    ActorRef<ClusterEvent.MemberEvent> memberEventAdapter =
        context.messageAdapter(ClusterEvent.MemberEvent.class, MemberChange::new);
    cluster.subscriptions()
        .tell(Subscribe.create(memberEventAdapter, ClusterEvent.MemberEvent.class));

    // 节点检测事件
    ActorRef<ClusterEvent.ReachabilityEvent> reachabilityAdapter =
        context.messageAdapter(ClusterEvent.ReachabilityEvent.class, ReachabilityChange::new);
    cluster.subscriptions()
        .tell(Subscribe.create(reachabilityAdapter, ClusterEvent.ReachabilityEvent.class));

    // 领导节点切换事件
    ActorRef<ClusterEvent.LeaderChanged> leaderChangedAdapter =
        context.messageAdapter(ClusterEvent.LeaderChanged.class, LeaderChanged::new);
    cluster.subscriptions()
        .tell(Subscribe.create(leaderChangedAdapter, ClusterEvent.LeaderChanged.class));
  }

  public static Behavior<Event> create(DistributedLeaderContext context) {
    distributedLeaderContext = context;
    return Behaviors.setup(DistributedLeaderListener::new);
  }

  @Override
  public Receive<Event> createReceive() {
    return newReceiveBuilder()
//        .onMessage(ReachabilityChange.class, this::onReachabilityChange)
        .onMessage(MemberChange.class, this::onMemberChange)
        .onMessage(LeaderChanged.class, this::onLeaderChange)
        .build();
  }

//  private Behavior<Event> onReachabilityChange(ReachabilityChange event) {
//    if (event.reachabilityEvent instanceof ClusterEvent.UnreachableMember) {
//      getContext().getLog()
//          .info("Member detected as unreachable: {}", event.reachabilityEvent.member());
//    } else if (event.reachabilityEvent instanceof ClusterEvent.ReachableMember) {
//      getContext().getLog().info("Member back to reachable: {}", event.reachabilityEvent.member());
//    }
//    return this;
//  }

  private Behavior<Event> onMemberChange(MemberChange event) {
    if (event.memberEvent instanceof ClusterEvent.MemberUp) {
      distributedLeaderContext.putMember(event.memberEvent.member());
      getContext().getLog().info("Member is up: {}", event.memberEvent.member());
    } else if (event.memberEvent instanceof ClusterEvent.MemberRemoved) {
      distributedLeaderContext.removeMember(event.memberEvent.member());
      distributedLeaderContext.setLeader(event.memberEvent.member().address(), false);
      getContext().getLog().info("Member is removed: {} after {}",
          event.memberEvent.member(),
          ((ClusterEvent.MemberRemoved) event.memberEvent).previousStatus()
      );
    }
    return this;
  }

  private Behavior<Event> onLeaderChange(LeaderChanged event) {
    if (getContext().getSystem().address().equals(event.leaderChanged.getLeader())) {
      distributedLeaderContext.setLeader(event.leaderChanged.getLeader(), true);
    }
    return this;
  }

}