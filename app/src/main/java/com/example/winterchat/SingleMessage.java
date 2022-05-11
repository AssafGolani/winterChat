package com.example.winterchat;

import java.time.LocalDateTime;

public class SingleMessage {
    private String userName;
    private String content;
    private LocalDateTime timeStamp;

    public SingleMessage(String userName, String content) {
        this.userName = userName;
        this.content = content;
        this.timeStamp = LocalDateTime.now();
    }

    // this default constructor is used by firebase for persistence
    public SingleMessage() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

}
