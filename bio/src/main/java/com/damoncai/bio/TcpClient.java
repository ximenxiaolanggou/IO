package com.damoncai.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: 西门小狼狗
 * @Date: 2019/5/19 18:02
 */
public class TcpClient {

    public static void main(String[] args) throws Exception {
        while (true){
            //1.创建Socker对象
            Socket socket = new Socket("127.0.0.1",9999);
            //2.从连接中获取输出流并发消息
            OutputStream os = socket.getOutputStream();
            System.out.println("请输入：");
            Scanner sc = new Scanner(System.in);
            String msg = sc.nextLine();
            os.write(msg.getBytes());
            //3.从连接中取出输入流并接收回话
            InputStream is = socket.getInputStream();//阻塞
            byte[] b = new byte[100];
            is.read(b);
            System.out.println("老板说：" + new String(b).trim());
            //4.关闭
            socket.close();
        }
    }
}
