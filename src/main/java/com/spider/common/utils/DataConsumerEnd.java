package com.spider.common.utils;

import java.util.concurrent.BlockingQueue;

/**
 * Created by xiang.gao on 2017/5/18.
 * project majorSpider
 */
public class DataConsumerEnd<T> implements Runnable {
    private BlockingQueue<T> queue;
    private DataProcessorService<T> service;
    private Thread producer;

    public DataConsumerEnd(BlockingQueue<T> queue, DataProcessorService<T> service, Thread producer) {
        this.queue = queue;
        this.service = service;
        this.producer = producer;
    }


    public void run() {
        while (producer.isAlive() || !queue.isEmpty()) {
            try {
                T o = queue.take();
                service.doInProcess(o);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
