/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.listener;

import com.ngochin.tweeter.model.DaoFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.ocpsoft.prettytime.PrettyTime;

/**
 * Web application lifecycle listener.
 *
 * @author chin
 */
@WebListener()
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        PrettyTime pt = new PrettyTime();
        ServletContext ctx = sce.getServletContext();
        ctx.setAttribute("prettyTime", pt);
        ctx.setAttribute("daoFactory", new DaoFactory());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
