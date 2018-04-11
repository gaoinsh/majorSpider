package com.fanli.thread;

import com.fanli.bean.WebSpiderPage;
import com.fanli.bean.WebSpiderRequest;
import com.fanli.holder.RequestHolder;
import com.fanli.processor.PageProcessor;
import com.fanli.requestor.DefaultWebSpiderDownloader;
import com.fanli.requestor.WebSpiderDownloader;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiang.gao on 2018/1/17.
 * project 51fanli
 */
public class WebSpiderWorker implements Runnable {
    private static Logger logger = Logger.getLogger(WebSpiderWorker.class);

    private ExecutorService threadPool = Executors.newFixedThreadPool(5);

    private RequestHolder holder = new RequestHolder();

    private ReentrantLock newUrlLock = new ReentrantLock();

    private Condition newUrlCondition = newUrlLock.newCondition();

    private PageProcessor pageProcessor;

    private WebSpiderDownloader downloader = new DefaultWebSpiderDownloader();


    public WebSpiderWorker(PageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
    }

    public void start() {
        new Thread(this).start();
    }

    public WebSpiderWorker setDownloader(WebSpiderDownloader downloader) {
        this.downloader = downloader;
        return this;
    }

    @Override
    public void run() {
        logger.info("web spider started!");
        while (!Thread.currentThread().isInterrupted()) {
            WebSpiderRequest request = holder.poll();
            if (request == null) {
                waitNewUrl();
            } else {
                doRequestAsyn(request);
            }
        }
        logger.info("web spider stopted");
    }

    private void doRequestAsyn(final WebSpiderRequest request) {
        threadPool.submit(() -> {
            WebSpiderPage page = downloader.download(request);

            if (page == null) {
                onError(request);
            } else {
                pageProcessor.process(page);
                List<WebSpiderRequest> targetRequests = page.getTargetRequests();
                if (targetRequests != null && !targetRequests.isEmpty()) {
                    targetRequests.forEach(this::addRequest);
                }
            }
        });
    }

    private void onError(WebSpiderRequest request) {
        logger.error("request url occurs error ! " + request.getUrl());

    }

    private void waitNewUrl() {
        newUrlLock.lock();
        try {
            newUrlCondition.await(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.warn("waitNewUrl - interrupted, error {}", e);
        } finally {
            newUrlLock.unlock();
        }
    }

    public WebSpiderWorker addRequest(WebSpiderRequest request) {
        holder.push(request);
        signalNewUrl();
        return this;
    }

    public WebSpiderWorker addUrl(String url) {
        holder.push(new WebSpiderRequest(url));
        return this;
    }

    private void signalNewUrl() {
        newUrlLock.lock();
        try {
            newUrlCondition.signalAll();
        } finally {
            newUrlLock.unlock();
        }
    }

}
