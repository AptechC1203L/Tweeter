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
public class NotificationDao {
    private DataSource ds;
    private DaoFactory factory;

    public NotificationDao(DataSource ds, DaoFactory factory) {
        this.ds = ds;
        this.factory = factory;
    }

    public boolean addNotification(Notification n) {
        try (Connection conn = ds.getConnection()) {
            Statement st = conn.createStatement();
            int count = st.executeUpdate(
                    String.format("insert into notifications (user_name, message, creation_time, link, isRead) "
                            + "values (\"%s\", \"%s\", datetime(\"now\"), \"%s\", 0)",
                            n.getUsername(), n.getMessage(), n.getLink()));
            
            return count == 1;
        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    private Notification notificationFromRs(ResultSet rs) throws SQLException {
        Notification n = new Notification();

        int id = rs.getInt("id");
        String userName = rs.getString("user_name");
        String creationTime = rs.getString("creation_time");
        String message = rs.getString("message");
        String link = rs.getString("link");
        boolean isRead = rs.getBoolean("isRead");

        n.setId(id);
        n.setUsername(userName);
        n.setMessage(message);
        n.setLink(link);
        n.setIsRead(isRead);
        try {
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            n.setCreationTime(fmt.parse(creationTime));
        } catch (ParseException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
            n.setCreationTime(new Date());
        }

        return n;
    }

    public Notification getNotification(int id) {
        try (Connection conn = ds.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from notifications where id=" + Integer.toString(id));
            
            while (rs.next()) {
                return notificationFromRs(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public List<Notification> getNotificationsFromUser(String username) {
        ArrayList<Notification> notifications = new ArrayList<>();

        try (Connection conn = ds.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = 
                st.executeQuery("select * from notifications where isRead=0 and user_name=\"" + username + "\"");

            while (rs.next()) {
                notifications.add(notificationFromRs(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return notifications;
    }
    
    public boolean saveNotification(Notification n) {
        // Actually we only update the isRead field.
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("update notifications set isRead=? where id=?");
            ps.setBoolean(1, n.isIsRead());
            ps.setInt(2, n.getId());
            int count = ps.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
