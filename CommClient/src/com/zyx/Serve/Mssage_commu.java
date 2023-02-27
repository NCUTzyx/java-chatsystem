package com.zyx.Serve;

import com.zyx.common.Message_zyx;
import com.zyx.common.mType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * @author 张宇森
 * @version 1.0
 * 提供消息
 */
@SuppressWarnings("all")
public class Mssage_commu {

    public void sendToOther(String senderId,String receiverId,String content){

        Message_zyx message = new Message_zyx();
        message.setMessageType(mType.Common_Message);
        message.setSender(senderId);
        message.setRecevier(receiverId);
        message.setContents(content);
        message.setTime(new Date().toString());
        System.out.println("\n时间: "+message.getTime());
        System.out.println(senderId+"向"+receiverId+"说: "+content);

        //发送给服务端
        try {
            ObjectOutputStream obj =
                    new ObjectOutputStream(Cl_SeTreadList.getTread(senderId).getSocket().getOutputStream());
            obj.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void sendEverone(String senderId,String content){

        Message_zyx message = new Message_zyx();
        message.setMessageType(mType.Ever_Message );
        message.setSender(senderId);
        message.setContents(content);
        message.setTime(new Date().toString());
        System.out.println("\n时间: "+message.getTime());
        System.out.println(senderId+"在群聊里说: "+content);

        //发送给服务端
        try {
            ObjectOutputStream obj =
                    new ObjectOutputStream(Cl_SeTreadList.getTread(senderId).getSocket().getOutputStream());
            obj.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
