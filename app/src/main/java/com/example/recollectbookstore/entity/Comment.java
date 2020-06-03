package com.example.recollectbookstore.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Comment {

    private Long id;
    private String commentText;
    private String timeStamp;


    public Comment(Long id, String commentText, String timeStamp){
        this.id = id;
        this.commentText = commentText;
        this.timeStamp = timeStamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", commentText='" + commentText + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
