/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author chin
 */
public class Post {
    private String text = "";
    private String username = "";
    private Date timestamp = null;
    private int id = 0;
    
    public Post() {
        
    }

    public Post(String text, String username, Date timestamp) {
        this.text = text;
        this.username = username;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public List<Comment> getComments() {
        return new CommentDao().getCommentsOnPost(id);
    }
}
