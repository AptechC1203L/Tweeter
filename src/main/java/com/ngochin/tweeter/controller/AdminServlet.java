/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngochin.tweeter.controller;

import com.ngochin.tweeter.model.DaoFactory;
import com.ngochin.tweeter.model.User;
import com.ngochin.tweeter.model.UserDao;
import java.io.IOException;
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
@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

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
        DaoFactory daoFactory = new DaoFactory();
        
        UserDao userDao = daoFactory.getUserDao();

        if (request.getMethod().equals("POST")) {
            String username = request.getParameter("username");
            String fullName = request.getParameter("fullname");
            String password = request.getParameter("password");
            String confirmedPassword = request.getParameter("confirmed_password");
            String[] roles = request.getParameterValues("roles");

            if (username == null || username.isEmpty()) {
                request.setAttribute("error", "Username cannot be empty!");
            } else if (userDao.getUser(username) != null) {
                request.setAttribute("error", "That username has already been used!");
            } else if (password == null || password.isEmpty()) {
                request.setAttribute("error", "Password cannot be empty!");
            } else if (!password.equals(confirmedPassword)) {
                request.setAttribute("error", "Passwords do not match!");
            } else if (!User.isValidUsername(username)) {
                request.setAttribute("error", "Invalid username!");
            } else if (roles == null) {
                request.setAttribute("error", "At least one role must be selected!");
            } else {
                User u = new User();
                u.setUserId(username);
                u.setFullName(fullName);

                for (String role: roles) {
                    u.addRole(role);
                }

                // FIXME: Digest?
                u.setPassword(password);

                boolean ok = userDao.addUser(u);
                if (!ok) {
                    request.setAttribute("error", "Something wrong happened...");
                }
            }
        }

        request.setAttribute("users", userDao.getAllUsers());

        RequestDispatcher rd = request.getRequestDispatcher("/admin.jsp");
        rd.forward(request, response);
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
