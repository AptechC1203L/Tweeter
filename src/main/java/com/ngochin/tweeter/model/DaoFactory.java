/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author chin
 */
public class DaoFactory {
    // FIXME The @Resource annotation doesn't work here.
    @Resource(name = "jdbc/TweeterDB")
    private static DataSource ds;

    public DaoFactory() {
        try {
            if (ds == null) {
                Context envCtx = (Context) InitialContext.doLookup("java:comp/env");
                ds = (DataSource) envCtx.lookup("jdbc/TweeterDB");
            }
        } catch (NamingException ex) {
            Logger.getLogger(DaoFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CommentDao getCommentDao() {
        return new CommentDao(ds, this);
    }

    public PostDao getPostDao() {
        return new PostDao(ds, this);
    }

    public UserDao getUserDao() {
        return new UserDao(ds, this);
    }
}
