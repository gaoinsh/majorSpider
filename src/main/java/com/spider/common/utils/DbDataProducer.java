package com.spider.common.utils;

import com.spider.common.bean.Picture;
import org.apache.log4j.Logger;

import java.util.List;


/**
 * Created by xiang.gao on 2017/5/17.
 * project majorSpider
 */
public class DbDataProducer<T> implements Runnable {
    private static Logger logger = Logger.getLogger(DbDataProducer.class.getName());
    private String sql;
    private Object[] objects;
    private Class<T> clazz;
    private ItemProcessor<T> processor;
    private boolean emptyQuit = true;
    private long sleepMills = 1000;

    public DbDataProducer(String sql, Class<T> clazz, ItemProcessor<T> processor, Object... objects) {
        this.sql = sql;
        this.objects = objects;
        this.clazz = clazz;
        this.processor = processor;
    }

    public DbDataProducer(String sql, Class<T> clazz, ItemProcessor<T> processor, boolean emptyQuit, long sleepMills, Object... objects) {
        this(sql, clazz, processor, objects);
        this.sleepMills = sleepMills;
        this.emptyQuit = emptyQuit;
    }

    public void run() {
        List<T> objects = JdbcUtils.getObjects(sql, clazz, this.objects);
        while (true) {
            if (objects == null || objects.isEmpty()) {
                if (emptyQuit) {
                    logger.info("get empty results .. quit, sql: " + sql);
                    break;
                }
                try {
                    Thread.sleep(sleepMills);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                processor.doInProcess(objects);
            }

        }

    }

    public static void main(String[] args) {
        String sql = "select id ,pic_link as url,local_pic_link as localUrl,image_status as status from tb_picture";
        List<Picture> pictures = JdbcUtils.getObjects(sql, Picture.class);
        for (Picture pic : pictures) {
            System.out.println(pic.toString());
        }
    }
}
