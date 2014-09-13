/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.controller;

import com.ngochin.tweeter.model.Notification;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author chin
 */
@RunWith(MockitoJUnitRunner.class)
public class NotificationServletTest extends GenericServletTest {
    
    @Mock NotificationServlet ns;

    public NotificationServletTest() {
    }
    
    @Before
    @Override
    public void setup() throws Exception {
        when(ns.getServletContext()).thenReturn(ctx);
        doCallRealMethod().when(ns).processRequest(req, res);
        super.setup();
    }

    /**
     * Test of processRequest method, of class NotificationServlet.
     */
    @Test
    public void testShowAllNotifications() throws Exception {
        when(req.getPathInfo()).thenReturn("/");
        when(req.getRequestDispatcher(anyString())).thenReturn(rd);
        
        ns.processRequest(req, res);

        // Should forward to /all-notifications.jsp
        verify(req).getRequestDispatcher("/all-notifications.jsp");
        verify(rd).forward(req, res);
    }
    
    @Test
    public void testShowSingleNotification() throws Exception {
        String link = "/post/1";
        
        Notification n = new Notification();
        n.setLink(link);

        when(req.getPathInfo()).thenReturn("/1");
        when(notiDao.getNotification(1)).thenReturn(n);

        ns.processRequest(req, res);

        // Should redirect to the link in the notification
        verify(res).sendRedirect(ctxPath + link);
        
        // And mark it as read
        verify(notiDao).saveNotification(n);
        assertEquals(true, n.isIsRead());
    }
}
