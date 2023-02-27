package com.zyx.Server;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author 张宇森
 * @version 1.0
 * 用于存放服务端连接客户端的线程
 */
@SuppressWarnings("all")
public class Se_ClTreadList {

    private static HashMap<String, Se_ClTread> hm = new HashMap();

    public static HashMap<String, Se_ClTread> getHm() {
        return hm;
    }

    //添加线程对象到集合
    public static void addTread(String userId, Se_ClTread se_clTread) {
        hm.put(userId, se_clTread);
    }

    public static Se_ClTread getTread(String userId) {
        return hm.get(userId);
    }

    //删除线程对象从集合
    public static void remove(String userId){
        hm.remove(userId);

    }


    //返回在线用户列表
    public static String getOline() {

        //先遍历
        Iterator<String> iterator = hm.keySet().iterator();
        String users_list = "";
        while (iterator.hasNext()) {

            users_list += iterator.next().toString() + " ";
        }
        return users_list;
    }

}
