package com.damoncai.chat;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author: 西门小狼狗
 * @Date: 2019/5/22 17:03
 */
public class ChatClient {
    private final String HOST = "127.0.0.1"; //服务器地址
    private int PORT = 9999; // 服务器端口
    private SocketChannel socketChannel; // 网络通道
    private String username; // 聊天用户

    public ChatClient(){
        try{
            //1.得到一个网络通道
            socketChannel = SocketChannel.open();
            //2.设置阻塞方式
            socketChannel.configureBlocking(false);
            //3.提供服务器端的地址和端口
            InetSocketAddress inetSocketAddress = new InetSocketAddress(HOST,PORT);
            //4.连接服务器端
            if(!socketChannel.connect(inetSocketAddress)){
                while (!socketChannel.finishConnect()){
                    System.out.println("Client:连接服务器的同时我还可以干一些其他的事情");
                }
            }
            //5.得到客户端的IP地址和端口信息，作为聊天用户名使用
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println("--------------------Client("+username+") is ready --------------------");

        }catch (Exception e){

        }
    }
    //向服务器发送消息
    public void sendMsg(String msg) throws Exception{
        if(msg.equalsIgnoreCase("bye")){
            socketChannel.close();
            return;
        }
        msg = username + "说：" + msg;
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(byteBuffer);
    }
    //从服务器端接收数据
    public void receiveMsg()throws Exception{
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int count = socketChannel.read(byteBuffer);
        if(count > 0){
            String msg = new String(byteBuffer.array());
            System.out.println(msg.trim());
        }
    }
}
