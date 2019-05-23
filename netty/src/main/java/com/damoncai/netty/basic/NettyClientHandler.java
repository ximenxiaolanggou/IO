package com.damoncai.netty.basic;

/**
 * @Author: 西门小狼狗
 * @Date: 2019/5/22 19:28
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 客户端业务处理类
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //就绪事件
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println("Client-ChannelHandlerContext:" + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("老板，还钱！", CharsetUtil.UTF_8));
    }

    //读取事件
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("服务器端发来消息：" + buf.toString(CharsetUtil.UTF_8));
    }
}
