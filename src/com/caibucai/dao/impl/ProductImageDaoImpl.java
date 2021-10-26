package com.caibucai.dao.impl;

import com.caibucai.bean.Product;
import com.caibucai.bean.ProductImage;
import com.caibucai.dao.ProductImageDao;
import com.caibucai.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Csy
 * @Classname ProductImageDaoImpl
 * @date 2021/9/9 19:43
 * @Description TODO
 */
public class ProductImageDaoImpl implements ProductImageDao {
    @Override
    public int getTotal() {
        int total = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select count(*) from ProductImage";
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
    public void add(ProductImage bean) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "insert into ProductImage values(null,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,bean.getProduct().getId());
            ps.setString(2,bean.getType());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(null,ps,connection);
        }
    }

    @Override
    public void update(ProductImage bean) {
    }
    @Override
    public void delete(int id) {

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "delete from ProductImage where id="+id;
            ps = connection.prepareStatement(sql);
            boolean success = ps.execute();
            System.out.println(success?"É¾³ýÍ¼Æ¬³É¹¦":"É¾³ýÍ¼Æ¬Ê§°Ü");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(null,ps,connection);
        }
    }

    @Override
    public ProductImage get(int id) {
        ProductImage bean = new ProductImage();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from ProductImage where id="+id;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                int pid = rs.getInt("pid");
                String type = rs.getString("type");
                Product product = new ProductDaoImpl().get(pid);
               bean.setId(id);
               bean.setType(type);
               bean.setProduct(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }
        return bean;
    }

    @Override
    public List<ProductImage> list(Product p, String type) {
        return list(p,type,0,Short.MAX_VALUE);
    }

    @Override
    public List<ProductImage> list(Product p, String type, int start, int count) {
        List<ProductImage> beans  = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from ProductImage where pid=? and type=? order by id desc limit ?,?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,p.getId());
            ps.setString(2,type);
            ps.setInt(3,start);
            ps.setInt(4,count);

            rs = ps.executeQuery();
            while(rs.next()){
                ProductImage image = new ProductImage();
                int id = rs.getInt(1);

                image.setProduct(p);
                image.setType(type);
                image.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }
        return beans;
    }
}
