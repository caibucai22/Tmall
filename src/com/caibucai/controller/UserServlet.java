package com.caibucai.controller;

import com.caibucai.bean.User;
import com.caibucai.utils.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Csy
 * @Classname UserServlet
 * @date 2021/9/9 19:38
 * @Description TODO
 */
public class UserServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<User> users = userDao.list(page.getStart(),page.getCount());
        int total = userDao.getToatal();
        page.setTotal(total);


        request.setAttribute("users",users);
        request.setAttribute("page",page);
        return "admin/listUser.jsp";
    }
}
