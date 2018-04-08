package com.example.kenneth.hiit;

import java.util.ArrayList;

public class Party {

    public String  RoomName;
    public String HostUname;
    public  ArrayList MemberList;
    public String Url;
    public Party(String roomName, String hostUname, String url) {
        this.MemberList = new ArrayList<>();
        this.RoomName = roomName;
        this.HostUname = hostUname;
        this.Url = url;
    }





}
