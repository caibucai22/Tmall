package com.caibucai.controller;

import com.caibucai.bean.Category;
import com.caibucai.bean.Property;
import com.caibucai.utils.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Csy
 * @Classname PropertyServlet
 * @date 2021-10-21 17:36
 * @Description TODO
 */
public class PropertyServlet extends BaseBackServlet {


    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDao.get(cid);

        String name = request.getParameter("name");
        Property p = new Property();
        p.setCategory(c);
        p.setName(name);
        propertyDao.add(p);
        return "@admin_property_list?cid="+cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("cid"));
        Property p = propertyDao.get(id);

        propertyDao.delete(id);
        return "@admin_property_list?cid="+p.getCategory();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Property p = propertyDao.get(id);
        request.setAttribute("p",p);
        return "admin/editProperty.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDao.get(cid);

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        Property p = new Property();
        p.setCategory(c);
        p.setId(id);
        p.setName(name);
        propertyDao.update(p);
        return "@admin_property_list?cid="+p.getCategory().getId();
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDao.get(cid);
        List<Property> ps = propertyDao.list(cid,page.getStart(),page.getCount());
        int total = propertyDao.getTotal(cid);
        page.setTotal(total);
        page.setParam("&cid="+category.getId());

        request.setAttribute("ps",ps);
        request.setAttribute("c",category);
        request.setAttribute("page",page);
        return "admin/listProperty.jsp";
    }
}
