package com.damoncai.chat;

import com.damoncai.socket.NIOServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class ChatServer {
    private ServerSocketChannel listenerChannel; // 监听通道 老大
    private Selector selector; //选择器对象 间谍
    private static final int PORT = 9999; // 服务器端口

    public ChatServer(){
        try{
            //1.得到一个ServerScoketChannel对象  老大
            listenerChannel = ServerSocketChannel.open();
            //2.得到一个Selector对象  间谍
            selector = Selector.open();
            //3.绑定一个端口
            listenerChannel.bind(new InetSocketAddress(9999));
            //4.设置非阻塞方式
            listenerChannel.configureBlocking(false);
            //5.把ServerSocketChannel对象注册给Selector对象
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e){

        }
    }

    public void start() throws Exception{
        while (true){
            //6.1监控客户端
            if(selector.select(2000) == 0){ // nio非阻塞式的优势
                System.out.println("Server:没有客户端搭理我，我就干点别的事");
                continue;
            }
            //6.2得到SelectionKey，判断通道里的事件
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
                if(key.isAcceptable()){ //客户端连接事件
                    System.out.println("OP_ACCEPT");
                    SocketChannel socketChannel = listenerChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println(socketChannel.getRemoteAddress().toString().substring(1) + ":上线了...");
                }
                if(key.isReadable()){  //读取客户端数据事件
                   readMsg(key);
                }
                //6.3手动从集合中移除当前key,防止重复处理
                keyIterator.remove();
            }
        }
    }

    //读取客户端信息并广播出去
    public void readMsg(SelectionKey key)throws Exception{
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int count = channel.read(buffer);
        if(count > 0){
            String msg = new String(buffer.array());
            printInfo(msg);
            //发广播
            broadCast(channel,msg);
        }
    }

    //发广播
    public void broadCast(SocketChannel exceptChannel,String msg) throws Exception{
        System.out.println("开始广播...");
        for(SelectionKey key : selector.keys()){
            SelectableChannel selectableChannel = key.channel();
            if(selectableChannel instanceof SocketChannel && selectableChannel != exceptChannel){
                SocketChannel channel = (SocketChannel)selectableChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                channel.write(buffer);
            }
        }
    }
    //往控制台打印消息
    private void printInfo(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("[" + sdf.format(new Date()) + "] ->" + str);
    }
    public static void main(String[] args) throws Exception {
        new ChatServer().start();
    }


}
