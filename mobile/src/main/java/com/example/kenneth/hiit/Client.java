package com.example.kenneth.hiit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author qc
 */
public class Client implements Runnable {
    public PrintWriter out;
    public BufferedReader in;
    public Socket sc;
    public String ip;
    public int port = 3456;
    public String UserName;

    public Client(String UserName) {
        this.UserName = UserName;
        StartClient();
    }


    public void GetIP() {
        try {
            InetAddress add = InetAddress.getByName("cheuklaw126.mynetgear.com");
            this.ip = add.getHostAddress();
     //  this.ip = "192.168.1.22";
        } catch (Exception ex) {
            this.ip = "";
        }
    }

    public void StartClient() {
        try {

            Thread getIpThread = new Thread() {
                @Override
                public void run() {
                    GetIP();
                }
            };

            getIpThread.start();
            getIpThread.join();

            Thread ConnectThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sc = new Socket(ip, port);
                        System.out.println("done");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            ConnectThread.start();

            ConnectThread.join();
            int timeout = 5000;
            System.out.println("連線中....");
            out = new PrintWriter(sc.getOutputStream(), true);
            Thread LoginThread = new Thread() {
                @Override
                public void run() {
                    Send("|login|" + UserName);
                }
            };
            LoginThread.start();
            LoginThread.join();
            //  String str = "testttttt";
            // byte[] b = str.getBytes("UTF-8");
            //    out.println("我入到SERVER");
            //   System.out.println(str);
            in = new BufferedReader(new InputStreamReader(sc.getInputStream()));


        /*    while (true) {
                System.out.println("Server：" + in.readLine());
            }*/
        } catch (SocketTimeoutException e) {
            System.out.println("Timeout");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Send(String msg) {
        final String msgr = msg;
    Thread msgThread=    new Thread(){
            @Override
            public void run(){
                out.println(msgr);
            }
        };
    msgThread.start();
        try {
            msgThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public String Listener() {
        try {
            return in.readLine();
        } catch (Exception ex) {
            return "";
        }
    }


    @Override
    public void run() {
        Listener();
    }




}
