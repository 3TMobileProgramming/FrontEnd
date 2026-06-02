package com.example.capstonedesign.models;

public class ChatRequest {
    private String question;

    public ChatRequest(String question) {
        this.question = question;
    }

    // Getter, Setter
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
}