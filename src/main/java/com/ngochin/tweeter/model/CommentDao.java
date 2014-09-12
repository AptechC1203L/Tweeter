/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author chin
 */
public class CommentDao {
    private final DataSource ds;
    private final DaoFactory factory;

    public CommentDao(DataSource ds, DaoFactory factory) {
        this.ds = ds;
        this.factory = factory;
    }

    public boolean addComment(Comment c) {
        try (Connection conn = ds.getConnection()) {
            Statement st = conn.createStatement();
            int count = st.executeUpdate(
                    String.format("insert into comments (user_name, post_id, creation_time, text) "
                            + "values (\"%s\", \"%d\", datetime('now', 'localtime'), \"%s\")",
                            c.getUserId(), c.getPostId(), c.getText()));
            return count == 1;
        } catch (SQLException ex) {
            Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Comment> getCommentsOnPost(int postId) {
        ArrayList<Comment> commentsOnPost = new ArrayList<>();
        
        try (Connection conn = ds.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from comments where post_id=" + Integer.toString(postId));

            while (rs.next()) {
                commentsOnPost.add(commentFromRs(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return commentsOnPost;
    }

    private Comment commentFromRs(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String userName = rs.getString("user_name");
        int postId = rs.getInt("post_id");
        Date creationTime = rs.getDate("creation_time");
        String text = rs.getString("text");
        
        Comment c = new Comment(); 
        c.setId(id);
        c.setPostId(postId);
        c.setText(text);
        c.setTimestamp(creationTime);
        c.setUserId(userName);
        c.setUserDao(factory.getUserDao());
        c.setPostDao(factory.getPostDao());
        
        return c;
    }
}
