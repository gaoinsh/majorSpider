package com.fanli.main;

import com.fanli.bean.WebSpiderPage;
import com.fanli.processor.HierarchyLinkPageProcessor;
import com.fanli.thread.WebSpiderWorker;


/**
 * Created by xiang.gao on 2018/1/17.
 * project 51fanli
 */
public class WebSpiderFanliMain {
    private static final int maxLevel = 4;

    public static void main(String[] args) {
        execute();
    }

    private static void execute() {

        HierarchyLinkPageProcessor pageProcessor = new HierarchyLinkPageProcessor(maxLevel) {
            @Override
            public void process(WebSpiderPage page) {
                super.process(page);

            }
        };


        new WebSpiderWorker(pageProcessor).addUrl("http://passport.fanli.com/center/welcome").start();

    }


}
