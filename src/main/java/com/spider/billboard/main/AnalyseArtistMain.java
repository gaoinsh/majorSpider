package com.spider.billboard.main;

import com.spider.billboard.bean.ArtistSong;
import com.spider.billboard.bean.SongInfo;
import com.spider.billboard.dao.SongDao;
import com.spider.common.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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
    private static Logger logger = Logger.getLogger(AnalyseArtistMain.class);
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
                    String rs = artist.replaceAll("(\\s+([fF]eat\\.|[fF]eaturing|&|x|/|,|[aA]nd|[wW]ith)\\s+|,)", " Featuring ");
                    artists.addAll(Arrays.asList(rs.split("Featuring")));
                    for (String name : artists) {
                        ArtistSong as = new ArtistSong();
                        as.setName(name.trim());
                        as.setSongId(songId);
                        songDao.saveArtist(as);
                    }
                }

            }
        }, producerThread);

        new Thread(consumerEnd).start();

    }


}
