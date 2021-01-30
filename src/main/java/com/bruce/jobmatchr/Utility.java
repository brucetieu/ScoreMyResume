package com.bruce.jobmatchr;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class Utility {

    // Get the site url
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
