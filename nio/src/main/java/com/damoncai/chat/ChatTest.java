package com.damoncai.chat;

import java.util.Scanner;

/**
 * @Author: 西门小狼狗
 * @Date: 2019/5/22 17:23
 */
public class ChatTest {

    public static void main(String[] args)throws Exception {
        final ChatClient chatClient = new ChatClient();
        new Thread(){
            public void run(){
                while (true) {
                    try {
                        chatClient.receiveMsg();
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String msg = scanner.nextLine();
            chatClient.sendMsg(msg);
        }
    }
}
