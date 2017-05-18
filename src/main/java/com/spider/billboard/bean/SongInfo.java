package com.spider.billboard.bean;

/**
 * Created by xiang.gao on 2017/5/18.
 * project majorSpider
 */
public class SongInfo {

    private long id;
    private long boardId;
    private String songName;
    private String artist;
    private Integer rank;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    @Override
    public String toString() {
        return "SongInfo{" +
                "songName='" + songName + '\'' +
                ", artist='" + artist + '\'' +
                ", rank=" + rank +
                '}';
    }
}
