package com.caibucai.dao.impl;

import com.caibucai.bean.Category;
import com.caibucai.bean.Product;
import com.caibucai.bean.ProductImage;
import com.caibucai.dao.ProductDao;
import com.caibucai.dao.ProductImageDao;
import com.caibucai.utils.DBUtil;
import com.caibucai.utils.DateUtil;
import org.apache.commons.lang.time.DateUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Csy
 * @Classname ProductDaoImpl
 * @date 2021/9/9 19:44
 * @Description TODO
 */
public class ProductDaoImpl implements ProductDao {
    @Override
    public int getTotal(int cid) {
        int total = 0;
        try (Connection connection = DBUtil.getConnection(); Statement s = connection.createStatement();) {
            String sql = "select count(*) from Product where cid = " + cid;
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public void add(Product bean) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "insert into Product values(null,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getSubTitle());
            ps.setDouble(3, bean.getOriginalPrice());
            ps.setDouble(4, bean.getPromotePrice());
            ps.setInt(5, bean.getStock());
            ps.setInt(6, bean.getCategory().getId());
            ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));

            boolean success = ps.execute();
            System.out.println(success ? "添加商品成功" : "添加商品失败");

            // 获取id回传
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, connection);
        }
    }

    @Override
    public void update(Product bean) {
        String sql = "update Product set name = ?,subTitle=?,originPrice=?,promotePrice=?,stock=?,cid=?,createDate=? where id=?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getSubTitle());
            ps.setDouble(3, bean.getOriginalPrice());
            ps.setDouble(4, bean.getPromotePrice());
            ps.setInt(5, bean.getStock());
            ps.setInt(6, bean.getCategory().getId());
            ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
            ps.setInt(8, bean.getId());

            boolean success = ps.execute();
            System.out.println(success ? "商品信息更新成功" : "商品信息更新失败");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DBUtil.getConnection(); Statement ps = connection.createStatement()) {
            String sql = "delete from Product where id=" + id;
            boolean success = ps.execute(sql);

            System.out.println(success ? "删除商品成功" : "删除商品失败");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product get(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Product product = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from Product where id = " + id;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                product = new Product();
                String name = rs.getString("name");
                String subTitle = rs.getString("subTitle");
                double originalPrice = rs.getDouble("originalPrice");
                double promotePrice = rs.getDouble("promotePrice");
                int stock = rs.getInt("stock");
                int cid = rs.getInt("cid");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

                product.setName(name);
                product.setSubTitle(subTitle);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setStock(stock);
                Category category = new CategoryDaoImpl().get(cid);
                product.setCategory(category);
                product.setCreateDate(createDate);
                product.setId(id);
                setFirstProductImage(product);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> list(int cid) {
        return list(cid, 0, Short.MAX_VALUE);
    }

    @Override
    public List<Product> list(int cid, int start, int count) {
        List<Product> products = new ArrayList<>();
        Category category = new CategoryDaoImpl().get(cid);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from Product where cid = ? order by id desc limit ?,?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setInt(2, start);
            ps.setInt(3, count);

            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                int id = rs.getInt(1);
                String name = rs.getString("name");
                String subTitle = rs.getString("subTitle");
                double originalPrice = rs.getDouble("originalPrice");
                double promotePrice = rs.getDouble("promotePrice");
                int stock = rs.getInt("stock");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

                product.setName(name);
                product.setSubTitle(subTitle);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setStock(stock);
                product.setCreateDate(createDate);
                product.setId(id);

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> list() {
        return list(0,Short.MAX_VALUE);
    }

    @Override
    public List<Product> list(int start, int count) {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from Product limit ?,?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, start);
            ps.setInt(2, count);

            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                int id = rs.getInt(1);
                String name = rs.getString("name");
                String subTitle = rs.getString("subTitle");
                double originalPrice = rs.getDouble("originalPrice");
                double promotePrice = rs.getDouble("promotePrice");
                int stock = rs.getInt("stock");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

                product.setName(name);
                product.setSubTitle(subTitle);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setStock(stock);
                product.setCreateDate(createDate);
                product.setId(id);

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public void fill(List<Category> categories) {
        for (Category category : categories) {
            fill(category);
        }
    }

    @Override
    public void fill(Category c) {
        List<Product> products = this.list(c.getId());
        c.setProducts(products);
    }

    @Override
    public void fillByRow(List<Category> categories) {
        int productNumberEachRow = 8;
        for (Category category : categories) {
            List<Product> products = category.getProducts();
            List<List<Product>> productByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i+= productNumberEachRow) {
                int size = i +productNumberEachRow;
                size = size > products.size() ? products.size():size;
                List<Product> productsOfEachRow = products.subList(i,size);
                productByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productByRow);
        }

    }

    @Override
    public void setFirstProductImage(Product p) {
        List<ProductImage>  pis = new ProductImageDaoImpl().list(p, ProductImageDao.type_single);
        if(!pis.isEmpty()){
            p.setFirstProductImage(pis.get(0));
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product p) {
        int saleCount = new OrderItemDaoImpl().getSaleCount(p.getId());
        p.setSaleCount(saleCount);

        int reviewCount = new ReviewDaoImpl().getCount(p.getId());
        p.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products) {
            setSaleAndReviewNumber(product);
        }
    }

    @Override
    public List<Product> search(String keyword, int start, int count) {
        List<Product> beans = new ArrayList<>();

        if(null == keyword || 0==keyword.trim().length()){
            return beans;
        }

        String sql = "select * from Product where name like ? limit ?,?";

        try(Connection connection = DBUtil.getConnection();PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,"%"+keyword.trim()+"%");
            ps.setInt(2,start);
            ps.setInt(3,count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product bean = new Product();
                int id = rs.getInt(1);
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                String subTitle = rs.getString("subTitle");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int stock = rs.getInt("stock");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOriginalPrice(originalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCreateDate(createDate);
                bean.setId(id);

                Category category = new CategoryDaoImpl().get(cid);
                bean.setCategory(category);
                setFirstProductImage(bean);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return beans;
    }
}
