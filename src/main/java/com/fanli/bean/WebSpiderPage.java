package com.fanli.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiang.gao on 2018/1/17.
 * project 51fanli
 */
public class WebSpiderPage {

    private WebSpiderRequest request;
    private String content;
    private final List<WebSpiderRequest> targetRequests = new ArrayList<>();

    public WebSpiderPage(WebSpiderRequest request, String content) {
        this.request = request;
        this.content = content;
    }

    public void addTargetRequest(WebSpiderRequest request) {
        targetRequests.add(request);
    }

    public List<WebSpiderRequest> getTargetRequests() {
        return targetRequests;
    }

    public WebSpiderRequest getRequest() {
        return request;
    }


    public String getContent() {
        return content;
    }

}
