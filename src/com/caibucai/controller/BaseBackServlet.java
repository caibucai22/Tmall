package com.caibucai.controller;

import com.caibucai.dao.UserDao;
import com.caibucai.dao.impl.UserDaoImpl;
import com.caibucai.utils.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Csy
 * @Classname BaseBackServlet
 * @date 2021/9/9 9:34
 * @Description ��̨������ ��ɾ�Ĳ�
 */
public abstract class BaseBackServlet {

    /**
     *  ���
     */
    public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page);


    /**
     * ɾ��
     */
    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page);


    /**
     * ��
     */
    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page);

    /**
     *  ����
     * @param request
     * @param response
     * @param page
     * @return
     */



    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page);

    /**
     * ��ѯ �б���ʽ
     * @param request
     * @param response
     * @param page
     * @return
     */
    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page);


    protected UserDao userDao = new UserDaoImpl();
}
