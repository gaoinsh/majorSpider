package com.spider.billboard.dao;

import com.spider.billboard.bean.SongInfo;
import com.spider.common.utils.JdbcUtils;

/**
 * Created by xiang.gao on 2017/5/18.
 * project majorSpider
 */
public class SongDao {

    public void saveAndUpdate(SongInfo song) {
        String sql = "insert into tb_song (board_id,artist,song_name,rank,createtime,updatetime) values " +
                "(?,?,?,?,UNIX_TIMESTAMP(),UNIX_TIMESTAMP()) ON DUPLICATE KEY UPDATE updatetime = UNIX_TIMESTAMP()";
        JdbcUtils.update(sql, song.getBoardId(), song.getArtist(), song.getSongName(), song.getRank());
    }


}
