package org.springbee.distributed.leader;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "springbee.distributed.leader.enabled", havingValue = "true")
@ImportAutoConfiguration({DistributedLeaderProperties.class})
public class DistributedLeaderAutoConfiguration {

  private static Behavior<Void> rootBehavior() {
    return Behaviors.setup(context -> {
      context.spawn(DistributedLeaderListener.create(DistributedLeaderContext.getInstance()),
          "springbee-akka-cluster-listener");
      return Behaviors.empty();
    });
  }

  @Bean
  public Config akkaConfiguration(ConfigurableApplicationContext applicationContext,
      DistributedLeaderProperties distributedLeaderProperties) {
    return ConfigFactory
        .parseMap(distributedLeaderProperties.convertAkkaConfig(distributedLeaderProperties))
        .withFallback(ConfigFactory.defaultReference(applicationContext.getClassLoader()));
  }

  @Bean
  public ActorSystem rootActorSystem(Config akkaConfiguration) {
    ActorSystem system = ActorSystem
        .create(rootBehavior(), "springbee-akka-cluster", akkaConfiguration);
    //DistributedLeader.SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
    return system;
  }

//  @Bean
//  public ClusterSingleton clusterSingleton(ActorSystem rootActorSystem){
//    ClusterSingleton singleton = ClusterSingleton.get(rootActorSystem);
//    ActorRef<Command> proxy =
//        singleton.init(
//            SingletonActor.of(
//                Behaviors.supervise(LeaderActor.create())
//                    .onFailure(
//                        SupervisorStrategy.restartWithBackoff(
//                            Duration.ofSeconds(1), Duration.ofSeconds(10), 0.2)),
//                "ClusterLeaderActor"));
//    return singleton;
//  }
}