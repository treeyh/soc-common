package com.treeyh.common.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author TreeYH
 * @version 1.0
 * @description 网络工具类
 * @create 2019-05-17 18:05
 */
public class NetworkUtils {

    /**
     * 检测网络是否通常
     * @param strIp ip地址
     * @param port 端口
     * @return 1：网络通畅，0：网络不通
     */
    public static Integer telnetConnect(String strIp, int port) {
        Socket server = null;
        try {
            server = new Socket();
            InetSocketAddress address = new InetSocketAddress(strIp, port);
            server.connect(address, 5000);
            return 1;
        } catch (UnknownHostException e) {
            return 0;
        } catch (IOException e) {
            return 0;
        } finally {
            if (null != server) {
                try {
                    server.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
