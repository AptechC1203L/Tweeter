/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.controller;

import com.ngochin.tweeter.model.Post;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

/**
 *
 * @author chin
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeServletTest extends GenericServletTest {
    
    @Mock HomeServlet hs;
    
    @Before
    public void setup() throws Exception {
        when(hs.getServletContext()).thenReturn(ctx);
        doCallRealMethod().when(hs).processRequest(req, res);
        super.setup();
    }

    /**
     * Test of processRequest method, of class HomeServlet.
     */
    @Test
    public void testProcessRequest() throws Exception {
        Post p1 = new Post();
        Post p2 = new Post();
        
        List<Post> posts = Arrays.asList(p1, p2);
        final List<Post> reversedPosts = new ArrayList<>();
        
        when(postDao.getAllPosts()).thenReturn(posts);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                for (Post p : (List<Post>) invocation.getArguments()[1]) {
                    reversedPosts.add(p);
                }
                return null;
            }
        }).when(req).setAttribute(eq("posts"), anyList());
        hs.processRequest(req, res);

        // Reversed post order
        assertEquals(p1, reversedPosts.get(1));
        assertEquals(p2, reversedPosts.get(0));
    }
}
