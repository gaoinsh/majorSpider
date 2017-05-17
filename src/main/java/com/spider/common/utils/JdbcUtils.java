package com.spider.common.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by xiang.gao on 2017/5/16.
 * project majorSpider
 */
public class JdbcUtils {

    public static long insertGenerateKey(String sql, Object... objects) {
        long id = -1;
        QueryRunner qr = new QueryRunner(DbFactory.getDataSource());
        Map<String, Object> map = null;
        try {
            map = qr.insert(sql, new MapHandler(), objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (map != null && !map.isEmpty()) {
            Object obj = map.get("GENERATED_KEY");
            id = obj != null ? Long.parseLong(obj.toString()) : -1;
        }
        return id;
    }

    public static Map<String, Object> getMap(String sql, Object... objects) {
        QueryRunner qr = new QueryRunner(DbFactory.getDataSource());
        Map<String, Object> map = null;
        try {
            map = qr.query(sql, new MapHandler(), objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void update(String sql, Object... objects) {
        QueryRunner qr = new QueryRunner(DbFactory.getDataSource());
        try {
            qr.update(sql, objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getObject(String sql, Class<T> clazz, Object... objects) {
        QueryRunner qr = new QueryRunner(DbFactory.getDataSource());
        try {
            return qr.query(sql, new BeanHandler<T>(clazz), objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> getObjects(String sql, Class clazz, Object... objects) {
        QueryRunner qr = new QueryRunner(DbFactory.getDataSource());
        List<T> query = null;
        try {
            query = qr.query(sql, new BeanListHandler<T>(clazz), objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }
}
