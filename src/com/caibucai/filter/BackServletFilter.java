package com.caibucai.filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Csy
 * @Classname BackServletFilter
 * @date 2021/10/5 21:02
 * @Description TODO
 */
public class BackServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String contextPath = request.getServletContext().getContextPath();
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri,contextPath);
        // 获得访问的uri
        if(uri.startsWith("/admin")){
            String servletPath = StringUtils.substringBetween(uri," "," ")+"Servlet";
            // categoryServlet
            String method = StringUtils.substringAfterLast(uri," ");
            // list

           request.setAttribute("method",method);
           request.getRequestDispatcher("/"+servletPath).forward(request,response);
           return;
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
