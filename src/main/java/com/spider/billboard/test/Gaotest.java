package com.spider.billboard.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xiang.gao on 2017/5/19.
 * project majorSpider
 */
public class Gaotest {


    @Test
    public void test(){
        String artist="Imagine Dragons With Logic, Ty Dolla $ign";
        List<String> artists = new ArrayList<String>();
        String rs = artist.replaceAll("(\\s+(Feat\\.|Featuring|&|x|/|,|And)\\s+|,)", " Featuring ");
        artists.addAll(Arrays.asList(rs.split("Featuring")));
        System.out.println(artists);
    }
}
