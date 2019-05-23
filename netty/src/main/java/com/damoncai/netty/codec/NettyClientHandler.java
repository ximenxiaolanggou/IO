package com.damoncai.netty.codec;

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
        BookMessage.Book book = BookMessage.Book.newBuilder().setName("SpringBoot").setId(1).build();
        ctx.writeAndFlush(book);
    }
}
