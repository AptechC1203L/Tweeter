/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.controller;

import com.ngochin.tweeter.model.DaoFactory;
import com.ngochin.tweeter.model.Notification;
import com.ngochin.tweeter.model.NotificationDao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class NotificationServletTest {
    
    @Mock NotificationServlet ns;
    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock RequestDispatcher rd;
    @Mock ServletContext ctx;
    @Mock DaoFactory f;
    @Mock NotificationDao notiDao;
    String ctxPath = "/Tweeter";

    public NotificationServletTest() {
    }
    
    @Before
    public void setup() throws Exception {
        doCallRealMethod().when(ns).processRequest(request, response);
        when(ns.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute("daoFactory")).thenReturn(f);
        when(ctx.getContextPath()).thenReturn(ctxPath);
        when(f.getNotificationDao()).thenReturn(notiDao);
    }

    /**
     * Test of processRequest method, of class NotificationServlet.
     */
    @Test
    public void testShowAllNotifications() throws Exception {
        when(request.getPathInfo()).thenReturn("/");
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);
        
        ns.processRequest(request, response);

        // Should forward to /all-notifications.jsp
        verify(request).getRequestDispatcher("/all-notifications.jsp");
        verify(rd).forward(request, response);
    }
    
    @Test
    public void testShowSingleNotification() throws Exception {
        String link = "/post/1";
        
        Notification n = new Notification();
        n.setLink(link);

        when(request.getPathInfo()).thenReturn("/1");
        when(notiDao.getNotification(1)).thenReturn(n);

        ns.processRequest(request, response);

        // Should redirect to the link in the notification
        verify(response).sendRedirect(ctxPath + link);
        
        // And mark it as read
        verify(notiDao).saveNotification(n);
        assertEquals(true, n.isIsRead());
    }
}
