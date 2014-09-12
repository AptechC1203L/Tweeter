/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.controller;

import com.ngochin.tweeter.model.Comment;
import com.ngochin.tweeter.model.DaoFactory;
import com.ngochin.tweeter.model.Notification;
import com.ngochin.tweeter.model.Post;
import com.ngochin.tweeter.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author chin
 */
@WebServlet(name = "CommentsServlet", urlPatterns = {"/comments"})
public class CommentsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commentText = request.getParameter("text");
        int postId = Integer.parseInt(request.getParameter("postId"));
        
        Comment c = new Comment();

        if (!(commentText == null || commentText.isEmpty())) {
            DaoFactory daoFactory = new DaoFactory();
            Post p = daoFactory.getPostDao().getPost(postId);
            User commentUser = (User) request.getSession().getAttribute("authUser");

            c.setText(commentText);
            c.setUserId(request.getRemoteUser());
            c.setPostId(postId);

            // If the comment is from a different user than the currently
            // logged in user
            if (!c.getUserId().equals(p.getUsername())) {
                // Then create a notification
                Notification n = new Notification();
                n.setMessage(commentUser.getFullName() + " commented on your post.");
                n.setLink("/post/" + Integer.toString(postId));
                n.setUsername(p.getUsername());

                daoFactory.getNotificationDao().addNotification(n);
            }

            daoFactory.getCommentDao().addComment(c);
        }
        
        response.sendRedirect(request.getHeader("referer"));
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

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
