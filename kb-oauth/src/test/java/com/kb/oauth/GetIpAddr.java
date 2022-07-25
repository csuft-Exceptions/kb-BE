package com.kb.oauth;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author wjx
 * @create 2022/7/13 14:14
 */
public class GetIpAddr {

        public static void main(String[] args) throws Exception {
//            // 获得本机的所有网络接口
//            Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
//
//            while (nifs.hasMoreElements()) {
//                NetworkInterface nif = nifs.nextElement();
//
//                // 获得与该网络接口绑定的 IP 地址，一般只有一个
//                Enumeration<InetAddress> addresses = nif.getInetAddresses();
//                while (addresses.hasMoreElements()) {
//                    InetAddress addr = addresses.nextElement();
//
//                    if (addr instanceof Inet4Address) { // 只关心 IPv4 地址
//                        System.out.println("网卡接口名称：" + nif.getName());
//                        System.out.println("网卡接口地址：" + addr.getHostAddress());
//                        System.out.println();
//                    }
//                }
//            }
            System.out.println(getLANAddressOnWindows());
        }


    public static InetAddress getLANAddressOnWindows() {
        try {
            Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
            while (nifs.hasMoreElements()) {
                NetworkInterface nif = nifs.nextElement();

                if (nif.getName().startsWith("wlan2")) {
                    Enumeration<InetAddress> addresses = nif.getInetAddresses();

                    while (addresses.hasMoreElements()) {

                        InetAddress addr = addresses.nextElement();
                        if (addr.getAddress().length == 4) { // 速度快于 instanceof
                            return addr;
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace(System.err);
        }
        return null;
    }


}
