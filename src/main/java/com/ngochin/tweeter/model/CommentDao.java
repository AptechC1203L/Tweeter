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
public class CommentDao {
    private static ArrayList<Comment> comments;

    public CommentDao() {
        if (comments == null) {
            comments = new ArrayList<Comment>();
            comments.add(new Comment("Hi", "chin", 0, new Date()));
        }
    }

    public CommentDao addComment(Comment c) {
        comments.add(c);
        return this;
    }

    public List<Comment> getCommentsOnPost(int postId) {
        ArrayList<Comment> commentsOnPost = new ArrayList<Comment>();
        
        for (Comment c : comments) {
            if (c.getPostId() == postId) {
                commentsOnPost.add(c);
            }
        }
        
        return commentsOnPost;
    }
}
