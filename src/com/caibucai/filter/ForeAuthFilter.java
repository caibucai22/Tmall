package com.caibucai.filter;

import com.caibucai.bean.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Csy
 * @Classname ForeAuthFilter
 * @date 2021/9/28 21:05
 * @Description TODO
 */
public class ForeAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String contextPath = request.getServletContext().getContextPath();

        String[] noNeedAuthPage = new String[]{
                "home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"
        };

        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri,contextPath);
        if(uri.startsWith("/fore") && !uri.startsWith("foreServlet")){
            String method = StringUtils.substringAfterLast(uri,"fore");

            if(!Arrays.asList(noNeedAuthPage).contains(method)){
                User user = (User) request.getSession().getAttribute("user");
                if(null == user){
                    response.sendRedirect("login.jsp");
                    return;
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
