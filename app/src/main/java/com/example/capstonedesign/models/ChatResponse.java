package com.example.capstonedesign.models;

import java.util.List;

public class ChatResponse {
    private String question;
    private String answer;
    private List<Notice> notices; // NoticeSummaryDto

    // Getter
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public List<Notice> getNotices() { return notices; }
}