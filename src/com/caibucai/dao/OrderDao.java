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
     * ��ȡȫ��������
     * @return
     */
    int getTotal();

    /**
     * ��Ӷ���
     */
    void add(Order bean);

    /**
     * ���¶���
     */
    void update(Order bean);

    /**
     * ɾ������
     */
    void delete(int id);

    /**
     * ��ȡ����
     */

    Order get(int id);

    List<Order> list();

    List<Order> list(int start,int count);

    List<Order> list(int uid,String excludeStatus);

    List<Order> list(int uid,String excludeStatus,int start,int count);


}
