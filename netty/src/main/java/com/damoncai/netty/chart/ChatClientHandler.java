package com.damoncai.netty.chart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: 西门小狼狗
 * @Date: 2019/5/23 9:58
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println( s.trim() + "\n");
    }
}
