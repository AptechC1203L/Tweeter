/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.filter;

import com.ngochin.tweeter.model.DaoFactory;
import com.ngochin.tweeter.model.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chin
 */
@WebFilter(filterName = "PutUserObjectInSessionScopeFilter", urlPatterns = "/*")
public class PutUserObjectInSessionScopeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest r = (HttpServletRequest) request;
            DaoFactory daoFactory = new DaoFactory();
            User user = daoFactory.getUserDao().getUser(r.getRemoteUser());

            if (user != null) {
                HttpSession session = r.getSession(false);
                session.setAttribute("authUser", user);
            }
        }

        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
    }
    
}
