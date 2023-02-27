package com.zyx.Server;

import com.zyx.common.Message_zyx;
import com.zyx.common.User_zyx;
import com.zyx.common.mType;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 张宇森
 * @version 1.0
 * 服务端，监听端口，等待客户链接，保持通讯
 */
@SuppressWarnings("all")
public class server_zyx {

    private ServerSocket serverSocket =null;

    //创建集合存放多个用户-》可实现并发
    //HashMap没有处理线程安全
    //ConcurrentHashMap处理线程安全，即线程同步
    private static ConcurrentHashMap<String,User_zyx> userHashMap=new ConcurrentHashMap();
    static {//初始化集合
        userHashMap.put("张三",new User_zyx("张三","zyx123"));
        userHashMap.put("陈四",new User_zyx("陈四","rtty123"));
        userHashMap.put("杨五",new User_zyx("杨五","ym123"));
        userHashMap.put("尹六",new User_zyx("尹六","yrj123"));
        userHashMap.put("刘七",new User_zyx("刘七","lyx123"));
        userHashMap.put("不知名网友",new User_zyx("不知名网友","123456"));
    }
    //验证是否有效
    private boolean checkuser(String userId,String userPw){

        boolean flag=false;
        User_zyx user = userHashMap.get(userId);
        if(user==null){  //说明用户不存在啊
            System.out.println("用户不存在");
            return false;
        }
        String userPwd = user.getUserPwd();
        if(!userPwd.equals(userPw)){ ////说明密码错误
            System.out.println("密码错误");
            return false;
        }
        return true;
    }

    //构造器
    public server_zyx() throws IOException, ClassNotFoundException {


        System.out.println("服务端在9998 端口监听...");
        //启动推送线程
        new  Thread(new Sever_ToAll()).start();

        try {
            serverSocket = new ServerSocket(8888);
            //循环监听，连接
            while(true) {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                //得到客户端对象
                ObjectInputStream obm = new ObjectInputStream(inputStream);
                //读取User对象
                User_zyx user = (User_zyx)obm.readObject();
                //得到socket关联的对象输出流
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());

                //创建Message对象，用于回复客户端
                Message_zyx message = new Message_zyx();

                //验证用户
                if(checkuser(user.getUserId(), user.getUserPwd())){
                    //登陆成功
                    message.setMessageType(mType.Sign_Success);
                    //回复
                    obj.writeObject(message);
                    //创建一个线程，和客户端保持通讯，该线程需持有socket对象
                    Se_ClTread se_clTread = new Se_ClTread(socket, user.getUserId());
                    //启动线程
                    se_clTread.start();
                    //把该线程对象放入到一个集合中进行管理.
                    Se_ClTreadList.addTread(user.getUserId(),se_clTread);

                }else{
                    //登录失败
                    message.setMessageType(mType.Sign_Fail);
                    System.out.println("用户名:"+ user.getUserId()+ " 密码:"+user.getUserPwd()+"验证失败");
                    obj.writeObject(message);
                    socket.close();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //退出循环，说明不在监听，关闭
            serverSocket.close();
        }

    }
}
