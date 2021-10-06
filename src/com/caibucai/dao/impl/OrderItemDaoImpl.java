package com.caibucai.dao.impl;

import com.caibucai.bean.Order;
import com.caibucai.bean.OrderItem;
import com.caibucai.bean.Product;
import com.caibucai.dao.OrderItemDao;
import com.caibucai.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Csy
 * @Classname OrderItemDaoImpl
 * @date 2021/9/9 19:44
 * @Description TODO
 */
public class OrderItemDaoImpl implements OrderItemDao {
    @Override
    public int getTotal() {
        int total = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select count(*) from orderItem";
            ps = connection.prepareStatement(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, connection);
        }
        return total;
    }

    @Override
    public void add(OrderItem bean) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "insert into orderItem values(null,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bean.getProduct().getId());
            if (null == bean.getOrder()) {
                ps.setInt(2, -1);
            } else {
                ps.setInt(2, bean.getOrder().getId());
            }

            ps.setInt(3, bean.getUser().getId());
            ps.setInt(4, bean.getNumber());

            boolean success = ps.execute();
            System.out.println(success ? "添加成功" : "添加失败");
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bean.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, connection);
        }

    }

    @Override
    public void update(OrderItem bean) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "update orderItem set pid=?,oid=?,uid=?,number=? where id=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bean.getProduct().getId());

            if (null == bean.getOrder()) {
                ps.setInt(2, -1);
            } else {
                ps.setInt(2, bean.getOrder().getId());
            }
            ps.setInt(3, bean.getUser().getId());
            ps.setInt(4, bean.getNumber());
            boolean success = ps.execute();
            System.out.println(success ? "订单项更新成功" : "订单项更新失败");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, connection);
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "delete from orderItem where id=" + id;
            ps = connection.prepareStatement(sql);
            boolean success = ps.execute();
            System.out.println(success ? "删除订单项成功" : "删除订单项失败");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, connection);
        }
    }

    @Override
    public List<OrderItem> listByUser(int uid) {
        return listByUser(uid, 0, Short.MAX_VALUE);
    }

    @Override
    public List<OrderItem> listByUser(int uid, int start, int count) {
        List<OrderItem> orderItemList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from orderItem where oid = ? order by id desc limit ?,?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, start);
            ps.setInt(3, count);
            rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                int id = rs.getInt(1);
                int pid = rs.getInt("pid");
                int oid = rs.getInt("oid");
                int number = rs.getInt("number");

                orderItem.setProduct(new ProductDaoImpl().get(pid));
                if (-1 != oid) {
                    orderItem.setOrder(new OrderDaoImpl().get(oid));
                }

                orderItem.setUser(new UserDaoImpl().get(uid));
                orderItem.setNumber(number);
                orderItem.setId(id);

                orderItemList.add(orderItem);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, connection);
        }

        return orderItemList;
    }

    @Override
    public List<OrderItem> listByOrder(int oid) {
        return listByOrder(oid, 0, Short.MAX_VALUE);
    }

    @Override
    public List<OrderItem> listByOrder(int oid, int start, int count) {
        List<OrderItem> orderItemList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from orderItem where oid = ? order by id desc limit ?,?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, oid);
            ps.setInt(2, start);
            ps.setInt(3, count);

            rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                int id = rs.getInt("id");
                int pid = rs.getInt("pid");
                int uid = rs.getInt("uid");
                int numbers = rs.getInt("numbers");

                orderItem.setId(id);
                orderItem.setNumber(numbers);
                if (-1!=oid){
                    orderItem.setOrder(new OrderDaoImpl().get(oid));
                }
                orderItem.setUser(new UserDaoImpl().get(uid));
                orderItem.setProduct(new ProductDaoImpl().get(pid));

                orderItemList.add(orderItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }
        return orderItemList;
    }

    @Override
    public void fill(List<Order> os) {
        for (Order o : os) {
            List<OrderItem> ois = listByUser(o.getId());
            float total = 0;
            int totalNumber = 0;
            for(OrderItem orderItem:ois){
                total += orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
                totalNumber += orderItem.getNumber();
            }
            o.setTotal(total);
            o.setOrderItems(ois);
            o.setTotalNumber(totalNumber);
        }
    }

    @Override
    public void fill(Order o) {
        List<OrderItem> orderItemList = listByOrder(o.getId());
        float total = 0;
        for (OrderItem orderItem:orderItemList){
            total += orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
        }
        o.setTotal(total);
        o.setOrderItems(orderItemList);
    }

    @Override
    public List<OrderItem> listByProduct(int pid) {
        return listByProduct(pid,0,Short.MAX_VALUE);
    }

    @Override
    public List<OrderItem> listByProduct(int pid, int start, int count) {
        Connection connection = null;
        PreparedStatement ps = null;
        List<OrderItem> orderItemList = new ArrayList<>();
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from orderItem where pid= ? order by id desc limit ?,?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pid);
            ps.setInt(2,start);
            ps.setInt(3,count);

            rs = ps.executeQuery();
            while(rs.next()){
                OrderItem orderItem = new OrderItem();
                int id = rs.getInt("id");
                int oid = rs.getInt("oid");
                int uid = rs.getInt("uid");
                int numbers = rs.getInt("numbers");

                orderItem.setId(id);
                orderItem.setNumber(numbers);
                if (-1!=oid){
                    orderItem.setOrder(new OrderDaoImpl().get(oid));
                }

                orderItem.setUser(new UserDaoImpl().get(uid));
                orderItem.setProduct(new ProductDaoImpl().get(pid));

                orderItemList.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }

        return orderItemList;
    }

    @Override
    public int getSaleCount(int pid) {
        int total =0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select sum(number) from orderItem where pid="+pid;
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();
            if(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}
