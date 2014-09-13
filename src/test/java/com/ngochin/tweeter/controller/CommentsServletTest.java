/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.controller;

import com.ngochin.tweeter.model.Comment;
import com.ngochin.tweeter.model.Notification;
import com.ngochin.tweeter.model.Post;
import com.ngochin.tweeter.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
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
public class CommentsServletTest extends GenericServletTest {
    
    @Mock CommentsServlet cs;

    public CommentsServletTest() {
    }
    
    @Before
    @Override
    public void setup() throws Exception {
        when(cs.getServletContext()).thenReturn(ctx);
        doCallRealMethod().when(cs).processRequest(req, res);
        super.setup();
    }

    @Test
    public void testCreateComment() throws Exception {
        Post p = mock(Post.class);
        User owner = new User();
        owner.setUserId("owner");

        when(p.getId()).thenReturn(1);
        when(p.getUsername()).thenReturn("trung");
        when(p.getComments()).thenReturn(new ArrayList<Comment>());
        when(p.getPoster()).thenReturn(owner);

        when(req.getParameter("text")).thenReturn("the comment");
        when(req.getParameter("postId")).thenReturn("1");
        when(postDao.getPost(1)).thenReturn(p);

        cs.processRequest(req, res);
        
        verify(commentDao).addComment(any(Comment.class));
    }
    
    @Test
    public void testCreateNotiIfCommenterIsNotOwner() throws Exception {
        Post p = mock(Post.class);
        when(p.getId()).thenReturn(1);
        when(p.getUsername()).thenReturn("mike");
        when(p.getComments()).thenReturn(new ArrayList<Comment>());
        
        // Commenter is not post owner
        assertFalse(p.getUsername().equals(authUser.getUserId()));
        
        when(req.getParameter("text")).thenReturn("the comment");
        when(req.getParameter("postId")).thenReturn("1");
        when(postDao.getPost(1)).thenReturn(p);

        cs.processRequest(req, res);
        verify(notiDao).addNotification(any(Notification.class));
    }

    @Test
    public void testCreateNotiForAllOtherCommenters() throws Exception {
        User owner = new User();
        User fstCommenter = new User();  // authUser is the 2nd commenter
        User taggee = new User();
        Post p = mock(Post.class);

        owner.setUserId("james");
        fstCommenter.setUserId("mike");
        taggee.setUserId("taggee");

        when(p.getPoster()).thenReturn(owner);
        when(p.getUsername()).thenReturn(owner.getUserId());
        when(p.getId()).thenReturn(0);
        
        when(userDao.getUser("james")).thenReturn(owner);
        when(userDao.getUser("mike")).thenReturn(fstCommenter);
        when(userDao.getUser("taggee")).thenReturn(taggee);
        
        // There was a previous comment
        Comment firstComment = new Comment();
        firstComment.setUserId(fstCommenter.getUserId());

        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(firstComment);

        when(p.getComments()).thenReturn(comments);
        
        ArrayList<User> taggedUsers = new ArrayList<>();
        taggedUsers.add(taggee);
        when(p.getTaggedUsers()).thenReturn(taggedUsers);

        when(postDao.getPost(0)).thenReturn(p);
        when(req.getParameter("postId")).thenReturn("0");
        when(req.getParameter("text")).thenReturn("The comment aimed at @taggee");

        final HashMap<String, Integer> notis = new HashMap<>();

        doAnswer(new Answer<Boolean>() {

            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                Notification n = (Notification) invocation.getArguments()[0];
                if (notis.containsKey(n.getUsername())) {
                    int count = notis.get(n.getUsername());
                    notis.put(n.getUsername(), count + 1);
                } else {
                    notis.put(n.getUsername(), 1);
                }

                return true;
            }
        }).when(notiDao).addNotification(any(Notification.class));

        cs.processRequest(req, res);

        // The owner, the first commenter and the tagged user each should have 
        // exactly one notification
        verify(notiDao, times(3)).addNotification(any(Notification.class));
        assertTrue(notis.containsKey(owner.getUserId()));
        assertTrue(notis.containsKey(fstCommenter.getUserId()));
        assertTrue(notis.containsKey(taggee.getUserId()));
        
        assertEquals(1, (int) notis.get(owner.getUserId()));
        assertEquals(1, (int) notis.get(fstCommenter.getUserId()));
        assertEquals(1, (int) notis.get(taggee.getUserId()));
    }
}
