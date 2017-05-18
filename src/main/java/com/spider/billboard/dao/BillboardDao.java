package com.spider.billboard.dao;

import com.spider.billboard.bean.Billboard;
import com.spider.common.utils.JdbcUtils;

/**
 * Created by xiang.gao on 2017/5/18.
 * project majorSpider
 */
public class BillboardDao {

    public long save(Billboard billboard) {
        String sql = "insert into tb_billboard (time,createtime,updatetime) values(?,UNIX_TIMESTAMP(),UNIX_TIMESTAMP())  ON DUPLICATE KEY UPDATE updatetime = UNIX_TIMESTAMP()";
        return JdbcUtils.insertGenerateKey(sql, billboard.getTime());
    }
}
