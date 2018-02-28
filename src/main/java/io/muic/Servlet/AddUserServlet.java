package io.muic.Servlet;

import com.ja.security.PasswordHash;
import io.muic.Service.MySQLService;
import io.muic.Service.SecurityService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUserServlet extends HttpServlet {
    private SecurityService securityService;
    private MySQLService mySQLService;
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
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/adduser.jsp");
            rd.include(request, response);
        }
        else {
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String name = request.getParameter("name");

        if (!username.equals("") && !password.equals("") && !repassword.equals("") && !name.equals(""))  {
            // authenticate
            if(username.length() > USERNAME_LENGTH){
                String userError = "Username length is greater than " + USERNAME_LENGTH;
                request.setAttribute("error", userError);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/adduser.jsp");
                rd.include(request, response);
            }
            else if(password.equals(repassword)) {
                try {
                    String hashPass = new PasswordHash().createHash(password);
                    mySQLService.writeData(username, hashPass, name);
                    response.sendRedirect("/");
                    System.out.println(1);
                }catch (MySQLIntegrityConstraintViolationException e){
                    String duplicateError = "That username is already taken.";
                    request.setAttribute("error", duplicateError);
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/adduser.jsp");
                    rd.include(request, response);
                    System.out.println(2);
                }
                catch (Exception e){
                    System.out.println("Shouldn't reach here");
                    e.printStackTrace();
                    System.out.println(3);
                }
            }
            else {
                String passError = "Password doesn't match each other.";
                request.setAttribute("error", passError);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/adduser.jsp");
                rd.include(request, response);
            }
        } else {
            String nullError = "Please fill in all the block.";
            request.setAttribute("error", nullError);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/adduser.jsp");
            rd.include(request, response);
        }


    }
}