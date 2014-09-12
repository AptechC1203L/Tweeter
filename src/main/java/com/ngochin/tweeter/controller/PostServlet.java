/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.controller;

import com.ngochin.tweeter.model.DaoFactory;
import com.ngochin.tweeter.model.Notification;
import com.ngochin.tweeter.model.NotificationDao;
import com.ngochin.tweeter.model.Post;
import com.ngochin.tweeter.model.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author chin
 */
@WebServlet(name = "PostServlet", urlPatterns = {"/post/*"})
public class PostServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postId = request.getPathInfo();
        
        // The empty pathInfo is '/'
        if (postId.length() > 1) {
            try {
                int id = Integer.parseInt(postId.substring(1));
                
                // This factory could have been extracted out but it messes
                // with testing.
                DaoFactory f = (DaoFactory) getServletContext().getAttribute("daoFactory");
                Post post = f.getPostDao().getPost(id);
                if (post != null) {
                    request.setAttribute("post", post);

                    RequestDispatcher rd = request.getRequestDispatcher("/single-post.jsp");
                    rd.forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                // Fallthrough
            }
        }
        
        response.sendRedirect(getServletContext().getContextPath());
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postContent = request.getParameter("postContent");

        if (!(postContent == null || postContent.isEmpty())) {
            DaoFactory f = (DaoFactory) getServletContext().getAttribute("daoFactory");
            
            Post p = new Post();
            p.setText(postContent);
            p.setUsername(request.getRemoteUser());
            p.setUserDao(f.getUserDao());
            
            NotificationDao nDao = f.getNotificationDao();
            Post addedPost = f.getPostDao().addPost(p);
            
            List<User> taggedUsers = p.getTaggedUsers();
            if (taggedUsers.size() > 0) {
                for (User u : taggedUsers) {
                    if (!u.getUserId().equals(request.getRemoteUser())) {
                        Notification n = new Notification();
                        n.setMessage(p.getPoster().getFullName() + " tagged you in a post.");
                        n.setUsername(u.getUserId());
                        n.setLink("/post/" + Integer.toString(addedPost.getId()));

                        nDao.addNotification(n);
                    }
                }
            }
        }

        response.sendRedirect(request.getHeader("referer"));
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
