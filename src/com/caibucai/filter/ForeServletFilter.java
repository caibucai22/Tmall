package com.caibucai.filter;


import com.caibucai.bean.Category;
import com.caibucai.bean.OrderItem;
import com.caibucai.bean.User;
import com.caibucai.dao.impl.CategoryDaoImpl;
import com.caibucai.dao.impl.OrderItemDaoImpl;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Csy
 * @Classname ForeServletFilter
 * @date 2021/9/26 22:08
 * @Description TODO
 */
public class ForeServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String contextPath = request.getServletContext().getContextPath();
        request.getServletContext().setAttribute("contextPath",contextPath);

        User user = (User) request.getSession().getAttribute("user");
        int cartTotalItemNumber = 0;
        if(null != user){
            List<OrderItem> orderItems = new OrderItemDaoImpl().listByUser(user.getId());
            for (OrderItem orderItem : orderItems) {
                cartTotalItemNumber += orderItem.getNumber();
            }
        }
       request.setAttribute("cartTotalItemNumber",cartTotalItemNumber);

        List<Category> categories = (List<Category>) request.getAttribute("categories");
        if(null == categories){
            categories = new CategoryDaoImpl().list();
            request.setAttribute("categories",categories);
        }

        // 对请求路径进行处理
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri,contextPath);
        if(uri.startsWith("/fore")&&!uri.startsWith("/foreServlet")){
            String method = StringUtils.substringAfterLast(uri,"/fore");
            request.setAttribute("method",method);
            servletRequest.getRequestDispatcher("/foreServlet").forward(request,response);
            return;
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
