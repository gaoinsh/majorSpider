package com.spider.common.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by xiang.gao on 2017/5/16.
 * project majorSpider
 */
public class JdbcUtils {

    private static DataSource ds = new ComboPooledDataSource();

    private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

    public static DataSource getDataSource() {
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        /*
         * 如果有事务，返回当前事务的con
         * 如果没有事务，通过连接池返回新的con
         */
        Connection con = tl.get();//获取当前线程的事务连接
        if (con != null) return con;
        return ds.getConnection();
    }


    public static void beginTransaction() throws SQLException {
        Connection con = tl.get();//获取当前线程的事务连接
        if (con != null) throw new SQLException("已经开启了事务，不能重复开启！");
        con = ds.getConnection();//给con赋值，表示开启了事务
        con.setAutoCommit(false);//设置为手动提交
        tl.set(con);//把当前事务连接放到tl中
    }

    public static void commitTransaction() throws SQLException {
        Connection con = tl.get();//获取当前线程的事务连接
        if (con == null) throw new SQLException("没有事务不能提交！");
        con.commit();//提交事务
        con.close();//关闭连接
        con = null;//表示事务结束！
        tl.remove();
    }


    public static void rollbackTransaction() throws SQLException {
        Connection con = tl.get();//获取当前线程的事务连接
        if (con == null) throw new SQLException("没有事务不能回滚！");
        con.rollback();
        con.close();
        con = null;
        tl.remove();
    }

    public static void releaseConnection(Connection connection) throws SQLException {
        Connection con = tl.get();//获取当前线程的事务连接
        if (connection != con) {//如果参数连接，与当前事务连接不同，说明这个连接不是当前事务，可以关闭！
            if (connection != null && !connection.isClosed()) {//如果参数连接没有关闭，关闭之！
                connection.close();
            }
        }
    }
}
