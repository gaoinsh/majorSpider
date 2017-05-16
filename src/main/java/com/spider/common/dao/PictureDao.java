package com.spider.common.dao;


import com.spider.common.utils.JdbcUtils;
import com.spider.common.utils.MD5Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by xiang.gao on 2017/5/16.
 * project majorSpider
 */
public class PictureDao {

    public long getPicId(String url) {
        long picId = -1;
        if (StringUtils.isEmpty(url)) {
            return picId;
        }
        String md5 = MD5Utils.md5(url);
        if (StringUtils.isNotEmpty(md5)) {
            picId = getPicIdByMd5(md5);
        }
        if (picId < 0) {
            picId = savePic(url, md5);
        }
        return picId;
    }

    private long savePic(String url, String md5) {
        long id = -1;
        String sql = "insert into tb_picture (pic_link,link_md5,image_status,createtime,updatetime) values (?,?,0,UNIX_TIMESTAMP(),UNIX_TIMESTAMP())";
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        Map<String, Object> map = null;
        try {
            map = qr.insert(sql, new MapHandler(), url, md5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (map != null && !map.isEmpty()) {
            Object obj = map.get("GENERATED_KEY");
            id = obj != null ? Long.parseLong(obj.toString()) : -1;
        }
        return id;
    }

    private long getPicIdByMd5(String md5) {
        long id = -1;
        String sql = "select id from tb_picture where link_md5=?";
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        Map<String, Object> map = null;
        try {
            map = qr.query(sql, new MapHandler(), md5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (map != null && !map.isEmpty()) {
            Object obj = map.get("id");
            id = obj != null ? Long.parseLong(obj.toString()) : -1;
        }
        return id;
    }

    public static void main(String[] args) {
        long picId = new PictureDao().getPicId("http://img2.ad.agrantsem.com//18084/modified_1ebfdf43e4b3da22a705e31e8d428f45.jpg");
        System.out.println(picId);
    }
}
