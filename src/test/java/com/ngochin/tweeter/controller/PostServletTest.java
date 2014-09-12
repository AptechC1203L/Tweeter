/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.controller;

import com.ngochin.tweeter.model.DaoFactory;
import com.ngochin.tweeter.model.Post;
import com.ngochin.tweeter.model.PostDao;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class PostServletTest {
    
    @Mock PostServlet ps;
    @Mock HttpServletRequest req;
    @Mock HttpServletResponse res;
    @Mock ServletContext ctx;
    @Mock DaoFactory f;
    @Mock PostDao postDao;
    @Mock RequestDispatcher rd;
    final String ctxPath = "/Tweeter/";
    
    public PostServletTest() {
    }
    
    @Before
    public void setup() throws ServletException, IOException {
        when(ps.getServletContext()).thenReturn(ctx);
        when(ctx.getAttribute("daoFactory")).thenReturn(f);
        when(f.getPostDao()).thenReturn(postDao);
        when(req.getRequestDispatcher(anyString())).thenReturn(rd);
        doCallRealMethod().when(ps).doGet(req, res);
        doCallRealMethod().when(ps).doPost(req, res);
        when(ctx.getContextPath()).thenReturn(ctxPath);
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
}
