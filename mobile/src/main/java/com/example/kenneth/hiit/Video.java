package com.example.kenneth.hiit;

public class Video {
    private String videolink, videodesc;

    public Video(String videolink, String videodesc){

        setVideoLink(videolink);
        setVideoDesc(videodesc);
    }

    public String getVideolink(){return videolink;}

    public void setVideoLink(String videolink) {this.videolink = videolink;}

    public String getVideodesc(){return videodesc;}

    public void setVideoDesc(String videodesc) {this.videodesc = videodesc;}
}
