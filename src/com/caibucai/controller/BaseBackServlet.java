package com.caibucai.controller;

import com.caibucai.bean.ProductImage;
import com.caibucai.dao.*;
import com.caibucai.dao.impl.*;
import com.caibucai.utils.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Csy
 * @Classname BaseBackServlet
 * @date 2021/9/9 9:34
 * @Description 后台抽象类 增删改查
 */
public abstract class BaseBackServlet {

    /**
     *  添加
     */
    public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page);


    /**
     * 删除
     */
    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page);


    /**
     * 改
     */
    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page);

    /**
     *  更新
     * @param request
     * @param response
     * @param page
     * @return
     */



    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page);

    /**
     * 查询 列表形式
     * @param request
     * @param response
     * @param page
     * @return
     */
    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page);


    protected UserDao userDao = new UserDaoImpl();
    protected ReviewDao reviewDao = new ReviewDaoImpl();
    protected PropertyValueDao propertyValueDao = new PropertyValueDaoImpl();
    protected PropertyDao propertyDao = new PropertyDaoImpl();
    protected ProductDao productDao = new ProductDaoImpl();
    protected ProductImageDao productImageDao = new ProductImageDaoImpl();
    protected OrderItemDao orderItemDao = new OrderItemDaoImpl();
    protected OrderDao orderDao = new OrderDaoImpl();
    protected CategoryDao categoryDao = new CategoryDaoImpl();
}
