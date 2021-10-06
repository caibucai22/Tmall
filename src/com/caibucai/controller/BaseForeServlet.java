package com.caibucai.controller;



import com.caibucai.dao.*;
import com.caibucai.dao.impl.*;
import com.caibucai.utils.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Csy
 * @Classname BaseForeServlet
 * @date 2021/9/9 19:42
 * @Description TODO
 */
public class BaseForeServlet extends HttpServlet {
    protected CategoryDao categoryDao = new CategoryDaoImpl();
    protected OrderDao orderDao = new OrderDaoImpl();
    protected OrderItemDao orderItemDao = new OrderItemDaoImpl();
    protected ProductDao productDao = new ProductDaoImpl();
    protected ProductImageDao productImageDao = new ProductImageDaoImpl();
    protected PropertyDao propertyDao = new PropertyDaoImpl();
    protected PropertyValueDao propertyValueDao = new PropertyValueDaoImpl();
    protected ReviewDao reviewDao = new ReviewDaoImpl();
    protected UserDao userDao = new UserDaoImpl();

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int start = 0;
            int count = 10;
            if (null != req.getParameter("page.start") && null != req.getParameter("page.count")) {
                start = Integer.valueOf(req.getParameter("page.start"));
                count = Integer.valueOf(req.getParameter("page.count"));
            }

            Page page = new Page(start, count);
            String method = (String) req.getAttribute("method");
            Method m = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class, Page.class);
            String redirect = m.invoke(this, req, resp, page).toString();
            if (redirect.startsWith("@")) {
                resp.sendRedirect(redirect.substring(1));
            } else if (redirect.startsWith("%")) {
                resp.getWriter().print(redirect.substring(1));
            } else {
                req.getRequestDispatcher(redirect).forward(req, resp);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
