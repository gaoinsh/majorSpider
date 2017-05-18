package com.spider.tuchong.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spider.common.dao.PictureDao;
import com.spider.tuchong.bean.Photo;
import com.spider.tuchong.dao.PhotoDao;
import com.spider.tuchong.dao.PhotoSourceDao;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selector;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xiang.gao on 2017/5/17.
 * project majorSpider
 */
public class GrabMain {
    private static Logger logger = Logger.getLogger(GrabMain.class.getName());
    private static final String baseUrl = "https://tuchong.com/rest/tags/@tag@/posts?page=@page@&count=100&order=weekly&before_timestamp=";
    private static PictureDao picDao = new PictureDao();
    private static PhotoSourceDao picSourceDao = new PhotoSourceDao();
    private static PhotoDao photoDao = new PhotoDao();
    private static long sourceId;

    enum Tag {
        portrait("人像"),
       /* scenery("风光"),
        humanity("人文"),*/
        beauty("美女");

        private String tagName;

        Tag(String tagName) {
            this.tagName = tagName;
        }
    }

    public static void main(String[] args) {
        sourceId = picSourceDao.getSourceId("图虫");
        for (Tag tag : Tag.values()) {
            String tagParam = "";
            try {
                tagParam = URLEncoder.encode(tag.tagName, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url = baseUrl.replace("@page@", "1").replace("@tag@", tagParam);
            PhotoPageProcessor photoPageProcessor = new PhotoPageProcessor(tag.tagName, tagParam);
            PhotoPipeline photoPipeline = new PhotoPipeline();
            Spider.create(photoPageProcessor).addPipeline(photoPipeline).addUrl(url).thread(5).start();
        }
    }


    private static class PhotoPageProcessor implements PageProcessor {

        private String tagName;
        private String encodeTag;
        private AtomicInteger currPage = new AtomicInteger(1);

        public PhotoPageProcessor(String tagName, String encodeTag) {
            this.tagName = tagName;
            this.encodeTag = encodeTag;
        }

        public void process(Page page) {
            boolean goNext = false;
            logger.info(" tag : " + tagName + " , currpage: " + currPage.get());
            List<String> pics = page.getHtml().selectDocumentForList(new Selector() {
                public String select(String text) {

                    return null;
                }

                public List<String> selectList(String text) {
                    text = text.substring(text.indexOf("{"), text.indexOf("</body>"));
                    List<String> pics = new ArrayList<String>();
                    JSONObject jsonObject = JSON.parseObject(text);
                    Object postList = jsonObject.get("postList");
                    if (postList != null && postList instanceof List) {
                        List<JSONObject> posts = (List<JSONObject>) postList;
                        for (JSONObject post : posts) {
                            Object images = post.get("images");
                            if (images instanceof JSONArray) {
                                JSONArray imagesArray = (JSONArray) images;
                                for (Object imageObj : imagesArray) {
                                    JSONObject imageJsonObj = (JSONObject) imageObj;
                                    Object img_id = imageJsonObj.get("img_id");
                                    Object user_id = imageJsonObj.get("user_id");
                                    String imgUrl = "https://photo.tuchong.com/" + user_id + "/l/" + img_id + ".webp";
                                    pics.add(imgUrl);
                                }

                            }
                        }
                    }
                    return pics;
                }
            });
            if (!pics.isEmpty()) {
                page.putField("data", pics);
                page.putField("tag", tagName);
                goNext = true;
            }
            if (goNext) {
                page.addTargetRequest(baseUrl.replace("@page@", currPage.incrementAndGet() + "").replace("@tag@", encodeTag));
            }
        }

        public Site getSite() {
            return Site.me().setRetryTimes(3).setCharset("utf-8");
        }
    }

    private static class PhotoPipeline implements Pipeline {

        public void process(ResultItems resultItems, Task task) {
            Object data = resultItems.get("data");
            if (data instanceof List) {
                List<String> pics = (List<String>) data;
                List<Photo> photos = new ArrayList<Photo>();
                for (String pic : pics) {
                    Photo photo = new Photo();
                    photo.setTag(resultItems.get("tag").toString());
                    photo.setPicId(picDao.getPicId(pic));
                    photo.setSourceId(sourceId);
                    photos.add(photo);
                }
                if (!photos.isEmpty()) {
                    for (Photo photo : photos) {
                        photoDao.save(photo);
                    }
                }
            }
        }
    }
}
