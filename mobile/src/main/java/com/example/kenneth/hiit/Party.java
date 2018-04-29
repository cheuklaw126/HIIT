package com.example.kenneth.hiit;

import java.util.ArrayList;

public class Party {

    public String  RoomName;
    public String HostUname;
    public ArrayList<PartyUser> MemberList;
    public String Url;
    public Party(String roomName, String hostUname, String url) {
        this.MemberList = new ArrayList<PartyUser>();
        this.MemberList.add(new PartyUser(hostUname));
        this.RoomName = roomName;
        this.HostUname = hostUname;
        this.Url = url;
    }

    public void AddMember(String uname){
        this.MemberList.add(new PartyUser(uname));
    }

public class PartyUser{
        public String uname;
        public Boolean isReady;

    public PartyUser(String uname, Boolean isReady) {
        this.uname = uname;
        this.isReady = isReady;
    }
    public PartyUser(String uname) {
        this(uname,false);
    }


}



        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }
        @Override
        public String toString(){
            return Url;
        }




}
