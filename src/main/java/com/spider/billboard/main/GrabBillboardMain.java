package com.spider.billboard.main;

import com.spider.billboard.bean.Billboard;
import com.spider.billboard.bean.SongInfo;
import com.spider.billboard.dao.BillboardDao;
import com.spider.billboard.dao.SongDao;
import com.spider.common.utils.DateUtils;
import com.spider.common.utils.RegexFilter;
import com.spider.common.utils.StringExtUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiang.gao on 2017/5/18.
 * project majorSpider
 */
public class GrabBillboardMain {
    private static Logger logger = Logger.getLogger(GrabBillboardMain.class);
    private static BillboardDao billboardDao = new BillboardDao();
    private static SongDao songDao = new SongDao();

    public static void main(String[] args) {

        Spider.create(new BillboardPageProcessor()).addPipeline(new DbPipeline()).thread(1).addUrl("http://www.billboard.com/charts/hot-100").start();

    }

    private static class BillboardPageProcessor implements PageProcessor {

        public void process(Page page) {
            String currPage = page.getUrl().get();
            String content = page.getRawText();
            List<String> list = new ArrayList<String>();
            String previous = RegexFilter.regexFilter(content, "<a[^>]*?href=\"([^\"]*)\"[^>]*?title=\"Previous Week\"");
            String next = RegexFilter.regexFilter(content, "<a[^>]*?href=\"([^\"]*)\"[^>]*?title=\"Next Week\"");
            if (StringUtils.isNotEmpty(previous)) {
                previous = UrlUtils.canonicalizeUrl(previous, currPage);
                list.add(previous);
            }
            if (StringUtils.isNotEmpty(next) && !next.equals(currPage)) {
                next = UrlUtils.canonicalizeUrl(next, currPage);
                list.add(next);
            }


            if (!list.isEmpty()) {
                page.addTargetRequests(list);
            }
            String time = page.getUrl().regex("(\\d{4}-\\d{2}-\\d{2})", 1).get();
            if (StringUtils.isNotEmpty(time)) {
                page.putField("time", time);
            } else {
                time = RegexFilter.regexFilter(content, "datetime=\"([^\"]*)\"");
                page.putField("time", time);
            }

            List<String> articles = RegexFilter.regexFilterList(content, "(<h\\d+[^>]*?class=\"chart-row__song\"[^>]*>[^<]*</h\\d+>\\s*<[ah][^>]*?[Aa]rtist[^>]*?>[^<]*</)");

            if (articles != null && !articles.isEmpty()) {
                int rank = 1;
                List<SongInfo> songInfos = new ArrayList<SongInfo>();
                for (String article : articles) {
                    String name = RegexFilter.regexFilter(article, "<h\\d+[^>]*?class=\"chart-row__song\"[^>]*>([^<]*)<");
                    String artist = RegexFilter.regexFilter(article, "</h\\d+>\\s*<[ah][^>]*?[Aa]rtist[^>]*?>([^<]*)</");
                    if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(artist)) {
                        SongInfo songInfo = new SongInfo();
                        songInfo.setArtist(StringExtUtils.unescape(artist));
                        songInfo.setRank(rank++);
                        songInfo.setSongName(StringExtUtils.unescape(name));
                        songInfos.add(songInfo);
                    } else {
                        System.out.println(article);
                    }
                }
                page.putField("data", songInfos);
            }

        }

        public Site getSite() {
            return Site.me().setCharset("utf-8").setRetryTimes(3);

        }
    }

    private static class DbPipeline implements Pipeline {

        public void process(ResultItems resultItems, Task task) {
            Object data = resultItems.get("data");
            Object time = resultItems.get("time");
            if (time != null && data != null && data instanceof List) {
                List<SongInfo> songInfos = (List<SongInfo>) data;
                Billboard billboard = new Billboard();
                billboard.setTime(DateUtils.getDate(time.toString(), "yyyy-MM-dd"));
                long billboardId = billboardDao.save(billboard);
                if (billboardId > 0) {
                    logger.info(" ------------------" + time + "------------------");
                    for (SongInfo song : songInfos) {
                        song.setBoardId(billboardId);
                        logger.info(" rank : " + song.getRank() + " ," + song.getSongName() + " by --- " + song.getArtist());
                        songDao.saveAndUpdate(song);
                    }
                }

            }
        }
    }
}
