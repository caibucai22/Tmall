package com.caibucai.controller;

import com.caibucai.bean.*;
import com.caibucai.dao.ProductImageDao;
import com.caibucai.dao.impl.CategoryDaoImpl;
import com.caibucai.dao.impl.ProductDaoImpl;
import com.caibucai.utils.Page;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * @author Csy
 * @Classname ForeServlet
 * @date 2021/9/9 20:20
 * @Description TODO
 */
public class ForeServlet extends BaseForeServlet {

    public String home(HttpServletRequest request,HttpServletResponse response,Page page){
        List<Category> categories = new CategoryDaoImpl().list();
        new ProductDaoImpl().fill(categories);
        new ProductDaoImpl().fillByRow(categories);

        request.setAttribute("categories",categories);

        return "index.jsp";
    }

    public String register(HttpServletRequest request, HttpServletResponse rep, Page page) throws IOException {
        request.setCharacterEncoding("utf-8");
        // post ����Ĳ��������������У� get ���������������
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        name = HtmlUtils.htmlEscape(name);

        boolean exist = userDao.isExist(name);

        if(exist){
            request.setAttribute("msg","���û����Ѿ���ע���");
            return "register.jsp";
        }

        // ������
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        // ����û���ж��Ƿ�ע��ɹ� ����ע��ʧ�� Ĭ���ǳɹ�

        userDao.add(user);

        return "@registerSuccess.jsp";
    }

    public String login(HttpServletRequest req,HttpServletResponse response,Page page){
        String name = req.getParameter("name");
        name = HtmlUtils.htmlEscape(name);
        String password = req.getParameter("password");

        // ��ѯ�û��Ƿ����
        User loginUser = userDao.get(name,password);

        // �û�������
        if(null == loginUser){
            req.setAttribute("msg","�˺��������");
            return "login.jsp";
        }

        // �û����ڵ�¼�ɹ������û���Ϣ����session�� �Ա����ݹ���
        req.getSession().setAttribute("user",loginUser);
        // ��ת��ǰ̨ҳ��
        return "@forehome";
    }

    public String logout(HttpServletRequest req,HttpServletResponse rep,Page page){
        // ��session ���Ƴ� ���˳�
        req.getSession().removeAttribute("user");
        return "@forehome";
    }


    public String product(HttpServletRequest request,HttpServletResponse response,Page page){
        //��ȡ
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product p = productDao.get(pid);

        List<ProductImage> productSingleImages = productImageDao.list(p, ProductImageDao.type_single);
        List<ProductImage> productDetailImages = productImageDao.list(p,ProductImageDao.type_detail);

        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);

        List<PropertyValue> propertyValues = propertyValueDao.list(p.getId());
        List<Review> reviews = reviewDao.list(p.getId());

        productDao.setSaleAndReviewNumber(p);

        request.setAttribute("reviews",reviews);

        request.setAttribute("p",p);
        request.setAttribute("propertyValues",propertyValues);

        return "product.jsp";
    }


    public String checkLogin(HttpServletRequest request,HttpServletResponse response,Page page){

        User user = (User) request.getSession().getAttribute("user");
        if(null == user){
            return "%success";
        }
        return "%fail";
    }


    public String loginAjax(HttpServletRequest request,HttpServletResponse response,Page page){

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        User user = userDao.get(name,password);

        if(null == user){
            return "%fail";
        }
        request.getSession().setAttribute("user",user);
        return "%success";
    }


    public String category(HttpServletRequest request,HttpServletResponse response,Page page){
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = new CategoryDaoImpl().get(cid);
        new ProductDaoImpl().fill(category);
        new ProductDaoImpl().setSaleAndReviewNumber(category.getProducts());

        String sort = request.getParameter("sort");
        if(null!=sort){
            switch (sort){
                case "review":
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o2.getReviewCount()-o1.getReviewCount();
                        }
                    });
                    break;
                case "date":
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o1.getCreateDate().compareTo(o2.getCreateDate());
                        }
                    });
                    break;
                case "saleCount":
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o1.getSaleCount()-o2.getSaleCount();
                        }
                    });
                    break;
                case "price":
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return (int)(o1.getPromotePrice()-o2.getPromotePrice());
                        }
                    });
                    break;
                case "all":
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o1.getReviewCount()*o1.getSaleCount()
                                    -o2.getReviewCount()*o2.getSaleCount();
                        }
                    });
                    break;
                    default:
                        break;
            }
        }
        request.setAttribute("category",category);
        return "category.jsp";
    }

    public String search(HttpServletRequest request,HttpServletResponse response,Page page){
        String keyword = request.getParameter("keyword");
        List<Product> products = new ProductDaoImpl().search(keyword,0,20);
        request.setAttribute("ps",products);
        return "searchResult.jsp";
    }

    /**
     * ����
     */
    public String buyone(HttpServletRequest request,HttpServletResponse response,Page page){
        int pid = Integer.parseInt(request.getParameter("pid"));
        int num = Integer.parseInt(request.getParameter("num"));
        Product p = productDao.get(pid);
        int orderItemId = 0;

        // �������Ʒ�Ѿ������������б����ҵ���������һ
        User user = (User) request.getSession().getAttribute("user");
        boolean found = false;
        List<OrderItem> orderItems = orderItemDao.listByUser(user.getId());
        for (OrderItem orderItem : orderItems) {
            if(orderItem.getProduct().getId() == p.getId()){
                orderItem.setNumber(orderItem.getNumber()+num);
                orderItemDao.update(orderItem);
                found = true;
            }
        }

        // û������ʹ����µĶ�����
        if(!found){
            OrderItem orderItem = new OrderItem();
            orderItem.setUser(user);
            orderItem.setNumber(num);
            orderItem.setProduct(p);
            orderItemDao.add(orderItem);
            orderItemId = orderItem.getId();
        }
        return "@forebuy?orderItemId="+orderItemId;
    }

    public String buy(HttpServletRequest request,HttpServletResponse response,Page page){
        String[] oiids = request.getParameterValues("orderItemId");
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;
        for (String strid : oiids) {
            int orderItemId = Integer.parseInt(strid);
            OrderItem orderItem = orderItemDao.get(orderItemId);
            total += orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
            orderItems.add(orderItem);
        }
        request.getSession().setAttribute("orderItems",orderItems);
        request.setAttribute("total",total);
        return "buy.jsp";
    }
}
