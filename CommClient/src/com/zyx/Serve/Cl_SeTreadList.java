package com.zyx.Serve;

import java.util.HashMap;

/**
 * @author 张宇森
 * @version 1.0
 * 用于存放客户端连接服务端的线程
 */
@SuppressWarnings("all")
public class Cl_SeTreadList {

    //把多个线程放入到集合中
    private static HashMap<String,Cl_SeTread> hm=new HashMap();

    //将线程加入集合中
    public static void addTread(String userId,Cl_SeTread cst){
        hm.put(userId,cst);
    }
    //取出线程
    public static Cl_SeTread getTread(String userId){
        return hm.get(userId);
    }

}
