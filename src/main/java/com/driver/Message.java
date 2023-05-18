package com.driver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private int id;
    private String content;
    private Date timestamp;

    public Message() {
    }

    public Message(int id, String content) {
        this.id = id;
        this.content = content;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy.HH:mm:ss");
        String time = df.format(new Date());
        this.timestamp = new Date(time);
    }

//    public Message(int id, String content, Date timestamp) {
//        this.id = id;
//        this.content = content;
//        this.timestamp = timestamp;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
