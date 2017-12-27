package com.spider.selenium;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

/**
 * Created by xiang.gao on 2017/12/11.
 * project majorSpider
 */
public class SeleniumDemo {
    public static void main(String[] args) throws IOException {
        //webDriver目录 classes根目录下
        String path = SeleniumDemo.class.getResource("/").getPath() + "chromedriver.exe";
        ChromeDriverService service = new ChromeDriverService.Builder().usingAnyFreePort().usingDriverExecutable(new File(path)).build();
        service.start();
        //chrome位置
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
        WebDriver driver = new ChromeDriver(service, options);
        driver.get("https://weibo.com/");
        driver.manage().window().maximize();
        WebDriverWait waitCondition=new WebDriverWait(driver,30);
//        waitCondition.until(new Function<WebDriver, Object>() {
//            public Object apply(WebDriver webDriver) {
//                return webDriver.findElement(By.xpath("//*[@id=\"loginname\"]"));
//            }
//        });
        //登录
        driver.findElement(By.xpath("//*[@id=\"loginname\"]")).sendKeys("15623643356@sina.cn");
        driver.findElement(By.xpath("//*[@id=\"pl_login_form\"]/div/div[3]/div[2]/div/input")).sendKeys("lydia921204");
        driver.findElement(By.xpath("//*[@id=\"pl_login_form\"]/div/div[3]/div[6]/a")).click();

        driver.findElement(By.xpath("//*[@id=\"v6_pl_content_publishertop\"]/div/div[2]/textarea")).sendKeys("selenium demo test ");
        driver.findElement(By.xpath("//*[@id=\"v6_pl_content_publishertop\"]/div/div[3]/div[1]/a")).click();


        service.stop();
    }
}
