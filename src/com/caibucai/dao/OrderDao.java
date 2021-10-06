package com.caibucai.dao;

import com.caibucai.bean.Order;

import java.util.List;

public interface OrderDao {
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    /**
     * 获取全部订单数
     * @return
     */
    int getTotal();

    /**
     * 添加订单
     */
    void add(Order bean);

    /**
     * 更新订单
     */
    void update(Order bean);

    /**
     * 删除订单
     */
    void delete(int id);

    /**
     * 获取订单
     */

    Order get(int id);

    List<Order> list();

    List<Order> list(int start,int count);

    List<Order> list(int uid,String excludeStatus);

    List<Order> list(int uid,String excludeStatus,int start,int count);


}
