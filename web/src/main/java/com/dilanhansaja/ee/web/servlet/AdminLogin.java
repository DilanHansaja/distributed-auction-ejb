package com.dilanhansaja.ee.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.dilanhansaja.ee.ejb.remote.AdminService;

import java.io.IOException;

@WebServlet("/adminLogin")
public class AdminLogin extends HttpServlet {

    @EJB
    private AdminService adminService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (adminService.validate(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", username);

            response.sendRedirect("adminDashboard");

        } else {
            request.setAttribute("error", "Invalid credentials");
            request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
        }

    }
}
