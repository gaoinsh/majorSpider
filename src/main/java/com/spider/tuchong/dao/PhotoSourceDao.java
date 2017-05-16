package com.spider.tuchong.dao;

import com.spider.common.utils.JdbcUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by xiang.gao on 2017/5/16.
 * project majorSpider
 */
public class PhotoSourceDao {

    public long getSourceId(String name) {
        long id = -1;
        if (StringUtils.isEmpty(name)) {
            return id;
        }
        id = getIdByName(name);
        if (id < 0) {
            id = save(name);
        }
        return id;
    }

    private long save(String name) {
        String sql = "insert into tb_photo_source (source_name,createtime,updatetime) values(?,UNIX_TIMESTAMP(),UNIX_TIMESTAMP())";
        return JdbcUtils.insertGenerateKey(sql, name);
    }

    private long getIdByName(String name) {
        String sql = "select id from tb_photo_source where source_name = ?";
        Map<String, Object> map = JdbcUtils.getMap(sql, name);
        return (map != null && map.get("id") != null) ? Long.parseLong(map.get("id").toString()) : -1;
    }

    public static void main(String[] args) {
        long sourceId = new PhotoSourceDao().getSourceId("图虫");
        System.out.println(sourceId);

    }
}
