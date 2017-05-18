package com.spider.common.utils;

import java.util.concurrent.BlockingQueue;

/**
 * Created by xiang.gao on 2017/5/18.
 * project majorSpider
 */
public class DataConsumer<T> implements Runnable {
    private BlockingQueue<T> queue;
    private DataProcessorService<T> service;

    public DataConsumer(BlockingQueue<T> queue, DataProcessorService<T> service) {
        this.queue = queue;
        this.service = service;
    }

    public void run() {

        while (true) {
            try {
                T o = queue.take();
                service.doInProcess(o);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
