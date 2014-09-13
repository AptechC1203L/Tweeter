/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author chin
 */
public class UserTest {
    
    public UserTest() {
    }

    @Test
    public void testUsernameValidation() {
        String[] badUsernames = new String[] {
            "with-dash",
            "with/slash",
            "with,comma",
            "with;semicolon",
            "with:colon",
            "with?punc!tu@a#tion",
            "",
            "toooooooooooooooooooooooloooooooooooooooooonng"
        };

        String[] goodUsenames = new String[] {
            "justAlpha",
            "isnumber1",
            "i.can.haz.dot",
            "power_of_the_dash"
        };

        User u = new User();
        for (String username : badUsernames) {
            u.setUserId(username);
            assertFalse(u.hasValidUsername());
        }

        for (String username : goodUsenames) {
            u.setUserId(username);
            assertTrue(username, u.hasValidUsername());
        }
    }
}
