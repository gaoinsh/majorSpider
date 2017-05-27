package com.spider.billboard.main;

import com.spider.billboard.bean.ArtistSong;
import com.spider.billboard.bean.SongInfo;
import com.spider.billboard.dao.SongDao;
import com.spider.common.utils.*;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by xiang.gao on 2017/5/18.
 * project majorSpider
 */
public class AnalyseArtistMain {
    private static final int threadNum = 4;
    private static SongDao songDao = new SongDao();

    public static void main(String[] args) {
        BlockingQueue<SongInfo> queue = new LinkedBlockingQueue<SongInfo>(1000);
        ItemProcessor<SongInfo> processor = new SingleItemProcessor<SongInfo>(queue);
        String sql = "select id ,artist from tb_song where id >? order by id limit ?";
        DbDataProducer<SongInfo> producer = new DbDataProducer<SongInfo>(sql, processor, "id", 1, SongInfo.class, 0, 1000);
        Thread producerThread = new Thread(producer);
        producerThread.start();

        DataConsumerEnd<SongInfo> consumerEnd = new DataConsumerEnd<SongInfo>(queue, new DataProcessorService<SongInfo>() {
            public void doInProcess(SongInfo o) {
                String artist = o.getArtist();
                long songId = o.getId();
                if (StringUtils.isNotEmpty(artist)) {
                    List<String> artists = new ArrayList<String>();
                    String rs = replaceStr(artist);
                    artists.addAll(Arrays.asList(rs.split("Featuring")));
                    for (String name : artists) {
                        if (StringUtils.isNotEmpty(name.trim())) {
                            ArtistSong as = new ArtistSong();
                            as.setName(name.trim());
                            as.setSongId(songId);
                            songDao.saveArtist(as);
                        }
                    }
                }

            }
        }, producerThread);
        for (int i = 0; i < threadNum; i++) {
            new Thread(consumerEnd).start();
        }

    }

    public static String replaceStr(String str) {
        return str.replace(")", "").replaceAll("(\\s+([fF]eat\\.|[fF]eat[uring]{5}|[Cc]o-[sS]tarring|&|[Xx]|\\+|/|[oO][rR]|,|[aA]nd|AND|(?:[dD]uet\\s*)?[wW]ith|WITH|[Vv][Ss])\\s+|,|/|\\()", " Featuring ");
    }


}
