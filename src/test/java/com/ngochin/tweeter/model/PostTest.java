/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.model;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author chin
 */
public class PostTest {
    
    public PostTest() {
    }

    @Test
    public void testExtractTags() {
        Post p = new Post();
        p.setText("@a");
        List<Post.Tag> tags = p.getTags();
        
        assertEquals(1, tags.size());
        Post.Tag t = tags.get(0);
        assertEquals("@a", t.getText());
        assertEquals(0, t.getStart());
        assertEquals(2, t.getLen());
    }
    
    @Test
    public void testExtractTagsWithPunctuation() {
        Post p = new Post();
        p.setText("@a..!/");
        List<Post.Tag> tags = p.getTags();

        assertEquals(1, tags.size());
        Post.Tag t = tags.get(0);
        assertEquals("@a", t.getText());
        assertEquals(0, t.getStart());
        assertEquals(2, t.getLen());
        
        p.setText("@a!");
        tags = p.getTags();

        assertEquals(1, tags.size());
        t = tags.get(0);
        assertEquals("@a", t.getText());
        assertEquals(0, t.getStart());
        assertEquals(2, t.getLen());
    }
    
    @Test
    public void testGetFragments() {
        User u = new User();
        UserDao mockedDao = mock(UserDao.class);
        when(mockedDao.getUser("me")).thenReturn(u);

        Post p = new Post();
        p.setUserDao(mockedDao);
        p.setText("a post @me");

        List<Object> fragments = p.getContentFragments();
        assertEquals(3, fragments.size());
        assertEquals("a post ", fragments.get(0));
        assertEquals(u, fragments.get(1));
        assertEquals("", fragments.get(2));
    }
}
