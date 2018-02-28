package io.muic.Servlet;


import io.muic.Service.MySQLService;
import io.muic.Service.SecurityService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    private SecurityService securityService;
    private MySQLService mySQLService;
    public void setSecurityManager(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void setMySQLManager(MySQLService mySQLService){this.mySQLService = mySQLService;}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (authorized) {
            String username = (String) request.getSession().getAttribute("username");
            String firstName = mySQLService.getFirstName(username);
            request.setAttribute("username", username);
            request.setAttribute("firstname",firstName);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/user.jsp");
            rd.include(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }
}

