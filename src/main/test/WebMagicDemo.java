import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by xiang.gao on 2017/5/16.
 * project majorSpider
 */
public class WebMagicDemo implements PageProcessor{
    private Site site = Site.me().setSleepTime(1000);

    public void process(Page page) {
        page.addTargetRequest(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all().get(0));
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new WebMagicDemo()).addUrl("https://github.com/code4craft").thread(1).addPipeline(new Pipeline() {
            public void process(ResultItems resultItems, Task task) {
                System.out.println(" hello ？ "+resultItems.getAll());
            }
        }).run();
    }
}
