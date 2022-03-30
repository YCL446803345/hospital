package com.woniu.entity;

import java.io.Serializable;

public class ToEmail implements Serializable {

    private String[] tos;
    private String subject;
    private String content;

    public String[] getTos ( ) {
        return tos;
    }

    public void setTos (String[] tos) {
        this.tos = tos;
    }

    public String getSubject ( ) {
        return subject;
    }

    public void setSubject (String subject) {
        this.subject = subject;
    }

    public String getContent ( ) {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }
}
