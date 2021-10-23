package com.caibucai.controller;

import com.caibucai.bean.Order;
import com.caibucai.dao.OrderDao;
import com.caibucai.utils.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author Csy
 * @Classname OrderServlet
 * @date 2021-10-23 10:47
 * @Description 订单生成是由用户购买产生，后台不需要进行提供 增删改 管理，
 * 只需要提供 查询操作即可
 */
public class OrderServlet extends BaseBackServlet {
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
        List<Order> orders = orderDao.list(page.getStart(),page.getCount());
        orderItemDao.fill(orders);

        int total = orderDao.getTotal();
        page.setTotal(total);

        request.setAttribute("orders",orders);
        request.setAttribute("page",page);

        return "admin/listOrder.jsp";
    }

    public String delivery(HttpServletRequest request,HttpServletResponse response,Page page){
        int id = Integer.parseInt(request.getParameter("id"));
        Order order = orderDao.get(id);

        order.setDeliveryDate(new Date());
        order.setStatus(OrderDao.waitConfirm);

        // 更新订单
        orderDao.update(order);
        return "@admin_order_list";
    }
}
