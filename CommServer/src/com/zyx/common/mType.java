package com.zyx.common;

/**
 * @author 张宇森
 * @version 1.0
 * 消息类型
 */
public interface mType {

    String Sign_Success = "1";//登陆成功
    String Sign_Fail = "0";//登陆失败
    String Common_Message = "2"; //信息包
    String Online_Get_User ="3"; //要求返回在线用户列表
    String Online_Run_User ="4"; //返回在线用户列表
    String Comm_Exit = "5"; //退出
    String Ever_Message = "6"; //群发信息包
    String File_Message = "7";  //文件消息

}
