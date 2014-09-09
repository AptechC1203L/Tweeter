/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngochin.tweeter.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chin
 */
public class PostDao {

    private String connectionString;
    private DaoFactory factory;

    public PostDao(String connectionString, DaoFactory factory) {
        this.connectionString = connectionString;
        this.factory = factory;
    }

    public boolean addPost(Post p) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            Statement st = conn.createStatement();
            int count = st.executeUpdate(
                    String.format("insert into posts (user_name, creation_time, content) "
                            + "values (\"%s\", datetime(\"now\"), \"%s\")",
                            p.getUsername(), p.getText()));
            
            return count == 1;
        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    private Post postFromRs(ResultSet rs) throws SQLException {
        Post p = new Post();

        int id = rs.getInt("id");
        String userName = rs.getString("user_name");
        String creationTime = rs.getString("creation_time");
        String content = rs.getString("content");
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        p.setId(id);
        p.setText(content);
        try {
            // FIXME: timestamp seems wrong
            p.setTimestamp(fmt.parse(creationTime));
        } catch (ParseException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
            p.setTimestamp(new Date());
        }
        p.setUsername(userName);
        p.setCommentDao(factory.getCommentDao());
        p.setUserDao(factory.getUserDao());

        return p;
    }

    public Post getPost(int postId) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from posts where id=" + Integer.toString(postId));
            
            while (rs.next()) {
                return postFromRs(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public List<Post> getPostsFromUser(String username) {
        ArrayList<Post> posts = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionString)) {
            Statement st = conn.createStatement();
            ResultSet rs = 
                st.executeQuery("select * from posts where user_name=\"" + username + "\"");

            while (rs.next()) {
                posts.add(postFromRs(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return posts;
    }

    public List<Post> getAllPosts() {
        ArrayList<Post> posts = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionString)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from posts");

            while (rs.next()) {
                posts.add(postFromRs(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return posts;
    }
}
