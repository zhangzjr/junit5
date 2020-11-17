package com.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Version 1.0
 * @Author:Admin
 * @Date:2020/11/12 12:39
 * @PackageName:com.web
 * @ServiceName:
 */
public class QyWeixinLoginTest {


    /**
     * 获取企业微信的cookie 保存到cookies.yaml文件中
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    void qyWeixinLoginTest() throws InterruptedException, IOException {

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://work.weixin.qq.com/wework_admin/frame");
        //sleep 20
        Thread.sleep(15000);
        Set<Cookie> cookies = driver.manage().getCookies();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.writeValue(new File("cookies.yaml"), cookies);

    }


    /**
     * 从 cookies.yaml文件中 读取
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    void qyWeixinLoginedTest() throws IOException, InterruptedException{

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://work.weixin.qq.com/wework_admin/frame");

        driver.manage().window().maximize();

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference typeReference=new TypeReference<List<HashMap<String, Object>>>() {};
        List<HashMap<String, Object>> cookies = (List<HashMap<String, Object>>) mapper.readValue(new File("cookies.yaml"), typeReference);
        System.out.println(cookies);

        cookies.forEach(cookieMap->{
            driver.manage().addCookie(new Cookie(cookieMap.get("name").toString(), cookieMap.get("value").toString()));
        });

        driver.navigate().refresh();

        driver.findElement(By.xpath("//span[@class='index_service_cnt_item_title']")).click();

        driver.findElement(By.name("username")).sendKeys("张三一");
        driver.findElement(By.name("acctid")).sendKeys("12345");
        driver.findElement(By.name("mobile")).sendKeys("18837159999");
        driver.findElement(By.linkText("保存并继续添加")).click();

        Thread.sleep(1000);

        //driver.findElement(By.linkText("首页")).click();
        driver.findElement(By.linkText("通讯录")).click();

    }
}
