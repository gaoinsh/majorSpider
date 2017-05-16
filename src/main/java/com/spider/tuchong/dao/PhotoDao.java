package com.spider.tuchong.dao;

import com.spider.common.utils.JdbcUtils;
import com.spider.tuchong.bean.Photo;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

/**
 * Created by xiang.gao on 2017/5/16.
 * project majorSpider
 */
public class PhotoDao {

    public void save(Photo photo) {
        QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "insert into tb_photo (source_id,pic_id,tag,createtime,updatetime) values (?,?,?,UNIX_TIMESTAMP(),UNIX_TIMESTAMP())";
        try {
            queryRunner.update(sql, photo.getSourceId(), photo.getPicId(), photo.getTag());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
