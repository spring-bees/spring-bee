package org.springbee.common.network;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.Builder;

@Builder
public class InetAddressUtil {

  public boolean isIpv6(String ip) {
    boolean ipv6 = false;
    try {
      InetAddress ia = InetAddress.getByName(ip);
      if (ia instanceof Inet4Address) {
        ipv6 = false;
      } else if (ia instanceof Inet6Address) {
        ipv6 = true;
      }
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return ipv6;
  }

  public String convertAddress(String ip, int port) {
    if (isIpv6(ip)) {
      return String.format("[%s]:%d", ip, port);
    } else {
      return String.format("%s:%d", ip, port);
    }
  }
}
