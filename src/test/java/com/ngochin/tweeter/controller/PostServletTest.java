/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.controller;

import com.ngochin.tweeter.model.Notification;
import com.ngochin.tweeter.model.Post;
import com.ngochin.tweeter.model.User;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

/**
 *
 * @author chin
 */
@RunWith(MockitoJUnitRunner.class)
public class PostServletTest extends GenericServletTest {
    
    @Mock PostServlet ps;
    
    public PostServletTest() {
    }
    
    @Before
    @Override
    public void setup() throws Exception {
        when(ps.getServletContext()).thenReturn(ctx);
        doCallRealMethod().when(ps).doGet(req, res);
        doCallRealMethod().when(ps).doPost(req, res);
        super.setup();
    }

    @Test
    public void testGetSinglePost() throws ServletException, IOException {
        Post p = new Post();
        
        when(req.getPathInfo()).thenReturn("/99");
        when(postDao.getPost(99)).thenReturn(p);
        
        ps.doGet(req, res);
        
        verify(req).setAttribute("post", p);
        verify(rd).forward(req, res);
        verify(res, never()).sendRedirect(anyString());
    }
    
    @Test
    public void testGetSinglePostNoPost() throws ServletException, IOException {
        when(req.getPathInfo()).thenReturn("/");
        
        ps.doGet(req, res);
        
        // Redirect to home
        verify(res).sendRedirect(ctxPath);
    }

    @Test
    public void testGetSinglePostNonExistent() throws IOException, ServletException {
        ps.init();

        when(req.getPathInfo()).thenReturn("/99");
        when(postDao.getPost(99)).thenReturn(null);

        ps.doGet(req, res);

        // Redirect to home
        verify(res).sendRedirect(ctxPath);
    }
    
    @Test
    public void testCreatePost() throws ServletException, IOException {
        when(req.getParameter("postContent")).thenReturn("haha");

        ps.doPost(req, res);
        
        verify(postDao).addPost(any(Post.class));
    }
    
    @Test
    public void testCreatePostNoContent() throws ServletException, IOException {
        ps.doPost(req, res);
        
        verify(postDao, never()).addPost(any(Post.class));
    }
    
    @Test
    public void testCreatePostEmptyContent() throws ServletException, IOException {
        when(req.getParameter("postContent")).thenReturn("");
        ps.doPost(req, res);
        
        verify(postDao, never()).addPost(any(Post.class));
    }
    
    @Test
    public void testCreateNotificationOnTag() throws Exception {
        final ArrayList<Notification> n = new ArrayList<>();

        Post addedPost = new Post();
        addedPost.setId(9);

        User mike = new User();
        mike.setUserId("mike");
        
        User trung = new User();
        trung.setUserId("trung");
        
        User alice = new User();
        alice.setUserId("alice");

        when(postDao.addPost(any(Post.class))).thenReturn(addedPost);
        when(userDao.getUser("mike")).thenReturn(mike);
        when(userDao.getUser("trung")).thenReturn(trung);
        when(userDao.getUser("alice")).thenReturn(alice);
        when(req.getParameter("postContent")).thenReturn("hey @mike and @trung");
        when(req.getRemoteUser()).thenReturn("alice");
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                // Save the notification
                n.add((Notification) invocation.getArguments()[0]);
                return null;
            }
        }).when(notiDao).addNotification(any(Notification.class));
        
        ps.doPost(req, res);
        
        verify(notiDao, times(2)).addNotification(any(Notification.class));
        Assert.assertEquals(2, n.size());

        Assert.assertEquals("mike", n.get(0).getUsername());
        Assert.assertEquals("/post/9", n.get(0).getLink());
        
        Assert.assertEquals("trung", n.get(1).getUsername());
        Assert.assertEquals("/post/9", n.get(1).getLink());
    }
    
    @Test
    public void testDontCreateNotiIfSameUser() throws Exception {
        User alice = new User();
        alice.setUserId("alice");

        when(req.getParameter("postContent")).thenReturn("hey @alice");
        when(req.getRemoteUser()).thenReturn("alice");
        when(userDao.getUser("alice")).thenReturn(alice);
        when(postDao.addPost(any(Post.class))).thenReturn(new Post());
        
        ps.doPost(req, res);
        
        verify(notiDao, never()).addNotification(any(Notification.class));
    }
}
