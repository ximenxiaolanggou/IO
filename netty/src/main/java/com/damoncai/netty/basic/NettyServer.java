package com.damoncai.netty.basic;

/**
 * @Author: 西门小狼狗
 * @Date: 2019/5/22 19:28
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务器端
 */
public class NettyServer {

    public static void main(String[] args) throws Exception {
        //1.创建线程组：接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //2.创建线程组：处理网络操作
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //3.创建服务器端启动助手来配置参数
        ServerBootstrap sb = new ServerBootstrap();
        sb.group(bossGroup,workerGroup) //4.设置两个线程组
          .channel(NioServerSocketChannel.class) //5.使用NioServerSocketChannel作为服务器端通道的实现
          .option(ChannelOption.SO_BACKLOG,128) // 6.设置线程队列中等待连接的个数
          .childOption(ChannelOption.SO_KEEPALIVE,true)// 7.保持活动连接状态
          .childHandler(new ChannelInitializer<SocketChannel>() { //8. 创建一个通道初始化对象
              protected void initChannel(SocketChannel sc) throws Exception {
                  sc.pipeline().addLast(new NettyServerHandler()); //9. 往Pipeline链中添加自定义的handler类
              }
          });
        System.out.println("............Server is ready............");
        ChannelFuture cf = sb.bind(9999).sync(); //10. 端口绑定 非阻塞
        System.out.println("............Server is starting............");

        //11.关闭通道，关闭线程
        cf.channel().closeFuture().sync(); // 异步
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
