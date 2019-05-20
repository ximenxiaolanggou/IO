package com.damoncai.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: 西门小狼狗
 * @Date: 2019/5/19 18:11
 */
public class TcpServer {
    public static void main(String[] args) throws Exception {
        //1.创建ServerSocket对象
        ServerSocket ss = new ServerSocket(9999);

        while (true){
            //2.监听客户端
            Socket socket = ss.accept();//阻塞
            //3.从连接中取出输入流来接收消息
            InputStream is = socket.getInputStream();//阻塞
            byte[] b = new byte[100];
            is.read(b);
            String clientIP = socket.getInetAddress().getHostName();
            System.out.println(clientIP + "说：" + new String(b).trim());
            //4.从连接中获取输出流并回话
            OutputStream os = socket.getOutputStream();
            os.write("没钱".getBytes());
            //5.关闭
            socket.close();
        }
    }
}
