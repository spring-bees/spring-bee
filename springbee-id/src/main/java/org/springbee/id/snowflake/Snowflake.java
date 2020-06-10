package org.springbee.id.snowflake;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springbee.id.IdGenerator;

@Slf4j
class Snowflake implements IdGenerator {

  // 每一部分占用的位数
//  private static final int TIME_LEN = 41; // 时间部分所占长度
  private static final int DATACENTER_BIT = 5; // 数据中心id所占长度
  private static final int MACHINE_BIT = 5; //机器id所占长度
  private static final int SEQUENCE_BIT = 12; // 毫秒内序列所占长度

  // 每一部分的最大值
  private final static int MAX_DATACENTER_NUM = ~(-1 << DATACENTER_BIT); // 数据中心id最大值 31
  private final static int MAX_MACHINE_NUM = ~(-1 << MACHINE_BIT); // 机器id最大值 31
  private static final long MAX_SEQUENCE = ~(-1 << SEQUENCE_BIT); //毫秒内序列的最大值 4095
  private static final int DATA_RANDOM = MAX_DATACENTER_NUM + 1; // 随机获取数据中心id的参数 32
  private static final int WORK_RANDOM = MAX_MACHINE_NUM + 1; // 随机获取机器id的参数 32

  // 每一部分向左的位移
  private final static long MACHINE_LEFT = SEQUENCE_BIT;
  private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
  private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

  private long startTime = 1338480000; // 定义起始时间 2012-06-01 00:00
  private long dataCenterId; // 数据中心
  private long machineId; // 机器标识
  private long sequence = 0L; // 序列号
  private long lastTimeStamp = -1L;// 上一次时间戳


  public Snowflake(SnowflakeProperties snowflakeProperties) {

    if (snowflakeProperties.getStartTime() > 0) {
      this.startTime = snowflakeProperties.getStartTime();
    }
    // 如果未定义数据中心ID，则根据 host name 取余，发生异常就获取 0到31之间的随机数
    if (snowflakeProperties.getDataCenterId() == -1) {
      try {
        this.dataCenterId = getHostId(Inet4Address.getLocalHost().getHostAddress(),
            MAX_DATACENTER_NUM);
      } catch (UnknownHostException e) {
        this.dataCenterId = new Random().nextInt(DATA_RANDOM);
      }
    } else {
      if (snowflakeProperties.getDataCenterId() > MAX_DATACENTER_NUM
          || snowflakeProperties.getDataCenterId() < 0) {
        throw new IllegalArgumentException(
            "datacenterId can't be greater than " + MAX_DATACENTER_NUM + " or less than 0");
      } else {
        this.dataCenterId = snowflakeProperties.getDataCenterId();
      }
    }
    // 如果未定义机器ID，则根据 host address 取余，发生异常就获取 0到31之间的随机数
    if (snowflakeProperties.getMachineId() == -1) {
      try {
        this.machineId = getHostId(Inet4Address.getLocalHost().getHostAddress(), MAX_MACHINE_NUM);
      } catch (UnknownHostException e) {
        this.machineId = new Random().nextInt(WORK_RANDOM);
      }
    } else {
      if (machineId > MAX_MACHINE_NUM || machineId < 0) {
        throw new IllegalArgumentException(
            "machineId can't be greater than " + MAX_MACHINE_NUM + " or less than 0");
      } else {
        this.machineId = snowflakeProperties.getMachineId();
      }
    }

    log.info("Snowflake: startTime={}, dataCenterId={}, machineId={}", this.startTime,
        this.dataCenterId, this.machineId);
  }

  public synchronized long genId() {
    long now = newstmp();

    //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
    if (now < lastTimeStamp) {
      throw new RuntimeException(
          String.format("Clock moved backwards %dms Refusing to generate id", startTime - now));
    }

    if (now == lastTimeStamp) {
      // 相同毫秒内，序列号自增
      sequence = (sequence + 1) & MAX_SEQUENCE;
      if (sequence == 0) {
        // 同一毫秒的序列数已经达到最大
        now = nextMillis();
      }
    } else {
      // 不同毫秒内，序列号置为0
      sequence = 0;
    }

    //上次生成ID的时间截
    lastTimeStamp = now;

    return (now - startTime) << TIMESTAMP_LEFT //时间戳部分
        | dataCenterId << DATACENTER_LEFT       //数据中心部分
        | machineId << MACHINE_LEFT             //机器标识部分
        | sequence;                             //序列号部分
  }

  // 获取下一不同毫秒的时间戳，不能与最后的时间戳一样
  private long nextMillis() {
    long mill = newstmp();
    while (mill <= lastTimeStamp) {
      mill = newstmp();
    }
    return mill;
  }

  private long newstmp() {
    return System.currentTimeMillis();
  }

  // 获取字符串s的字节数组，然后将数组的元素相加，对（max+1）取余
  private static int getHostId(String s, int max) {
    byte[] bytes = s.getBytes();
    int sums = 0;
    for (int b : bytes) {
      sums += b;
    }
    return sums % (max + 1);
  }

}