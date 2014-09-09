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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author chin
 */
public class PostDao {

    private String connectionString;

    public PostDao(String connectionString) {
        this.connectionString = connectionString;
    }

    public PostDao addPost(Post p) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            Statement st = conn.createStatement();
            st.executeUpdate(
                String.format("insert into posts (user_name, creation_time, content) "
                        + "values (\"%s\", datetime(\"now\"), \"%s\")",
                        p.getUsername(), p.getText()));
        }

        return this;
    }
    
    private Post postFromRs(ResultSet rs) throws SQLException {
        Post p = new Post();

        int id = rs.getInt("id");
        String userName = rs.getString("user_name");
        Date creationTime = rs.getTime("creation_time");
        String content = rs.getString("content");

        p.setId(id);
        p.setText(content);
        p.setTimestamp(creationTime);
        p.setUsername(userName);

        return p;
    }

    public Post getPost(int postId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from posts where id=" + Integer.toString(postId));
            
            while (rs.next()) {
                return postFromRs(rs);
            }
        }

        return null;
    }

    public List<Post> getPostsFromUser(String username) throws SQLException {
        ArrayList<Post> posts = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionString)) {
            Statement st = conn.createStatement();
            ResultSet rs = 
                st.executeQuery("select * from posts where user_name=\"" + username + "\"");

            while (rs.next()) {
                posts.add(postFromRs(rs));
            }
        }

        return posts;
    }

    public List<Post> getAllPosts() throws SQLException {
        ArrayList<Post> posts = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionString)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from posts");

            while (rs.next()) {
                posts.add(postFromRs(rs));
            }
        }

        return posts;
    }
}
