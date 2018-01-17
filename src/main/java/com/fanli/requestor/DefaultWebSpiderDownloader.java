package com.fanli.requestor;


import com.fanli.bean.WebSpiderPage;
import com.fanli.bean.WebSpiderRequest;

/**
 * Created by xiang.gao on 2018/1/17.
 * project 51fanli
 */
public class DefaultWebSpiderDownloader implements WebSpiderDownloader {
    @Override
    public WebSpiderPage download(WebSpiderRequest request) {
        String url = request.getUrl();

        return null;
    }
}
