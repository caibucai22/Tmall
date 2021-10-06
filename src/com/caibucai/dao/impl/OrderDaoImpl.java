package com.caibucai.dao.impl;

import com.caibucai.bean.Order;
import com.caibucai.bean.User;
import com.caibucai.dao.OrderDao;
import com.caibucai.utils.DBUtil;
import com.caibucai.utils.DateUtil;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Csy
 * @Classname OrderDaoImpl
 * @date 2021/9/9 19:44
 * @Description TODO
 */
public class OrderDaoImpl implements OrderDao {
    @Override
    public int getTotal() {
        int total = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select count(*) from order_";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }
        return total;
    }

    @Override
    public void add(Order bean) {
        String sql = "insert into order_ values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
        try(Connection connection = DBUtil.getConnection();PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,bean.getOrderCode());
            ps.setString(2,bean.getAddress());
            ps.setString(3,bean.getPost());
            ps.setString(4,bean.getReceiver());
            ps.setString(6,bean.getUserMessage());

            ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
            ps.setTimestamp(8,DateUtil.d2t(bean.getPayDate()));
            ps.setTimestamp(9,DateUtil.d2t(bean.getDeliveryDate()));
            ps.setTimestamp(10,DateUtil.d2t(bean.getConfirmDate()));

            ps.setInt(11,bean.getUser().getId());
            ps.setString(12,bean.getStatus());

            boolean success = ps.execute();
            System.out.println(success?"添加订单成功":"添加订单失败");
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                bean.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Order bean) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "update order_ set address=?,post =?,receiver=?,mobile=?,userMessage=?,createDate=?,payDate=?,deliveryDate=?,confirmDate=?,orderCode=? ,uid=?,status=? where id=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,bean.getAddress());
            ps.setString(2,bean.getPost());
            ps.setString(3,bean.getReceiver());
            ps.setString(4,bean.getMobile());
            ps.setString(5,bean.getUserMessage());
            ps.setTimestamp(6,DateUtil.d2t(bean.getCreateDate()));
            ps.setTimestamp(7,DateUtil.d2t(bean.getPayDate()));
            ps.setTimestamp(8,DateUtil.d2t(bean.getDeliveryDate()));
            ps.setTimestamp(9,DateUtil.d2t(bean.getConfirmDate()));
            ps.setString(10,bean.getOrderCode());
            ps.setInt(11,bean.getUser().getId());
            ps.setString(12,bean.getStatus());
            ps.setInt(13,bean.getId());

            boolean success = ps.execute();
            System.out.println(success?"更新订单成功":"更新订单失败");

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(null,ps,connection);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from order_ where id="+id;
        try(Connection connection = DBUtil.getConnection();PreparedStatement ps = connection.prepareStatement(sql)) {
            boolean success = ps.execute();
            System.out.println(success?"删除订单成功":"删除订单失败");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order get(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order order = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from order_ where id="+id;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                order = new Order();
                order.setId(id);
                order.setOrderCode(rs.getString("orderCode"));
                order.setAddress(rs.getString("address"));
                order.setPost(rs.getString("post"));
                order.setReceiver(rs.getString("receiver"));
                order.setMobile(rs.getString("mobile"));
                order.setUserMessage(rs.getString("userMessage"));
                order.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                order.setPayDate(DateUtil.t2d(rs.getTimestamp("payDate")));
                order.setDeliveryDate(DateUtil.t2d(rs.getTimestamp("deliveryDate")));
                order.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));

                User user = new UserDaoImpl().get(rs.getInt("uid"));
                order.setUser(user);
                order.setStatus(rs.getString("status"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<Order> list() {
        return list(0,Short.MAX_VALUE);
    }

    @Override
    public List<Order> list(int start, int count) {
        List<Order> orderList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();

            String sql = "select * from order_ order by id desc limit ?,?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,start);
            ps.setInt(2,count);

            rs = ps.executeQuery();
            while(rs.next()){
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setOrderCode(rs.getString("orderCode"));
                order.setAddress(rs.getString("address"));
                order.setPost(rs.getString("post"));
                order.setReceiver(rs.getString("receiver"));
                order.setMobile(rs.getString("mobile"));
                order.setUserMessage(rs.getString("userMessage"));
                order.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                order.setPayDate(DateUtil.t2d(rs.getTimestamp("payDate")));
                order.setDeliveryDate(DateUtil.t2d(rs.getTimestamp("deliveryDate")));
                order.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));

                User user = new UserDaoImpl().get(rs.getInt("uid"));
                order.setUser(user);
                order.setStatus(rs.getString("status"));

                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public List<Order> list(int uid, String excludeStatus) {
        return list(uid,excludeStatus,0,Short.MAX_VALUE);
    }

    @Override
    public List<Order> list(int uid, String excludeStatus, int start, int count) {
        List<Order> orderList = new ArrayList<>();
        String sql = "select * from order_ where uid=? and status=? order by id desc limit ?,?";
        try(Connection connection = DBUtil.getConnection();PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
            while(rs.next()){
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setOrderCode(rs.getString("orderCode"));
                order.setAddress(rs.getString("address"));
                order.setPost(rs.getString("post"));
                order.setReceiver(rs.getString("receiver"));
                order.setMobile(rs.getString("mobile"));
                order.setUserMessage(rs.getString("userMessage"));
                order.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                order.setPayDate(DateUtil.t2d(rs.getTimestamp("payDate")));
                order.setDeliveryDate(DateUtil.t2d(rs.getTimestamp("deliveryDate")));
                order.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));

                User user = new UserDaoImpl().get(rs.getInt("uid"));
                order.setUser(user);
                order.setStatus(rs.getString("status"));

                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }
}
