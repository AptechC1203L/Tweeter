/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngochin.tweeter.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author chin
 */
public class Post {

    private String text = "";
    private String username = "";
    private Date timestamp = new Date();
    private int id = 0;
    private CommentDao commentDao;
    private UserDao userDao;

    public Post() {
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

    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<Comment> getComments() {
        return commentDao.getCommentsOnPost(id);
    }

    public User getPoster() {
        return userDao.getUser(username);
    }

    List<Tag> getTags() {
        ArrayList<Tag> tags = new ArrayList<>();
        int start = -1;
        int end = -1;
        String puntuations = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

        for (int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);

            if (c == '@') {
                start = i;
            } else if (start != -1
                    && (c == ' '
                    || puntuations.indexOf(c) != -1
                    || i == text.length() - 1)) {
                end = i + (i == text.length() - 1 ? 1 : 0);

                Tag t = new Tag();
                t.setText(text.substring(start, end));
                t.setStart(start);
                t.setLen(end - start);
                tags.add(t);

                start = -1;
                end = -1;
            }
        }

        return tags;
    }
    
    /**
     * getContentFragments break the content text into adjacent fragments of
     * different types (string, user, hashtag, etc.). It is used to help with
     * rendering post content that contains tags.
     * @return 
     */
    public List<Object> getContentFragments() {
        List<Tag> tags = getTags();
        ArrayList<Object> fragments = new ArrayList<>();
        int i = 0;
        int k = 0;
        
        while (i < text.length() && k < tags.size()) {
            Tag t = tags.get(k);
            String textFragment = text.substring(i, t.getStart());
            fragments.add(textFragment);

            User user = userDao.getUser(t.getText().substring(1));
            if (user != null) {
                fragments.add(user);
            } else {
                fragments.add(text.substring(t.getStart(), t.getEnd()));
            }

            i = t.getEnd();
            k++;
        }
        
        fragments.add(text.substring(i));

        return fragments;
    }

    class Tag {

        private String text;
        private int start;
        private int len;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getLen() {
            return len;
        }

        public void setLen(int len) {
            this.len = len;
        }
        
        public int getEnd() {
            return getStart() + getLen();
        }
    }

}
