package io.muic.Service;

import javax.servlet.http.HttpServletRequest;

public class SecurityService {

    public boolean isAuthorized(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");

        return username != null;
    }
}
