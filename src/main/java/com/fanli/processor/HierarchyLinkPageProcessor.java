package com.fanli.processor;

import com.fanli.bean.WebSpiderPage;
import com.fanli.bean.WebSpiderRequest;
import com.spider.common.utils.RegexFilter;
import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.utils.UrlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiang.gao on 2018/1/17.
 * project 51fanli
 */
public class HierarchyLinkPageProcessor implements PageProcessor {
    private static final String LEVEL = "level";
    private static final String PARENT_URL = "parent_url";
    private int maxLevel = 3;  //最大深度

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    @Override
    public void process(WebSpiderPage page) {
        String content = page.getContent();
        WebSpiderRequest request = page.getRequest();
        String parentUrl = request.getUrl();
        Map<String, Object> extraDataMap = request.getExtraData();
        //请求页面的层级深度
        int currLevel = 1;
        if (extraDataMap != null && extraDataMap.get(LEVEL) != null) {
            currLevel = Integer.parseInt(extraDataMap.get(LEVEL).toString());
        }
        if (currLevel < maxLevel) {
            List<String> urls = getTargetUrl(content);
            if (urls != null && !urls.isEmpty()) {
                for (String url : urls) {
                    if (StringUtils.isBlank(url) || url.equals("#") || url.startsWith("javascript:")) {
                        continue;
                    }
                    url = UrlUtils.canonicalizeUrl(url, parentUrl);

                    WebSpiderRequest newRequest = new WebSpiderRequest();
                    newRequest.setUrl(url);
                    Map<String, Object> mapData = new HashMap<>();
                    mapData.put(LEVEL, currLevel + 1);
                    mapData.put(PARENT_URL, parentUrl);
                    newRequest.setExtraData(mapData);

                    page.addTargetRequest(newRequest);

                }
            }
        }
    }

    private List<String> getTargetUrl(String content) {
        return RegexFilter.regexFilterList(content, "<a[^>]*?href=\"([^\"]+)\"");
    }
}
