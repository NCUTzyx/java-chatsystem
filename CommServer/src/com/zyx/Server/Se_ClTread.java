package com.zyx.Server;

import com.zyx.common.Message_zyx;
import com.zyx.common.mType;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author 张宇森
 * @version 1.0
 * 服务端对象和客户端保持通讯的线程
 */
public class Se_ClTread extends Thread{

    private Socket socket;
    private String userId; //指定服务端的userId

    public Se_ClTread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        //不停读入数据或写入数据
        while(true){
            System.out.println("服务端和客户端"+userId+"保持通讯....");
            try {
                ObjectInputStream obm = new ObjectInputStream(socket.getInputStream());
                Message_zyx message = (Message_zyx)obm.readObject();

                //根据message做相应的业务处理
                if(message.getMessageType().equals(mType.Online_Get_User)){

                    //客户端要在线用户列表
                    System.out.println(message.getSender()+" 向服务器申请查看在线用户");
                    String online = Se_ClTreadList.getOline();
                    //返回message
                    //构建message对象返回给客户端
                    Message_zyx message2 = new Message_zyx();
                    message2.setMessageType(mType.Online_Run_User);
                    message2.setContents(online);
                    message2.setRecevier(message.getSender());
                    //写入到数据通道，返回给客户端
                    ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                    obj.writeObject(message2);

                //客户端退出
                }else if(message.getMessageType().equals(mType.Comm_Exit)){

                    System.out.println(message.getSender()+"要退出客户端了");
                    //在集合删除线程
                    Se_ClTreadList.remove(message.getSender());
                    //关闭连接
                    socket.close();
                    //退出
                    break;
                //发送消息
                }else if (message.getMessageType().equals(mType.Common_Message)){

                    //根据message获得userId,再得到对应的线程
                    Se_ClTread tread = Se_ClTreadList.getTread(message.getRecevier());
                    //转发给指定客户端
                    ObjectOutputStream obj = new ObjectOutputStream(tread.getSocket().getOutputStream());
                    //转发
                    obj.writeObject(message);
                //发送群消息
                }else if(message.getMessageType().equals(mType.Ever_Message)){

                    //遍历集合
                    HashMap<String, Se_ClTread> hm = Se_ClTreadList.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();

                    while (iterator.hasNext()) {

                        //在线用户的id
                        String next =  iterator.next().toString();
                        if (!next.equals(message.getSender())){

                            //转发message
                            ObjectOutputStream obj =
                                    new ObjectOutputStream(hm.get(next).getSocket().getOutputStream());

                            obj.writeObject(message);
                        }
                    }
                //发送文件
                }else if (message.getMessageType().equals(mType.File_Message)){

                    //根据userId得到对应的线程，将message对象转发
                    Se_ClTread tread = Se_ClTreadList.getTread(message.getRecevier());
                    ObjectOutputStream obj =
                            new ObjectOutputStream(tread.getSocket().getOutputStream());

                    //转发
                    obj.writeObject(message);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
