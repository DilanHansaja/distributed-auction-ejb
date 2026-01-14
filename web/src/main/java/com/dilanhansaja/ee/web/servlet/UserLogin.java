package com.dilanhansaja.ee.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.dilanhansaja.ee.ejb.remote.UserService;

import java.io.IOException;

@WebServlet("/userLogin")
public class UserLogin extends HttpServlet {

    @EJB
    UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        String msg = userService.login(email, password);

        if(msg.equals("Success")) {
            req.getSession().setAttribute("user", email);

            if(req.getParameter("id")!=null) {
                resp.sendRedirect("singleAuction?id="+req.getParameter("id"));
            }else{
                resp.sendRedirect("home");
            }

        }else{
            req.setAttribute("error",msg);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

    }
}
