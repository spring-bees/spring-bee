package org.springbee.common.network;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class InetAddressUtilTest {

  private InetAddressUtil inetAddressUtil = InetAddressUtil.builder().build();

  @Test
  public void testIpv6() {
    assertFalse(inetAddressUtil.isIpv6("127.0.0.1"));
    assertTrue(inetAddressUtil.isIpv6("fe80::aede:48ff:fe00:1122%en5"));
  }

  @Test
  public void testConvertAddress() {
    assertEquals(inetAddressUtil.convertAddress("127.0.0.1",8080), "127.0.0.1:8080");
    assertEquals(inetAddressUtil.convertAddress("fe80::aede:48ff:fe00:1122%en5",8080), "[fe80::aede:48ff:fe00:1122%en5]:8080");
  }
}