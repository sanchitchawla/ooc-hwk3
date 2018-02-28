package io.muic.Servlet;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import io.muic.Service.MySQLService;
import io.muic.Service.SecurityService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserServlet extends HttpServlet {
    private SecurityService securityService;
    private MySQLService mySQLService;
    private String id;
    private int USERNAME_LENGTH = 12;

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
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edituser.jsp");
            rd.include(request, response);
        }
        else {
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String name = request.getParameter("name");


        if (!username.equals("") && !name.equals(""))  {
            // authenticate
            if(username.length()>USERNAME_LENGTH){
                String userError = "Username length is greater than " + USERNAME_LENGTH;
                request.setAttribute("error", userError);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edituser.jsp");
                rd.include(request, response);
            }
            else{
                try {
                    //update data
                    mySQLService.updateData(id,username,name);
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
                    rd.include(request, response);
                }catch (MySQLIntegrityConstraintViolationException e){
                    String duplicateError = "That username is already taken.";
                    request.setAttribute("error", duplicateError);
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edituser.jsp");
                    rd.include(request, response);
                }
                catch (Exception e){
                    System.out.println("Shouldn't reach here");
                    e.printStackTrace();
                }
            }

        } else {
            String nullError = "Please fill in all the block.";
            request.setAttribute("error", nullError);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edituser.jsp");
            rd.include(request, response);
        }


    }
}
