package com.caibucai.controller;

import com.caibucai.bean.ProductImage;
import com.caibucai.dao.*;
import com.caibucai.dao.impl.*;
import com.caibucai.utils.Page;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Csy
 * @Classname BaseBackServlet
 * @date 2021/9/9 9:34
 * @Description ��̨������ ��ɾ�Ĳ�
 */
public abstract class BaseBackServlet extends HttpServlet {

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
    protected ReviewDao reviewDao = new ReviewDaoImpl();
    protected PropertyValueDao propertyValueDao = new PropertyValueDaoImpl();
    protected PropertyDao propertyDao = new PropertyDaoImpl();
    protected ProductDao productDao = new ProductDaoImpl();
    protected ProductImageDao productImageDao = new ProductImageDaoImpl();
    protected OrderItemDao orderItemDao = new OrderItemDaoImpl();
    protected OrderDao orderDao = new OrderDaoImpl();
    protected CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int start = 0;
            int count = 5;

            try {
                start = Integer.parseInt(req.getParameter("page.start"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                count = Integer.parseInt(req.getParameter("page.count"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            Page page = new Page(start,count);

            String method = (String) req.getAttribute("method");

            Method m = this.getClass().getMethod(method,javax.servlet.http.HttpServletRequest.class,
                    javax.servlet.http.HttpServletResponse.class,Page.class);
            String redirect = m.invoke(this,req,resp).toString();

            // ���ݷ����ķ���ֵ��������Ӧ�Ŀͻ�����ת���������ת�����߽���������ַ���

            if(redirect.startsWith("@")){
                resp.sendRedirect(redirect.substring(1));
            }else if(redirect.startsWith("%")){
                resp.getWriter().print(redirect.substring(1));
            }else{
                req.getRequestDispatcher(redirect).forward(req,resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public InputStream parseUpload(HttpServletRequest request, HttpServletResponse response, Map<String,String> params){
        InputStream is = null;

        try {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        // �����ϴ��ļ��Ĵ�С����Ϊ10M
        factory.setSizeThreshold(1024*1024);
        List items = upload.parseRequest(request);

            Iterator it = items.iterator();
            while(it.hasNext()){
                FileItem item = (FileItem) it.next();
                if(!item.isFormField()){
                    System.out.println("�ϴ��ļ�:�����ȡ��������item.isFormFieldΪtrue");
                    is = item.getInputStream();
                }else{
                    System.out.println("�ϴ��ļ�:δ�����ȡ����������item.isFormFieldΪfalse");

                    String paramName = item.getFieldName();
                    String paramValue = item.getString();
                    System.out.println(paramName+":"+paramValue);
                    paramValue = new String(paramValue.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
                    params.put(paramName,paramValue);

                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }
}
