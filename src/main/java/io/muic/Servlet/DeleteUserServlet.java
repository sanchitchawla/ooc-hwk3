package io.muic.Servlet;

import io.muic.Service.MySQLService;
import io.muic.Service.SecurityService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class DeleteUserServlet extends HttpServlet {
    private SecurityService securityService;
    private MySQLService mySQLService;
    private String id;

    public void setMySQLManager(MySQLService mySQLManager) {
        this.mySQLService = mySQLManager;
    }

    public void setSecurityManager(SecurityService securityService) {
        this.securityService = securityService;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);

        if(authorized) {
            id = request.getParameter("id");
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/delete.jsp");
            rd.include(request, response);
        }
        else {
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HashMap<String, String> idUsr = mySQLService.getIdUsr();

            if (id == null || request.getSession().getAttribute("username").equals(idUsr.get(id))) {
                response.sendRedirect("/");
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
                rd.include(request, response);
            }
            else {
                try {
                    mySQLService.deleteData(id);
                    response.sendRedirect("/");
                } catch (Exception e) {
                    System.out.println("Shouldn't reach here");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Get Fail");
        }
    }
}
