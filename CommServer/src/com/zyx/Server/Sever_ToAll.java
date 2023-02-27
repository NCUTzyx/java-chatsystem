package com.zyx.Server;

import com.zyx.common.Message_zyx;
import com.zyx.common.mType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author 张宇森
 * @version 1.0
 * 服务器推送消息
 */
public class Sever_ToAll implements Runnable{

    private Scanner sc=new Scanner(System.in);

    @Override
    public void run() {

        //利用线程多次发送消息
        while (true) {

            System.out.println("请输入服务器要推送的新闻(输入end即可退出线程)");
            String news = sc.next();
            if(news.equals("end")){
                break;
            }
            //构建一个消息，群发消息
            Message_zyx message = new Message_zyx();
            message.setSender("服务器");
            message.setMessageType(mType.Ever_Message);
            message.setContents(news);
            message.setTime(new Date().toString());
            System.out.println("给所有人的通知: " + news);

            //遍历所有线程
            HashMap<String, Se_ClTread> hm = Se_ClTreadList.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                //在线用户
                String next = iterator.next().toString();
                Se_ClTread se_clTread = hm.get(next);
                try {
                    ObjectOutputStream obj =
                            new ObjectOutputStream(se_clTread.getSocket().getOutputStream());
                    obj.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        }
    }
}
