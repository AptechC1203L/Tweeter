/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 *
 * @author chin
 */
public class User {
    private String userId;
    private String fullName;
    private String password;
    private List<String> roles;
    private PostDao postDao;
    private NotificationDao notiDao;
    public static int MAX_USERNAME_LEN = 25;
    
    public User() {
        this.roles = new ArrayList<>();
    }

    public String getFullName() {
        return fullName != null ? fullName : userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addRole(String role) {
        this.roles.add(role);
    }
    
    public boolean hasRole(String role) {
        for (String r : roles) {
            if (r.equals(role)) {
                return true;
            }
        }
        
        return false;
    }
    
    public List<String> getRoles() {
        return this.roles;
    }

    public void setPostDao(PostDao postDao) {
        this.postDao = postDao;
    }

    public void setNotificationDao(NotificationDao notiDao) {
        this.notiDao = notiDao;
    }

    public List<Post> getPosts() {
        return postDao.getPostsFromUser(userId);
    }
    
    public List<Notification> getNotifications() {
        return notiDao.getNotificationsFromUser(userId);
    }

    public static boolean isValidUsername(String username) {
        return username.length() <= MAX_USERNAME_LEN 
                && Pattern.matches("^[A-Za-z0-9_.]+$", username);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        return true;
    }
}
