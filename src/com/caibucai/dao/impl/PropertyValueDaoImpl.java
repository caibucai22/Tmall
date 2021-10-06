package com.caibucai.dao.impl;

import com.caibucai.bean.Product;
import com.caibucai.bean.Property;
import com.caibucai.bean.PropertyValue;
import com.caibucai.dao.PropertyValueDao;
import com.caibucai.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Csy
 * @Classname PropertyValueDaoImpl
 * @date 2021/9/9 19:43
 * @Description TODO
 */
public class PropertyValueDaoImpl implements PropertyValueDao {
    @Override
    public int getTotal() {
        int total = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select count(*) from propertyvalue";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
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
    public void add(PropertyValue bean) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "insert into propertyvalue values(null,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bean.getProduct().getId());
            ps.setInt(2, bean.getProperty().getId());
            boolean success = ps.execute();

            System.out.println(success ? "添加属性值成功" : "添加属性值失败");

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bean.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, connection);
        }
    }

    @Override
    public void update(PropertyValue bean) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "update  propertyvalue set pid=?,ptid=?,value=? where id=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bean.getProduct().getId());
            ps.setInt(2, bean.getProperty().getId());
            ps.setString(3, bean.getValue());
            ps.setInt(4, bean.getId());
            boolean success = ps.execute();

            System.out.println(success ? "更新属性值成功" : "更新属性值失败");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, connection);
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "delete from propertyvalue where id="+id;
            ps = connection.prepareStatement(sql);
            boolean success = ps.execute();

            System.out.println(success ? "删除属性值成功" : "删除属性值失败");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, connection);
        }
    }

    @Override
    public PropertyValue get(int id) {
        PropertyValue propertyValue =null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from propertyvalue where id="+id;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                propertyValue = new PropertyValue();
                propertyValue.setId(id);
                int ptid = rs.getInt("ptid");
                int pid = rs.getInt("pid");
                propertyValue.setProduct(new ProductDaoImpl().get(pid));
                propertyValue.setProperty(new PropertyDaoImpl().get(ptid));
                propertyValue.setValue(rs.getString("value"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, connection);
        }
        return propertyValue;
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValue propertyValue =null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from propertyvalue where ptid = ? and pid = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,ptid);
            ps.setInt(2,pid);
            rs = ps.executeQuery();
            while(rs.next()){
                propertyValue = new PropertyValue();
                propertyValue.setId(rs.getInt("id"));
                propertyValue.setProperty(new PropertyDaoImpl().get(ptid));
                propertyValue.setProduct(new ProductDaoImpl().get(pid));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, connection);
        }
        return propertyValue;
    }

    @Override
    public List<PropertyValue> list() {
        return list(0,Short.MAX_VALUE);
    }

    @Override
    public List<PropertyValue> list(int start, int count) {
        List<PropertyValue> propertyValueList=new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from propertyvalue order by id desc limit ?,?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,start);
            ps.setInt(2,count);
            rs = ps.executeQuery();
            while(rs.next()){
                PropertyValue propertyValue = new PropertyValue();
                propertyValue.setId(rs.getInt("id"));
                int ptid = rs.getInt("ptid");
                int pid = rs.getInt("pid");
                propertyValue.setProperty(new PropertyDaoImpl().get(ptid));
                propertyValue.setProduct(new ProductDaoImpl().get(pid));
                propertyValue.setValue(rs.getString("value"));

                propertyValueList.add(propertyValue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, connection);
        }
        return propertyValueList;
    }

    @Override
    public void init(Product p) {
        List<Property> propertyList = new PropertyDaoImpl().list(p.getCategory().getId());

        for (Property property : propertyList) {
            PropertyValue propertyValue = get(property.getId(),p.getId());
            if(null == propertyValue){
                propertyValue = new PropertyValue();
                propertyValue.setProduct(p);
                propertyValue.setProperty(property);
                this.add(propertyValue);
            }
        }
    }

    @Override
    public List<PropertyValue> list(int pid) {
        List<PropertyValue> propertyValueList=new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from propertyvalue where pid=? order by ptid desc";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pid);
            rs = ps.executeQuery();
            while(rs.next()){
                PropertyValue propertyValue = new PropertyValue();
                propertyValue.setId(rs.getInt("id"));
                int ptid = rs.getInt("ptid");
                propertyValue.setProperty(new PropertyDaoImpl().get(ptid));
                propertyValue.setProduct(new ProductDaoImpl().get(pid));
                propertyValue.setValue(rs.getString("value"));

                propertyValueList.add(propertyValue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, connection);
        }
        return propertyValueList;
    }
}
