/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngochin.tweeter.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javax.sql.DataSource;

/**
 *
 * @author chin
 */
public class PostDao {

    private DataSource ds;
    private DaoFactory factory;

    public PostDao(DataSource ds, DaoFactory factory) {
        this.ds = ds;
        this.factory = factory;
    }

    /**
     * Add a post.
     * @param p
     * @return the added post with ID filled in.
     */
    public Post addPost(Post p) {
        try (Connection conn = ds.getConnection();
                PreparedStatement insertPostQuery = conn.prepareStatement(
                        "insert into posts (user_name, creation_time, content) "
                        + "values (?, datetime('now', 'localtime'), ?)");) {

            insertPostQuery.setString(1, p.getUsername());
            insertPostQuery.setString(2, p.getText());

            int count = insertPostQuery.executeUpdate();
            
            Statement st = conn.createStatement();
            String selectLastRow = "select * from posts where rowid=last_insert_rowid()";
            if (count == 1) {
                ResultSet rs = st.executeQuery(selectLastRow);
                rs.next();
                return postFromRs(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
        try (Connection conn = ds.getConnection();
                PreparedStatement postQuery = conn.prepareStatement(
                        "select * from posts where id=?");) {
            postQuery.setInt(1, postId);
            ResultSet rs = postQuery.executeQuery();
            
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

        try (Connection conn = ds.getConnection()) {
            PreparedStatement postQuery = conn.prepareStatement("select * from posts where user_name=?");
            postQuery.setString(1, username);
            ResultSet rs = postQuery.executeQuery();

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

        try (Connection conn = ds.getConnection()) {
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
