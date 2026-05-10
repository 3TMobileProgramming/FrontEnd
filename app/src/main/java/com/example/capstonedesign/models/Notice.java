package com.example.capstonedesign.models;

import com.google.gson.annotations.SerializedName;

public class Notice {
    private Long id;
    private String category;
    private String title;
    private String date;

    @SerializedName("url")
    private String noticeUrl;
    public Notice() { }
    public Notice(String category, String title, String date, String noticeUrl){
        this.category = category;
        this.title = title;
        this.date = date;
        this.noticeUrl = noticeUrl;
    }

    public String getCategory(){
        return category;
    }
    public String getTitle(){
        return title;
    }
    public String getDate(){
        return date;
    }
    public String getNoticeUrl(){
        return noticeUrl;
    }
}
