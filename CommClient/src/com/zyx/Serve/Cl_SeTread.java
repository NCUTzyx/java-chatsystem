package com.zyx.Serve;

import com.zyx.common.Message_zyx;
import com.zyx.common.mType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author 张宇森
 * @version 1.0
 */
@SuppressWarnings("all")
public class Cl_SeTread extends Thread{

    //线程需持有socket
    private Socket socket;

    //接收socket对象那个
    public Cl_SeTread(Socket socket){

        this.socket=socket;
    }

    //启动线程
    @Override
    public void run() {
        //Thread 需要和服务器通信
        while(true){
            //保持一直登陆
            System.out.println("客户端线程，随时读取数据");
            try {
                ObjectInputStream obm = new ObjectInputStream(socket.getInputStream());
                //如果服务器没有发送Message,线程会阻塞在这个
                Message_zyx message = (Message_zyx)obm.readObject();
                //判断message类型，然后做相应的业务处理
                //如果读到的是 服务器返回的在线用户列表
                if(message.getMessageType().equals(mType.Online_Run_User)){

                //取出在线列表
                    String[] users = message.getContents().split(" ");
                    System.out.println("\n========当前在线用户列表========");
                    for (int i=0;i<users.length;i++){
                        System.out.println("用户: "+users[i]);
                    }
                //普通通连天消息
                }else if(message.getMessageType().equals(mType.Common_Message)){

                    //显示在控制台
                    System.out.println("\n时间: "+message.getTime());
                    System.out.println(""+message.getSender()+"向"+message.getRecevier()+"说: "+message.getContents() );
                //群聊消息
                }else if (message.getMessageType().equals(mType.Ever_Message)){
                    //显示在控制台
                    System.out.println("\n时间: "+message.getTime());
                    System.out.println(""+message.getSender()+"通知: "+message.getContents() );
                //文件消息
                }else if(message.getMessageType().equals(mType.File_Message)){

                    //显示在控制台
                    System.out.println("\n时间: "+message.getTime());
                    System.out.println(""+message.getSender()+" 给"+
                            message.getRecevier()+" 发送文件: "+message.getFilePath()+" 到它的电脑的目录: "+message.getDesertPath());

                    //取出字节数组，通过文件输出流写入到磁盘
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDesertPath());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.flush();
                    System.out.println("\n 保存文件成功！");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //得到socket
    public Socket getSocket() {
        return socket;
    }
}
