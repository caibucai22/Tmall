package com.caibucai.dao.impl;

import com.caibucai.bean.Category;
import com.caibucai.dao.CategoryDao;
import com.caibucai.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Csy
 * @Classname CategoryDaoImpl
 * @date 2021/9/9 19:44
 * @Description TODO
 */
public class CategoryDaoImpl implements CategoryDao {
    @Override
    public int getTotal() {
        int total = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select count(*) from Category";
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

    @Override
    public void add(Category bean) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "insert into category values(null,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1,bean.getName());
            boolean success = ps.execute();
            System.out.println(success?"类型添加成功":"类型添加失败");
            rs = ps.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }

    }

    @Override
    public void update(Category bean) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "update category set name=? where id =?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,bean.getName());
            ps.setInt(2,bean.getId());
            boolean success = ps.execute();
            System.out.println(success?"更新类型成功":"更新类型失败");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(null,ps,connection);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from category where id ="+id;
        try(Connection connection = DBUtil.getConnection();PreparedStatement ps = connection.prepareStatement(sql)) {
            boolean success = ps.execute();
            System.out.println(success?"删除类型成功":"删除类型失败");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category get(int id) {
        Category category = null;
        String sql = "select * from category where id="+id;
        try(Connection connection = DBUtil.getConnection();PreparedStatement ps = connection.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                category = new Category();
                category.setId(rs.getInt(1));
                category.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public List<Category> list() {
        return list(0,Short.MAX_VALUE);
    }

    @Override
    public List<Category> list(int start, int count) {
        List<Category> categoryList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from category order by id desc limit ?,?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,start);
            ps.setInt(2,count);
            rs = ps.executeQuery();
            while(rs.next()){
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));

                categoryList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryList;
    }
}
