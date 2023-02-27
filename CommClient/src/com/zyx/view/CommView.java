package com.zyx.view;

import com.zyx.Serve.Fileinput;
import com.zyx.Serve.Mssage_commu;
import com.zyx.Serve.isUser_zyx;

import java.util.Scanner;

/**
 * @author 张宇森
 * @version 1.0
 * 登陆界面
 */
public class CommView {

    //是否显示菜单
    private boolean op=true;
    //用于登陆服务器
    private isUser_zyx us=new isUser_zyx();
    //消息聊天
    private Mssage_commu ms=new Mssage_commu();
    //文件发送
    private Fileinput ft =new Fileinput();



    public static void main(String[] args) throws Exception {
        CommView commView = new CommView();
         commView.mainMenu();

    }

    //显示主菜单
    private void mainMenu() throws Exception{
        Scanner scanner = new Scanner(System.in);
        while(op){

            System.out.println("=========欢迎来到聊天通讯系统==========");
            System.out.println("\t\t 1 登陆系统");
            System.out.println("\t\t 0 退出系统");
            System.out.print("\t\t 请输入你的选择: ");
            int t = scanner.nextInt();
            //根据用户输入
            switch (t){
                case 1:
                    System.out.print("请输入用户号: ");
                    String uid= scanner.next();
                    System.out.print("请输入密 码: ");
                    String upd = scanner.next();
                    if(us.UserCheck(uid,upd)){
                        //进入二级菜单
                        System.out.println("=========欢迎(用户)"+uid+"登录==========");
                        while(op){
                            System.out.println("\n=========欢迎(用户)"+uid+"来到聊天通讯系统==========");
                            System.out.println("\t\t 1 显示在线活跃用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 0 退出系统");
                            System.out.print("\t\t 请输入你的选择: ");
                            int k = scanner.nextInt();
                            switch (k){
                                case 1:
                                    us.onlineList();
                                    break;
                                case 2:
                                    System.out.print("请输入想对大家说的话:");
                                    String QL = scanner.next();
                                    //将消息发送给服务端
                                    ms.sendEverone(uid,QL);
                                    break;
                                case 3:
                                    System.out.print("请输入想聊天的在线用户名: ");
                                    String UWant = scanner.next();
                                    System.out.print("请输入你想说的话: ");
                                    String contents = scanner.next();
                                    //将消息发送给服务端
                                    ms.sendToOther(uid,UWant,contents);
                                    break;
                                case 4:
                                    System.out.print("请输入你想把文件发送给的在线用户: ");
                                    String yh = scanner.next();
                                    System.out.print("请输入发送文件的完整路径: ");
                                    String ownPath = scanner.next();
                                    System.out.print("请输入对方接收文件的完整路径: ");
                                    String otherPath = scanner.next();
                                    ft.sendFile(ownPath,otherPath,uid,yh);
                                    break;
                                case 0:
                                    System.out.println("退出系统");
                                    //给服务器发送退出系统的消息
                                    us.ExitTread();
                                    op=false;
                                    break;
                                default:
                                    System.out.println("输入错误请重试");
                                    break;
                            }
                        }
                    }else{
                        System.out.println("用户"+uid+"登录失败");
                    }
                    break;
                case 0:
                    System.out.println("退出系统");
                    op=false;
                    break;
            }

        }
    }
}
