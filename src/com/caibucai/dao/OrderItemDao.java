package com.caibucai.dao;

import com.caibucai.bean.Order;
import com.caibucai.bean.OrderItem;

import java.util.List;

/**
 * @author Csy
 * @Classname OrderItemDao
 * @date 2021/9/9 19:37
 * @Description TODO
 */
public interface OrderItemDao {
    int getTotal();

    void add(OrderItem bean);

    void update(OrderItem bean);

    void delete(int id);

    /**
     * ªÒ»°
     */
    List<OrderItem> listByUser(int uid);

    List<OrderItem> listByUser(int uid,int start,int count);

    List<OrderItem> listByOrder(int oid);

    List<OrderItem> listByOrder(int oid,int start,int count);

    void fill(List<Order> os);

    void fill(Order o);

    List<OrderItem> listByProduct(int pid);

    List<OrderItem> listByProduct(int pid,int start,int count);

    int getSaleCount(int pid);
}
