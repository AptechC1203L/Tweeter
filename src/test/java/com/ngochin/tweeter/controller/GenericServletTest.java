/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.controller;

import com.ngochin.tweeter.model.CommentDao;
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
import org.junit.Before;
import org.junit.Ignore;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 *
 * @author chin
 */
@Ignore
public class GenericServletTest {
    protected @Mock HttpServletRequest req;
    protected @Mock HttpServletResponse res;
    protected @Mock ServletContext ctx;
    protected @Mock HttpSession session;
    protected @Mock RequestDispatcher rd;

    protected @Mock DaoFactory f;
    protected @Mock PostDao postDao;
    protected @Mock NotificationDao notiDao;
    protected @Mock UserDao userDao;
    protected @Mock CommentDao commentDao;

    protected @Mock User authUser;
    protected final String ctxPath = "/Tweeter";
    
    @Before
    public void setup() throws Exception {
        when(authUser.getUserId()).thenReturn("trung");
        when(authUser.getFullName()).thenReturn("Trung Ngo");
        
        when(ctx.getAttribute("daoFactory")).thenReturn(f);
        when(session.getAttribute("authUser")).thenReturn(authUser);

        when(f.getPostDao()).thenReturn(postDao);
        when(f.getUserDao()).thenReturn(userDao);
        when(f.getNotificationDao()).thenReturn(notiDao);
        when(f.getCommentDao()).thenReturn(commentDao);

        when(req.getRequestDispatcher(anyString())).thenReturn(rd);
        when(ctx.getContextPath()).thenReturn(ctxPath);
        when(req.getSession()).thenReturn(session);
    }
}
