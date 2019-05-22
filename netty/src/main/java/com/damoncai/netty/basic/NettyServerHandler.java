package com.damoncai.netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Author: 西门小狼狗
 * @Date: 2019/5/22 19:28
 */

/**
 * 服务器端的业务处理类
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取事件
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        System.out.println("Server-ChannelHandlerContext:" + ctx);
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("客户端发来消息：" + buf.toString(CharsetUtil.UTF_8));
    }

    //读取完毕事件
    public void channelReadComplete(ChannelHandlerContext ctx){
        //Unpooled：是用来操作缓冲区的工具类
        ctx.writeAndFlush(Unpooled.copiedBuffer("就是没钱",CharsetUtil.UTF_8));
    }

    //异常发生事件
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println("异常信息是：" + cause.getMessage());
        ctx.close();
    }
}
