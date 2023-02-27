package com.zyx.Serve;

import com.zyx.common.Message_zyx;
import com.zyx.common.mType;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * @author 张宇森
 * @version 1.0
 * 文件传输
 */
@SuppressWarnings("all")
public class Fileinput {

    public void sendFile(String filePath,String desertPath,String senderId,String receiverId) throws Exception {

        //文件-》message
        Message_zyx message = new Message_zyx();
        message.setMessageType(mType.File_Message);
        message.setSender(senderId);
        message.setRecevier(receiverId);
        message.setFilePath(filePath);
        message.setDesertPath(desertPath);
        message.setTime(new Date().toString());

        //读取文件
        byte[] bytes=new byte[(int)new File(filePath).length()];
        FileInputStream fileInputStream = new FileInputStream(filePath);
        fileInputStream.read(bytes);  //读入到程序的字节数组
        //文件字节数组设置到message对象
        message.setFileBytes(bytes);

        fileInputStream.close();
        System.out.println("\n时间: "+message.getTime());
        System.out.println(""+message.getSender()+" 给 "+message.getRecevier()
                +" 发送文件: "+filePath+" 到对方的目录: "+desertPath);
        //发送
        ObjectOutputStream obj =
                new ObjectOutputStream(Cl_SeTreadList.getTread(senderId).getSocket().getOutputStream());
        obj.writeObject(message);
        System.out.println("发送完毕");

    }
}
