package com.caibucai.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author Csy
 * @Classname DBUtil
 * @date 2021/9/8 19:28
 * @Description TODO
 */
public class DBUtil {

    private static Properties properties = new Properties();

    // 注册驱动
    static {

        try {
            // 加载配置文件
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");

            // 使用bundle 不能出现空指针异常
            // 将配置文件内容加载到对象中
            properties.load(in);

            Class.forName(properties.getProperty("jdbc.driver"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() throws SQLException {
        Connection connection = null;

            String jdbcUrl = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");

            // 得到数据库连接
            String url = String.format(jdbcUrl + "?characterEncoding=%s", "UTF-8");

//            System.out.println(url);

            connection = DriverManager.getConnection(url, username, password);


        return connection;
    }

    public static void close(ResultSet rs, PreparedStatement ps, Connection connection) {

        try {
            if (rs != null) {
                rs.close();
            }

            if (ps != null) {
                ps.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        System.out.println(getConnection());
    }

}
