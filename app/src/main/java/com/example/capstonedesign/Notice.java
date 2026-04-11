package com.example.capstonedesign;

public class Notice {
    private String category;
    private String title;
    private String date;
    private String noticeUrl;
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
