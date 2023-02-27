package com.zyx.Serve;

import com.zyx.common.Message_zyx;
import com.zyx.common.User_zyx;
import com.zyx.common.mType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author 张宇森
 * @version 1.0
 * 用户注册和登陆验证
 */
@SuppressWarnings("all")
public class isUser_zyx {

    //创建User对象属性-
    private User_zyx user = new User_zyx();
    //创建socket对象属性-
    private Socket socket;

    //根据userId 和 userpw 到服务器验证该用户
    public boolean UserCheck(String userId, String userpw) throws IOException, ClassNotFoundException {

        boolean lm = false;
        //user对象
        user.setUserId(userId);
        user.setUserPwd(userpw);
        //连接到服务端,发送对象
        socket = new Socket(InetAddress.getLocalHost(), 8888);
        //得到ObjectOutputStream对象
        ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
        obj.writeObject(user);

        //读取从服务端回复的Message对象
        ObjectInputStream obm = new ObjectInputStream(socket.getInputStream());
        Message_zyx mes = (Message_zyx) obm.readObject();

        //登录成功
        if (mes.getMessageType().equals(mType.Sign_Success)) {

            //创建一个和服务器端保持通讯的线程 -》线程类
            //创建线程
            Cl_SeTread cst = new Cl_SeTread(socket);
            //启动线程
            cst.start();
            //客户端拓展，将线程放入集合保管
            Cl_SeTreadList.addTread(userId, cst);
            lm = true;
        } else {
            //登录失败,不能启动线程，关闭socket
            socket.close();
        }
        return lm;
    }
    //向服务器端请求在线用户列表
    public void onlineList() throws IOException {

        //发送message
        Message_zyx message = new Message_zyx();
        message.setMessageType(mType.Online_Get_User);
        message.setSender(user.getUserId());

        //发送给服务器
        //得到当前线程的Socket 对应的输出对象
        ObjectOutputStream obm =
                new ObjectOutputStream(Cl_SeTreadList.getTread(user.getUserId()).getSocket().getOutputStream());
        obm.writeObject(message); //发送一个Message对象，向服务端要在线用户列表

    }
    //退出客户端，并向服务器发送退出系统的message对象
    public void ExitTread() throws IOException {

        Message_zyx message = new Message_zyx();
        message.setMessageType(mType.Comm_Exit);
        message.setSender(user.getUserId()); //指定客户端

        //发送message
        ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
        obj.writeObject(message);
        System.out.println(user.getUserId()+"退出了系统");
        System.exit(0); //结束系统
    }


}
