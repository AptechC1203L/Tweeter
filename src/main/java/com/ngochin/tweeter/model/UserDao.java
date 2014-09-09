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
import java.util.List;
/**
 *
 * @author chin
 */
public class UserDao {
    private final String connectionString;

    public UserDao(String connectionString) {
        this.connectionString = connectionString;
    }
    
    public UserDao addUser(User u) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            Statement st = conn.createStatement();

            st.executeUpdate(
                    String.format("insert into users (user_name, full_name, password) "
                            + "values (\"%s\", \"%s\", \"%s\")",
                            u.getUserId(), u.getFullName(), u.getPassword()));
        }
        
        return this;
    }

    public User getUser(String username) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from users where user_name=" + username);

            while (rs.next()) {
                return userFromRs(rs);
            }
        }

        return null;
    }
    
    public List<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionString)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from users");

            while (rs.next()) {
                users.add(userFromRs(rs));
            }
        }
        
        return users;
    }

    private User userFromRs(ResultSet rs) throws SQLException {
        String userName = rs.getString("user_name");
        String fullName = rs.getString("full_name");
        String password = rs.getString("password");

        User u = new User();
        u.setUserId(userName);
        u.setFullName(fullName);
        u.setPassword(password);
        
        return u;
    }
}
