package com.infinity.aroundme.domain;


import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;


public class Message {
    private String messageText;
    private String sender;
    private String receiver;
    @ServerTimestamp
    private Date date;

    public Message() {
    }

    public Message(String messageText, String sender, String receiver) {
        this.messageText = messageText;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Message(String messageText, String sender, String receiver, Date date) {
        this.messageText = messageText;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
