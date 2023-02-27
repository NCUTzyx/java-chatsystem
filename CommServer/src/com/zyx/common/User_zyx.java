package com.zyx.common;

import java.io.Serializable;

/**
 * @author 张宇森
 * @version 1.0
 * 用户/客户信息
 */
@SuppressWarnings({"all"})
//需要序列化
public class User_zyx implements Serializable {

    private static final long serialVersionUID =1l;  //兼容性
    private String userId; //用户id
    private String userPwd; //用户密码

    public User_zyx() {
    }

    public User_zyx(String userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

}
