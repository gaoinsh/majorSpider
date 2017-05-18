package com.spider.common.dao;


import com.spider.common.utils.JdbcUtils;
import com.spider.common.utils.MD5Utils;
import org.apache.commons.lang.StringUtils;

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
        String sql = "insert into tb_picture (pic_link,link_md5,image_status,createtime,updatetime) values (?,?,0,UNIX_TIMESTAMP(),UNIX_TIMESTAMP())";
        return JdbcUtils.insertGenerateKey(sql, url, md5);
    }

    private long getPicIdByMd5(String md5) {
        long id = -1;
        String sql = "select id from tb_picture where link_md5=?";
        Map<String, Object> map = JdbcUtils.getMap(sql, md5);
        return (map != null && map.get("id") != null) ? Long.parseLong(map.get("id").toString()) : id;
    }

    public static void main(String[] args) {
        long picId = new PictureDao().getPicId("http://img2.ad.agrantsem.com//18084/modified_1ebddd43e4b3da22a705e31e8d428f45.jpg");
        System.out.println(picId);
    }
}
