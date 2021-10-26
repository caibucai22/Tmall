package com.caibucai.dao.impl;

import com.caibucai.bean.User;
import com.caibucai.dao.UserDao;
import com.caibucai.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Csy
 * @Classname UserDaoImpl
 * @date 2021/9/9 9:05
 * @Description TODO
 */
public class UserDaoImpl implements UserDao {

    @Override
    public User get(String name) {
        User loginUser = null;

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select password from user where username = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            resultSet = ps.executeQuery();
            if (resultSet != null) {
                if (resultSet.next()) {
                    loginUser = new User();
                    int id = resultSet.getInt("id");
                    String password = resultSet.getString("password");
                    loginUser.setName(name);
                    loginUser.setPassword(password);
                    loginUser.setId(id);
                }
            } else {
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(resultSet, ps, connection);
        }
        return loginUser;
    }

    @Override
    public User get(String name, String password) {
        User loginUser = null;
        String sql = "select * from User where name = ? and password = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                loginUser = new User();
                int id = rs.getInt("id");
                loginUser.setId(id);
                loginUser.setName(name);
                loginUser.setPassword(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loginUser;
    }

    @Override
    public int getToatal() {
        int total = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select count(*) from User";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                // 返回的结果是 数值
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
    public void add(User bean) {
        String sql = "insert into User values(null,?,?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getPassword());

            ps.execute();
            /*
                 通常我们在应用中对mysql执行了insert操作后，需要获取插入记录的自增主键，
                 这时候通常用getGeneratedKeys()方法获取主键
             */
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User bean) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "update user set name=?,password=? where id=?";
            ps = connection.prepareStatement(sql);
            boolean success = ps.execute();

            // 源码中未给出判断是否成功
            System.out.println(success?"更新成功":"更新失败");

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
            String sql = "delete from User where id=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            boolean success = ps.execute();
            System.out.println(success?"删除成功":"删除失败");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(null,ps,connection);
        }
    }

    @Override
    public User get(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from User where id=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(id);
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }
        return user;
    }

    @Override
    public List<User> list() {
        return list(0,Short.MAX_VALUE);
    }

    @Override
    public List<User> list(int start, int count) {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from User order by id desc limit ?,?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,start);
            ps.setInt(2,count);

            rs = ps.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }

        return users;
    }

    @Override
    public boolean isExist(String name) {

        return get(name) != null;
    }
}
