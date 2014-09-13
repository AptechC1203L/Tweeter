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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author chin
 */
public class UserDao {

    private final DataSource ds;
    private final DaoFactory factory;

    public UserDao(DataSource ds, DaoFactory factory) {
        this.ds = ds;
        this.factory = factory;
    }

    /**
     * Add a user.
     *
     * @param u
     * @return Whether the user was added.
     */
    public boolean addUser(User u) {
        try (Connection conn = ds.getConnection();
                PreparedStatement insertUserQuery = conn.prepareStatement(
                        "insert into users (user_name, full_name, password)"
                        + "values (?, ?, ?)");
                PreparedStatement insertRoleQuery = conn.prepareStatement(
                        "insert into user_roles (user_name, role_name) "
                        + "values (?, ?)");) {
            
            insertUserQuery.setString(1, u.getUserId());
            insertUserQuery.setString(2, u.getFullName());
            insertUserQuery.setString(3, u.getPassword());

            // FIXME: Need safe state if arguments are invalid (empty username?)
            int count = insertUserQuery.executeUpdate();

            for (String role : u.getRoles()) {
                insertRoleQuery.setString(1, u.getUserId());
                insertRoleQuery.setString(2, role);
                
                // FIXME: Rollback if cannot insert role.
                insertRoleQuery.executeUpdate();
            }

            return count == 1;
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public User getUser(String username) {
        try (Connection conn = ds.getConnection();
                PreparedStatement userQuery = 
                        conn.prepareStatement("select * from users where user_name=?");
                PreparedStatement roleQuery = 
                        conn.prepareStatement("select role_name from user_roles where user_name=?");) {

            userQuery.setString(1, username);
            ResultSet rs = userQuery.executeQuery();

            roleQuery.setString(1, username);
            while (rs.next()) {
                User u = userFromRs(rs);

                ResultSet roles = roleQuery.executeQuery();

                while (roles.next()) {
                    u.addRole(roles.getString("role_name"));
                }

                return u;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (Connection conn = ds.getConnection();
                PreparedStatement roleQuery = conn.prepareStatement(
                        "select (role_name) from user_roles where user_name=?")) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from users");

            while (rs.next()) {
                User u = userFromRs(rs);

                roleQuery.setString(1, u.getUserId());
                ResultSet roles = roleQuery.executeQuery();

                while (roles.next()) {
                    u.addRole(roles.getString("role_name"));
                }

                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
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
        u.setPostDao(factory.getPostDao());
        u.setNotificationDao(factory.getNotificationDao());

        return u;
    }
}
