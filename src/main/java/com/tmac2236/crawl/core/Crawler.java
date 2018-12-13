package com.tmac2236.crawl.core;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public abstract class Crawler {
    
    public Connection getInitConnection(String url) {
        // 獲取請求連接
        Connection con = Jsoup.connect(url).timeout(600000);
        // 最大連接大小Bytes(O:infinity)
        con.maxBodySize(0);
        // 60秒
        con.timeout(60000);

        // 請求標頭設置，特別是cookie設置
        con.header("Accept", "text/html, application/xhtml+xml, */*");
        con.header("Content-Type", "application/x-www-form-urlencoded");
        con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))");
        return con;
    }

    public Document httpGet(Connection con) {

        // 解析请求结果
        Document doc = null;
        try {
            doc = con.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 解析tag用 Elements contents = document.select("table");
     */
    public void printDocument(Elements elements) {
        for (int i = 0; i < elements.size(); i++) {
            System.out.println("========== 第" + i + "個 " + elements.get(i).tagName() + "==========");
            System.out.println(elements.get(i));
        }
    }
    
}
