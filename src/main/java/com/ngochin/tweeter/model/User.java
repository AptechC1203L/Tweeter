/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chin
 */
public class User {
    private String userId;
    private String fullName;
    private String password;
    private List<String> roles;
    
    public User() {
        this.roles = new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
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
}
