package com.caibucai.controller;

import com.caibucai.bean.Category;
import com.caibucai.bean.Product;
import com.caibucai.bean.Property;
import com.caibucai.bean.PropertyValue;
import com.caibucai.dao.CategoryDao;
import com.caibucai.utils.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Csy
 * @Classname ProductServlet
 * @date 2021/10/15 20:18
 * @Description TODO
 */
public class ProductServlet extends BaseBackServlet {


    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDao.get(cid);

        String name = request.getParameter("name");
        String subTitle = request.getParameter("subTitle");
        System.out.println("originalPrice"+request.getParameter("originalPrice"));
        Double originalPrice = Double.parseDouble(request.getParameter("originalPrice"));
        Double promotePrice = Double.parseDouble(request.getParameter("promotePrice"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Product p = new Product();
        p.setCategory(c);
        p.setName(name);
        p.setSubTitle(subTitle);
        p.setOriginalPrice(originalPrice);
        p.setPromotePrice(promotePrice);
        p.setStock(stock);

        productDao.add(p);

        return "@admin_product_list?cid="+cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product p = productDao.get(id);
        productDao.delete(id);
        return "@admin_product_list?cid="+p.getCategory().getId();

    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product p = productDao.get(id);
        request.setAttribute("p",p);

        return "admin/editProduct.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {

        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDao.get(cid);

        int id = Integer.parseInt(request.getParameter("id"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        Double originalPrice = Double.parseDouble(request.getParameter("originalPrice"));
        Double promotePrice = Double.parseDouble(request.getParameter("promotePrice"));
        String subtitl = request.getParameter("subtitle");
        String name = request.getParameter("name");

        Product p = new Product();
        p.setName(name);
        p.setSubTitle(subtitl);
        p.setOriginalPrice(originalPrice);
        p.setPromotePrice(promotePrice);
        p.setStock(stock);
        p.setId(id);

        productDao.update(p);

        return "@admin_product_list?cid="+p.getCategory().getId();
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDao.get(cid);
        List<Product> ps = productDao.list(cid,page.getStart(),page.getCount());

        int total = productDao.getTotal(cid);
        page.setTotal(total);
        page.setParam("&cid="+category.getId());

        request.setAttribute("ps",ps);
        request.setAttribute("category",category);
        request.setAttribute("page",page);

        return "admin/listProduct.jsp";
    }

    public String editPropertyValue(HttpServletRequest request,HttpServletResponse response,Page page){
        int id = Integer.parseInt(request.getParameter("id"));
        Product p = productDao.get(id);

        List<Property> pts = propertyDao.list(p.getCategory().getId());
        propertyValueDao.init(p);
        List<PropertyValue> pvs = propertyValueDao.list(p.getId());
        request.setAttribute("pvs",pvs);

        return "admin/editProductValue.jsp";
    }


    public String updatPropertyValue(HttpServletRequest request,HttpServletResponse response,Page page){
        int pvid = Integer.parseInt(request.getParameter("pvid"));
        String value = request.getParameter("value");

        PropertyValue pv = propertyValueDao.get(pvid);
        pv.setValue(value);

        propertyValueDao.update(pv);

        return "%success";
    }
}
