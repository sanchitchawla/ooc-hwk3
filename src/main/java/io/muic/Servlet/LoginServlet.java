package io.muic.Servlet;

import io.muic.Service.MySQLService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private MySQLService mySQLService;

    public void setMySQLManager(MySQLService mySQLManager) {
        this.mySQLService = mySQLManager;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
        rd.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(!username.equals("") && !password.equals("")){
            String[] inp = {username,password};
            try {
                if (mySQLService.isValidInput(inp)) {
                    request.getSession().setAttribute("username", username);

                    response.sendRedirect("/");

                } else {
                    String failError = "Wrong username or password.";
                    request.setAttribute("error", failError);
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
                    rd.include(request, response);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            String nullError = "Username or Password is missing.";
            request.setAttribute("error",nullError);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
            rd.include(request, response);
        }
    }


}
