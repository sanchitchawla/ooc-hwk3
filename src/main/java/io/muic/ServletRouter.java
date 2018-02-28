package io.muic;

import io.muic.Service.MySQLService;
import io.muic.Service.SecurityService;
import io.muic.Servlet.*;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class ServletRouter {

    private SecurityService securityService;
    private MySQLService mySQLService;

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void setMySQLService(MySQLService mySQLService) {
        this.mySQLService = mySQLService;
    }

    public void init(Context ctx) {
        initHome(ctx);
        initLogin(ctx);
        initRegister(ctx);
        initAddUser(ctx);
        initEditUser(ctx);
        initDeleteUser(ctx);
        initUser(ctx);
    }

    private void initUser(Context ctx) {
        UserServlet userServlet = new UserServlet();
        userServlet.setMySQLManager(mySQLService);
        userServlet.setSecurityManager(securityService);
        Tomcat.addServlet(ctx,"UserServlet",userServlet);
        ctx.addServletMapping("/user","UserServlet");
    }

    private void initDeleteUser(Context ctx) {
        DeleteUserServlet deleteUserServlet = new DeleteUserServlet();
        deleteUserServlet.setSecurityManager(securityService);
        deleteUserServlet.setMySQLManager(mySQLService);
        Tomcat.addServlet(ctx,"DeleteUserServlet",deleteUserServlet);
        ctx.addServletMapping("/deleteuser","DeleteUserServlet");
    }

    private void initEditUser(Context ctx) {
        EditUserServlet editServlet = new EditUserServlet();
        editServlet.setSecurityManager(securityService);
        editServlet.setMySQLManager(mySQLService);
        Tomcat.addServlet(ctx, "EditUserServlet", editServlet);
        ctx.addServletMapping("/edituser", "EditUserServlet");
    }

    private void initRegister(Context ctx) {
        RegisterServlet registerServlet = new RegisterServlet();
        registerServlet.setSecurityManager(securityService);
        registerServlet.setMySQLManager(mySQLService);
        Tomcat.addServlet(ctx, "RegisterServlet", registerServlet);
        ctx.addServletMapping("/register", "RegisterServlet");
    }
    private void initAddUser(Context ctx) {
        AddUserServlet addUserServlet = new AddUserServlet();
        addUserServlet.setSecurityManager(securityService);
        addUserServlet.setMySQLManager(mySQLService);
        Tomcat.addServlet(ctx, "AddUserServlet", addUserServlet);
        ctx.addServletMapping("/adduser", "AddUserServlet");
    }


    private void initHome(Context ctx) {
        HomeServlet homeServlet = new HomeServlet();
        homeServlet.setSecurityManager(securityService);
        Tomcat.addServlet(ctx, "HomeServlet", homeServlet);
        ctx.addServletMapping("/index.jsp", "HomeServlet");
    }

    private void initLogin(Context ctx) {
        LoginServlet loginServlet = new LoginServlet();
        loginServlet.setMySQLManager(mySQLService);
        Tomcat.addServlet(ctx, "LoginServlet", loginServlet);
        ctx.addServletMapping("/login", "LoginServlet");
    }


}
