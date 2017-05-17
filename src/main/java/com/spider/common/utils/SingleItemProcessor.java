package com.spider.common.utils;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by xiang.gao on 2017/5/17.
 * project majorSpider
 */
public class SingleItemProcessor<T> implements ItemProcessor<T> {
    private BlockingQueue<T> queue;

    public SingleItemProcessor(BlockingQueue<T> queue) {
        this.queue = queue;
    }

    public void doInProcess(List<T> list) {
        if (list != null && !list.isEmpty()) {
            for (T object : list) {
                try {
                    queue.put(object);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
