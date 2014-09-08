/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngochin.tweeter.model;

import java.util.ArrayList;

/**
 *
 * @author chin
 */
public class UserDao {
    private static ArrayList<User> users;

    public UserDao() {
        if (users == null) {
            users = new ArrayList<User>();
            users.add(new User("chin", "Trung Ngo"));
        }
    }

    public User getUser(String username) {
        for (User u : users) {
            if (u.getUserId().equals(username)) {
                return u;
            }
        }

        return null;
    }
}
