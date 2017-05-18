package com.spider.billboard.bean;

/**
 * Created by xiang.gao on 2017/5/18.
 * project majorSpider
 */
public class ArtistSong {

    private long id;
    private String name;
    private long songId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }
}
