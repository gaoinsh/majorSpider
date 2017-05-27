package com.spider.billboard.test;

import com.spider.billboard.main.AnalyseArtistMain;
import org.junit.Test;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xiang.gao on 2017/5/19.
 * project majorSpider
 */
public class Gaotest {


    @Test
    public void test() {
        String artist = "Kungs vs Cookin' On 3 Burners";
        List<String> artists = new ArrayList<String>();
        String rs = AnalyseArtistMain.replaceStr(artist);
        artists.addAll(Arrays.asList(rs.split("Featuring")));
        System.out.println(artists);
    }
}
