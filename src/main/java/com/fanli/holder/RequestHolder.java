package com.fanli.holder;

import com.fanli.bean.WebSpiderRequest;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by xiang.gao on 2018/1/17.
 * project 51fanli
 * 保存抓取到的url
 */
public class RequestHolder {
    private static Logger logger = Logger.getLogger(RequestHolder.class);

    //待抓取的队列
    private BlockingQueue<WebSpiderRequest> requestQueue = new LinkedBlockingQueue<>();

    //内部set去重
    private DuplicateUrlRemover remover = new DuplicateUrlRemover();

    public void push(WebSpiderRequest request) {
//        logger.info("get request url : " + request.getUrl());
        if (!remover.isDuplicated(request)) {
            logger.info("push queue : " + request.getUrl());
            requestQueue.add(request);
        }
    }

    public WebSpiderRequest poll() {
        return requestQueue.poll();
    }
}
