/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.controller;

import com.ngochin.tweeter.model.DaoFactory;
import com.ngochin.tweeter.model.NotificationDao;
import com.ngochin.tweeter.model.PostDao;
import com.ngochin.tweeter.model.User;
import com.ngochin.tweeter.model.UserDao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
public class UserServletTest {

    @Mock UserServlet us;
    @Mock HttpServletRequest req;
    @Mock HttpServletResponse res;
    @Mock ServletContext ctx;
    @Mock DaoFactory f;
    @Mock PostDao postDao;
    @Mock NotificationDao notiDao;
    @Mock UserDao userDao;
    @Mock RequestDispatcher rd;
    @Mock HttpSession session;
    final String ctxPath = "/Tweeter";
    
    public UserServletTest() {
    }
    
    @Before
    public void setup() throws Exception {
        when(us.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute("daoFactory")).thenReturn(f);
        when(f.getPostDao()).thenReturn(postDao);
        when(f.getUserDao()).thenReturn(userDao);
        when(f.getNotificationDao()).thenReturn(notiDao);
        when(req.getRequestDispatcher(anyString())).thenReturn(rd);
        doCallRealMethod().when(us).processRequest(req, res);
        when(ctx.getContextPath()).thenReturn(ctxPath);
        when(req.getSession()).thenReturn(session);
    }

    /**
     * Test of processRequest method, of class UserServlet.
     */
    @Test
    public void testGetValidUser() throws Exception {
        User trung = new User();

        when(req.getPathInfo()).thenReturn("/trung");
        when(userDao.getUser("trung")).thenReturn(trung);
        
        us.processRequest(req, res);
        
        // Show them out
        verify(req).setAttribute("user", trung);
        verify(req).getRequestDispatcher("/user.jsp");
        verify(rd).forward(req, res);
    }
    
    @Test
    public void testGetInvalidUser() throws Exception {
        when(req.getPathInfo()).thenReturn("/trung");
        when(userDao.getUser("trung")).thenReturn(null);

        us.processRequest(req, res);

        // throw 404
        verify(res).sendError(HttpServletResponse.SC_NOT_FOUND);
    }
    
    @Test
    public void testGetNoUser() throws Exception {
        User authUser = new User();
        authUser.setUserId("trung");

        when(req.getPathInfo()).thenReturn("/");
        when(session.getAttribute("authUser")).thenReturn(authUser);

        us.processRequest(req, res);

        // Redirect to the current user's homepage
        verify(res).sendRedirect(ctxPath + "/user/" + authUser.getUserId());
    }
}
