package org.springbee.distributed.leader;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootTest(properties = {
    "springbee.distributed.leader.port=2551",
    "springbee.distributed.leader.seed-nodes[0]=0.0.0.0:2551"
},classes = {DistributedLeaderContextTest.class})
public class DistributedLeaderContextTest {

  @Autowired
  ApplicationContext applicationContext;

  ActorSystem actorOne;
  ActorSystem actorTwo;

  DistributedLeaderContext contextOne = new DistributedLeaderContext();
  DistributedLeaderContext contextTwo = new DistributedLeaderContext();

  @BeforeEach
  public void setup() {
    DistributedLeaderProperties one = new DistributedLeaderProperties();
    one.setHost("0.0.0.0");
    one.setPort(2551);
    one.setSeedNodes(new String[]{"0.0.0.0:2551"});
    DistributedLeaderProperties two = new DistributedLeaderProperties();
    two.setHost("0.0.0.0");
    two.setPort(2552);
    two.setSeedNodes(new String[]{"0.0.0.0:2551"});

    actorOne = ActorSystem
        .create(Behaviors.setup(context -> {
          context.spawn(DistributedLeaderListener.create(contextOne),
              "springbee-akka-cluster-listener");
          return Behaviors.empty();
        }), "springbee-akka-cluster", akkaConfiguration(one));
    actorTwo = ActorSystem
        .create(Behaviors.setup(context -> {
          context.spawn(DistributedLeaderListener.create(contextTwo),
              "springbee-akka-cluster-listener");
          return Behaviors.empty();
        }), "springbee-akka-cluster", akkaConfiguration(two));
  }

  @AfterEach
  public void tearDown() {
    actorOne.terminate();
    actorTwo.terminate();
    await().atMost(5, SECONDS).until(() -> actorOne.whenTerminated().isCompleted());
    await().atMost(5, SECONDS).until(() -> actorTwo.whenTerminated().isCompleted());
  }

  private Config akkaConfiguration(DistributedLeaderProperties distributedLeaderProperties) {
    return ConfigFactory
        .parseMap(distributedLeaderProperties.convertAkkaConfig(distributedLeaderProperties))
        .withFallback(ConfigFactory.defaultReference(applicationContext.getClassLoader()));
  }

  @Test
  public void testLeader() {
    await().atMost(1, SECONDS).until(() -> contextTwo.isLeader() != contextOne.isLeader());
  }

  @Test
  public void testLeaderChange() {
    if (contextOne.isLeader()) {
      actorOne.terminate();
    } else {
      actorTwo.terminate();
    }
    await().atMost(1, SECONDS).until(() -> contextTwo.isLeader() != contextOne.isLeader());
  }
}