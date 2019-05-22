package com.damoncai.socket;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NIOServer {

    public static void main(String[] args) throws Exception {
        //1.得到一个ServerScoketChannel对象  老大
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //2.得到一个Selector对象  间谍
        Selector selector = Selector.open();

        //3.绑定一个端口
        serverSocketChannel.bind(new InetSocketAddress(9999));

        //4.设置非阻塞方式
        serverSocketChannel.configureBlocking(false);

        //5.把ServerSocketChannel对象注册给Selector对象
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //6.干活
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
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(key.isReadable()){  //读取客户端数据事件
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("客户端发来数据：" + new String(buffer.array()));
                }
                //6.3手动从集合中移除当前key,防止重复处理
                keyIterator.remove();
            }
        }
    }
}
