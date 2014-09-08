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
public class PostDao {

    private static ArrayList<Post> posts;

    public PostDao() {
        if (posts == null) {
            posts = new ArrayList<Post>();
            addPost(new Post("Post 1", "chin", new Date()));
            addPost(new Post("Post 2", "chin", new Date()));
        }
    }

    public PostDao addPost(Post p) {
        p.setId(posts.size());
        posts.add(p);
        return this;
    }

    public Post getPost(int postId) {
        for (Post p : posts) {
            if (p.getId() == postId) {
                return p;
            }
        }

        return null;
    }

    public List<Post> getPostsFromUser(String username) {
        ArrayList<Post> postsByUser = new ArrayList<Post>();

        for (Post p : posts) {
            if (p.getUsername().equals(username)) {
                postsByUser.add(p);
            }
        }

        return postsByUser;
    }

    public List<Post> getAllPosts() {
        return posts;
    }
}
