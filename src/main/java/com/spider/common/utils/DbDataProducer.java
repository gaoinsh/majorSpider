package com.spider.common.utils;

import com.spider.common.bean.Picture;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


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
    private String withOrderByBeanName;
    private Integer index;

    public DbDataProducer(String sql, ItemProcessor<T> processor, String withOrderByBeanName, Integer index, Class<T> clazz, Object... objects) {
        this.sql = sql;
        this.objects = objects;
        this.clazz = clazz;
        this.processor = processor;
        this.withOrderByBeanName = withOrderByBeanName;
        this.index = index;
    }

    public DbDataProducer(String sql, ItemProcessor<T> processor, String withOrderByBeanName, Integer index, boolean emptyQuit, long sleepMills, Class<T> clazz, Object... objects) {
        this(sql, processor, withOrderByBeanName, index, clazz, objects);
        this.sleepMills = sleepMills;
        this.emptyQuit = emptyQuit;
    }

    public void run() {
        while (true) {
            List<T> list = JdbcUtils.getObjects(sql, clazz, this.objects);
            if (list == null || list.isEmpty()) {
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
                if (null != this.withOrderByBeanName) {
                    try {
                        BigInteger e = new BigInteger(BeanUtils.getProperty(list.get(list.size() - 1), this.withOrderByBeanName));
                        this.objects[this.index - 1] = e;
                        logger.info("will put " + list.size() + " data to queue;next id:" + e);
                    } catch (Exception var4) {
                        var4.printStackTrace();
                    }
                }
                processor.doInProcess(list);
                list.clear();
            }
        }

    }

    public static void main(String[] args) {
        String sql = "select id,pic_link as url from tb_picture where id >? order by id limit ?";
        BlockingQueue<Picture> queue = new LinkedBlockingQueue<Picture>(1000);
        ItemProcessor<Picture> processor = new SingleItemProcessor<Picture>(queue);
        DbDataProducer<Picture> producer = new DbDataProducer<Picture>(sql, processor, "id", 1, true, 1000L, Picture.class, 0, 1000);
        new Thread(producer).start();

        DataConsumer<Picture> consumer = new DataConsumer<Picture>(queue, new DataProcessorService<Picture>() {
            public void doInProcess(Picture o) {
//                System.out.println(o.getUrl());
            }
        });
        new Thread(consumer).start();
    }
}
