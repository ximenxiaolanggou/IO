package com.damoncai.netty.chart;

/**
 * @Author: 西门小狼狗
 * @Date: 2019/5/23 9:58
 */

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义一个服务器端业务处理类
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static List<Channel> channels = new ArrayList<Channel>();

    //通道就绪
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel inChannel = ctx.channel();
        channels.add(inChannel);
        String msg = "[系统提醒]:" + inChannel.remoteAddress().toString().substring(1) + "上线了";
        System.out.println(msg);
    }

    //通道未就绪
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel inChannel = ctx.channel();
        channels.remove(inChannel);
        String msg = "[系统提醒]:" + inChannel.remoteAddress().toString().substring(1) + "下线了";
        System.out.println(msg);
    }

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel inChannel = ctx.channel();
        //将消息广播出去
        for(Channel channel : channels){
            if(inChannel != channel){
                channel.writeAndFlush("[" + inChannel.remoteAddress().toString().substring(1) + "]" + "说:" + s.trim() +"\n");
            }
        }
    }
}
