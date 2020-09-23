package site.wanjiahao.utils;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;

public class PortUtils {

    // 用于检查redis是否启动
    public static boolean isStart(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void checkPort(int port, String serverName, boolean shutdown) {
        if (isStart(port)) {
            if (shutdown) {
                String message = String.format("在端口 %d 未检查得到 %s 启动%n", port, serverName);
                JOptionPane.showMessageDialog(null, message);
                System.exit(1);
            } else {
                String message = String.format("在端口 %d 未检查得到 %s 启动%n,是否继续?", port, serverName);
                if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(null,
                        message)) {
                    System.exit(1);
                }
            }
        }
    }
}
