package com.caibucai.dao.impl;

import com.caibucai.bean.Property;
import com.caibucai.dao.PropertyDao;
import com.caibucai.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Csy
 * @Classname PropertyDaoImpl
 * @date 2021/9/9 19:43
 * @Description TODO
 */
public class PropertyDaoImpl implements PropertyDao {
    @Override
    public int getTotal(int cid) {
        int total = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select count(*) from property where cid = "+cid;
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
    public void add(Property bean) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection  = DBUtil.getConnection();
            String sql = "insert into property values(null,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,bean.getCategory().getId());
            ps.setString(2,bean.getName());

            boolean success = ps.execute();
            if(success){
                System.out.println("添加属性成功");
                rs = ps.getGeneratedKeys();
                bean.setId(rs.getInt(1));
            }else{
                System.out.println("添加属性失败");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }
    }

    @Override
    public void update(Property bean) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "update property set cid=?,name=? where id=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,bean.getCategory().getId());
            ps.setString(2,bean.getName());
            ps.setInt(3,bean.getId());

            boolean success = ps.execute();
            System.out.println(success?"更新属性成功":"更新属性失败");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(null,ps,connection);
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "delete from property where id ="+id;
            ps = connection.prepareStatement(sql);
            boolean success = ps.execute();
            System.out.println(success?"删除属性成功":"删除属性失败");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(null,ps,connection);
        }
    }

    @Override
    public Property get(String name, int cid) {
        Property property = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from property where name = ? and cid = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,name);
            ps.setInt(2,cid);
            rs = ps.executeQuery();
            if(rs.next()){
                property = new Property();
                property.setId(rs.getInt(1));
                property.setCategory(new CategoryDaoImpl().get(cid));
                property.setName(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }

        return property;
    }

    @Override
    public Property get(int id) {
        Property property = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from property where id ="+id;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                property = new Property();
                property.setId(id);
                int cid = rs.getInt("cid");
                property.setCategory(new CategoryDaoImpl().get(cid));
                String name = rs.getString("name");
                property.setName(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }
        return property;
    }

    @Override
    public List<Property> list(int cid) {
        List<Property> propertyList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from property where cid ="+cid;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Property property = new Property();
                int id = rs.getInt(1);
                String name = rs.getString("name");
                property.setId(id);
                property.setName(name);
                property.setCategory(new CategoryDaoImpl().get(cid));

                propertyList.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }

        return propertyList;
    }

    @Override
    public List<Property> list(int cid, int start, int count) {
        List<Property> propertyList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from property where cid = ? order by id desc limit ?,?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,cid);
            ps.setInt(2,start);
            ps.setInt(3,count);

            rs = ps.executeQuery();
            while(rs.next()){
                Property property = new Property();
                int id = rs.getInt(1);
                String name = rs.getString("name");
                property.setId(id);
                property.setName(name);
                property.setCategory(new CategoryDaoImpl().get(cid));

                propertyList.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }

        return propertyList;
    }
}
