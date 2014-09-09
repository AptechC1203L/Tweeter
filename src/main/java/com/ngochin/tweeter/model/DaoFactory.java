/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.model;

/**
 *
 * @author chin
 */
public class DaoFactory {
    private final String connectionString;
    
    public DaoFactory(String connectionString) {
        this.connectionString = connectionString;
    }

    public CommentDao getCommentDao() {
        return new CommentDao(connectionString, this);
    }
    
    public PostDao getPostDao() {
        return new PostDao(connectionString, this);
    }
    
    public UserDao getUserDao() {
        return new UserDao(connectionString, this);
    }
}
