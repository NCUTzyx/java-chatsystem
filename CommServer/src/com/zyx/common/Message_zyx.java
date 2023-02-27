package com.zyx.common;

import java.io.Serializable;

/**
 * @author 张宇森
 * @version 1.0
 * 通讯时消息对象
 */
@SuppressWarnings({"all"})
//需要序列化
public class Message_zyx implements Serializable {

    private static final long serialVersionUID = 1l;  //兼容性
    private String sender;  //发送者
    private String recevier; //接收者
    private String contents; //消息内容
    private String time; //发送时间
    private String messageType; //消息类型 -》接口中定义

    //和文件相关的成员
    private byte[] fileBytes;
    private int lens=0;
    private String desertPath;  //目的位置
    private String filePath;   //文件原位置

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getLens() {
        return lens;
    }

    public void setLens(int lens) {
        this.lens = lens;
    }

    public String getDesertPath() {
        return desertPath;
    }

    public void setDesertPath(String desertPath) {
        this.desertPath = desertPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecevier() {
        return recevier;
    }

    public void setRecevier(String recevier) {
        this.recevier = recevier;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}