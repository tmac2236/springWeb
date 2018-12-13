package com.tmac2236.web.auth.session;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrossFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Init  CrossFilter");

        Enumeration enumeration = filterConfig.getInitParameterNames();
        while (enumeration.hasMoreElements()) {
            String eleName = (String) enumeration.nextElement();
            if (eleName == null || eleName == "") {
                continue;
            }
            System.out.println("Init parameter :" + filterConfig.getInitParameter(eleName));
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  httpServletRequest  = (HttpServletRequest)  request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestUri = httpServletRequest.getRequestURI();// SSH2/js/easyui.css
        
        // .css or .js  or .pdf  ....etc will be exclude.
        if(requestUri.lastIndexOf(".")!=-1){
            //capture String of '.css'  or  '.js'  or '.pdf' 
            String extension  = requestUri.substring(requestUri.lastIndexOf(".") , requestUri.length());
            chain.doFilter(httpServletRequest, httpServletResponse);
        }
        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setHeader("Expires", "0");
        
        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    public void destroy() {
        System.out.println("Destroy CrossFilter");
    }

}
