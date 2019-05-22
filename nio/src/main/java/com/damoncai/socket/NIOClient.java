package com.damoncai.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {

    public static void main(String[] args) throws Exception {
        //1.获取连接通道
        SocketChannel channel = SocketChannel.open();

        //2.设置为非阻塞方式
        channel.configureBlocking(false);

        //3.提供服务器的IP和端口号
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);

        //4.连接服务器端
        if(!channel.connect(inetSocketAddress)){
           while (!channel.finishConnect()){
               System.out.println("Client：连接服务器的同时我还可以干其他的事");
           }
        }

        //5.得到缓冲区并存入数据
        String msg = "hello,server";
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

        //6.发送数据
        channel.write(buffer);

        System.in.read();
    }
}
