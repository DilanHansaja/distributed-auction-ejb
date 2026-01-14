package com.dilanhansaja.ee.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.dilanhansaja.ee.ejb.remote.UserService;

import java.io.IOException;

@WebServlet("/userRegistration")
public class UserRegistration extends HttpServlet {

    @EJB
    UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        String confirmPassword = request.getParameter("confirm").trim();

        String msg =   userService.register(name, email, password, confirmPassword);

        if(msg.equals("Success")) {
            response.sendRedirect("login.jsp");
        }else{

            request.setAttribute("error", msg);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }

    }
}
