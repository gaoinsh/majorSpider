package com.fanli.requestor;


import com.fanli.bean.WebSpiderPage;
import com.fanli.bean.WebSpiderRequest;

/**
 * Created by xiang.gao on 2018/1/17.
 * project 51fanli
 */
public interface WebSpiderDownloader {

    WebSpiderPage download(WebSpiderRequest request);
}
