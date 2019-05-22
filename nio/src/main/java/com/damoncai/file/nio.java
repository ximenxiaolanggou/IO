package com.damoncai.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class nio {

    public static void main(String[] args)throws  Exception {
        fun_3();
    }

    /**
     * 写文件
     * @throws Exception
     */
    public static void fun_1() throws  Exception{
        //1.创建输出流
        FileOutputStream fos = new FileOutputStream("hello.text");
        //2.从流中得到一个通道
        FileChannel channel = fos.getChannel();
        //3.提供一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //4.向缓冲区中存入数据
        buffer.put("hello.damoncai".getBytes());
        //5.翻转缓冲区
        buffer.flip();
        //6.把缓冲区写到通道中
        channel.write(buffer);
        //7.关闭
        fos.close();
    }
    /**
     * 读文件
     * @throws Exception
     */
    public static void fun_2() throws  Exception{
        File file = new File("hello.text");
        //1.创建输入流
        FileInputStream fis = new FileInputStream(file);
        //2.从流中得到一个通道
        FileChannel channel = fis.getChannel();
        //3.准备个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        //4.从通道里读取数据并保存到缓冲区中
        channel.read(buffer);
        System.out.println(new String(buffer.array()));
        //5.关闭
        fis.close();
    }

    /**
     * 文件复制
     * @throws Exception
     */
    public static void fun_3() throws  Exception{
        //1.创建输入流和输出流
        FileInputStream fis = new FileInputStream("hello.text");
        FileOutputStream fos = new FileOutputStream("e:\\test\\hello.text");

        //2.获取两个通道
        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();

        //3.复制  0:从头取数据 size()取数据
        fosChannel.transferFrom(fisChannel, 0, fisChannel.size());

        //4.关闭
        fis.close();
        fos.close();
    }
}
