package com.tmac2236.crawl.core;

import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.tmac2236.crawl.dao.StockProfileDao;
import com.tmac2236.crawl.entity.StockProfile;
import com.tmac2236.spring.core.util.JSONUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *教學 :            https://blog.csdn.net/huxiweng/column/info/jsoup
 *基本selector 操作 http://rx1226.pixnet.net/blog/post/335167865-%5Bjava%5D-14-4-jsoup%E7%94%A8selector%E6%96%B9%E6%B3%95%E8%A7%A3%E6%9E%90html
 *實戰操作                 http://www.cnblogs.com/xiaoMzjm/p/3899366.html
 */
@Component
public class StockDBInit extends Crawler {
    private static Logger logger = LoggerFactory.getLogger(StockDBInit.class);
    private static final String STOCK_LIST =    "http://isin.twse.com.tw/isin/C_public.jsp?strMode=";
    @Autowired
    private StockProfileDao stockProfileDao;
    
    @PostConstruct
    public void exec() {
        //上市
        initDbStockCode(STOCK_LIST+"2","1");
        //上櫃
        initDbStockCode(STOCK_LIST+"4","2");
        logger.info("StockDBInit executed exec()~~~");
    }
    /**
     * 
     * @param urlString  strMode=2:上市,strMode=4:上櫃
     * @param stockType  1:上市 ,2:上櫃
     * @return
     */
    public List<StockProfile>initDbStockCode(String urlString ,String stockType){
        StockDBInit stockDBInit = new StockDBInit();
        Connection con = stockDBInit.getInitConnection(urlString);
        Document doc = stockDBInit.httpGet(con);
        Elements elements = doc.select("tr:contains(ESVUFR)");
        JSONArray jSONArray = new JSONArray();
        for (Element element : elements) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", element.child(0).text().split("　")[0]);
            jSONObject.put("name", element.child(0).text().split("　")[1]);
            jSONObject.put("type", stockType);//興櫃
            jSONArray.put(jSONObject);
            //System.out.println(element.child(0).text().split("　")[0]);
        }
        
        List<StockProfile> stockList = null;
        JsonNode jsonNode = JSONUtil.parseJSON2JsonNode(jSONArray.toString());
        if (jsonNode != null && jsonNode.size() > 0) {
            stockList = JSONUtil.parseJSON(new TypeReference<List<StockProfile>>() {
            }, jsonNode.toString());
        }
        return stockProfileDao.save(stockList);
    }
    
}