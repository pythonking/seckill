package com.karsa.utils;

import com.google.common.collect.Lists;
import com.karsa.dto.JdContent;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Slf4j
public class HtmlParseUtil {
    private final static String URL_JD = "https://search.jd.com/Search?keyword=";

    /**
     * 解析网页,Jsoup返回Document就是页面对象
     *
     * @param keyword
     * @return
     * @throws IOException
     */
    public static List<JdContent> parseJD(String keyword) throws IOException {
        String jsoupUrl = URL_JD + keyword;
        Document document = Jsoup.parse(new URL(jsoupUrl), 30000);
        Element element = document.getElementById("J_goodsList");
        log.info("爬虫地址 {},爬到京东数据 {}", jsoupUrl, element);
        if (null == element) {
            return null;
        }
        Elements liElements = element.getElementsByTag("li");
        if (CollectionUtils.isEmpty(liElements)) {
            return null;
        }
        List<JdContent> jdContentList = Lists.newArrayList();
        for (Element el : liElements) {
            String title = el.getElementsByClass("p-name").eq(0).text();
            String price = el.getElementsByClass("p-price").eq(0).text();
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            jdContentList.add(new JdContent(title, price, img));

        }
        return jdContentList;
    }

    public static void main(String[] args) throws IOException {
        parseJD("python").forEach(System.out::println);
    }
}
