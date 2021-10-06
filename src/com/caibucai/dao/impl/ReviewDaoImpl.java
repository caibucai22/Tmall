package com.caibucai.dao.impl;

import com.caibucai.bean.Review;
import com.caibucai.dao.ReviewDao;
import com.caibucai.utils.DBUtil;
import com.caibucai.utils.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Csy
 * @Classname ReviewDaoImpl
 * @date 2021/9/9 19:43
 * @Description TODO
 */
public class ReviewDaoImpl implements ReviewDao {
    @Override
    public int getTotal() {
        int total = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select count(*) from review";
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
    public int getTotal(int pid) {
        int total = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select count(*) from review where pid = "+pid;
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
    public void add(Review bean) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "insert into review values(null,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1,bean.getContent());
            ps.setInt(2,bean.getUser().getId());
            ps.setInt(3,bean.getProduct().getId());
            ps.setTimestamp(4,DateUtil.d2t(bean.getCreateDate()));

            boolean success = ps.execute();
            if(success){
                rs = ps.getGeneratedKeys();
                bean.setId(rs.getInt(1));
            }
            System.out.println(success?"添加评论成功":"添加评论失败");

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }
    }

    @Override
    public void update(Review bean) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "update review set content=?,uid=?,pid=?,createDate=? where id=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,bean.getContent());
            ps.setInt(2,bean.getUser().getId());
            ps.setInt(3,bean.getProduct().getId());
            ps.setTimestamp(4,DateUtil.d2t(bean.getCreateDate()));
            ps.setInt(5,bean.getId());

            boolean success = ps.execute();

            System.out.println(success?"更新评论成功":"更新评论失败");

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
            String sql = "delete from review where id="+id;
            ps = connection.prepareStatement(sql);

            boolean success = ps.execute();

            System.out.println(success?"删除评论成功":"删除评论失败");

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(null,ps,connection);
        }
    }

    @Override
    public Review get(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Review review = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from review where id=?";
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();
            if(rs.next()){
                review = new Review();
                review.setId(rs.getInt(1));
                review.setContent(rs.getString("content"));
                int uid = rs.getInt("uid");
                int pid = rs.getInt("pid");
                review.setUser(new UserDaoImpl().get(uid));
                review.setProduct(new ProductDaoImpl().get(pid));
                Date date = DateUtil.t2d(rs.getTimestamp("createDate"));
                review.setCreateDate(date);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(null,ps,connection);
        }
        return review;
    }

    @Override
    public List<Review> list(int pid) {
        return list(pid,0,Short.MAX_VALUE);
    }

    @Override
    public int getCount(int pid) {
        int count = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select count(*) from review where pid = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pid);
            rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }
        return count;
    }

    @Override
    public List<Review> list(int pid, int start, int count) {
        List<Review> reviewList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from review where pid = ? order by id desc limit ?,?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pid);
            ps.setInt(2,start);
            ps.setInt(3,count);

            rs = ps.executeQuery();

            while(rs.next()){
                Review review = new Review();
                review.setId(rs.getInt(1));
                review.setContent(rs.getString("content"));
                int uid = rs.getInt("uid");
                review.setUser(new UserDaoImpl().get(uid));
                review.setProduct(new ProductDaoImpl().get(pid));
                Date date = DateUtil.t2d(rs.getTimestamp("createDate"));
                review.setCreateDate(date);

                reviewList.add(review);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,ps,connection);
        }
        return reviewList;
    }

    @Override
    public boolean isExist(String content, int pid) {

        String sql = "select * from review where content = ? and pid = ?";

        try(Connection connection = DBUtil.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,content);
            ps.setInt(2,pid);

            ResultSet rs  = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
