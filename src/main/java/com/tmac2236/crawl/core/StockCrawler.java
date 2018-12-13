package com.tmac2236.crawl.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.tmac2236.crawl.dto.RoadBasic;
import com.tmac2236.spring.core.util.UrlUtil;


/**
 *教學 :            https://blog.csdn.net/huxiweng/column/info/jsoup
 *基本selector 操作 http://rx1226.pixnet.net/blog/post/335167865-%5Bjava%5D-14-4-jsoup%E7%94%A8selector%E6%96%B9%E6%B3%95%E8%A7%A3%E6%9E%90html
 *實戰操作                 http://www.cnblogs.com/xiaoMzjm/p/3899366.html
 */
public class StockCrawler extends Crawler {
    // public static Logger logger = Logger.getLogger(RoadCrawler.class);
    private static final String KS_ROAD_URL = "https://link.motc.gov.tw/v2/Road/RoadName/CityRoad/Kaohsiung?$format=JSON";
    private static final String GoodInfoPrefix = "https://goodinfo.tw/StockInfo/StockDividendPolicy.asp?STOCK_ID=";

    public static void main(String[] args) {

        StockCrawler stockCrawler = new StockCrawler();
        Connection con = stockCrawler.getInitConnection(GoodInfoPrefix+"3388");
        Document doc = stockCrawler.httpGet(con);
        Elements elements = doc.select("table");
        
        String stockPrice = elements.get(11).child(0).child(2).child(0).text();
        System.out.println(stockPrice);
    }
    
    

    public  List<RoadBasic> crawlingKaoShiung(String url) {
        List<RoadBasic> roadList = new ArrayList<RoadBasic>();
        try {
            JSONArray jsonArray = UrlUtil.readJsonArrayFromUrl(KS_ROAD_URL);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                RoadBasic roadBasic = new RoadBasic();
                roadBasic.setRoadId(obj.getString("RoadID"));
                roadBasic.setRoadName(obj.getString("RoadName"));
                roadBasic.setRoadNameID(obj.getString("RoadNameID"));
                roadBasic.setRoadClass(obj.getInt("RoadClass"));
                roadBasic.setRoadClassName(obj.getString("RoadClassName"));
                roadBasic.setCityID(obj.getString("CityID"));
                roadBasic.setCityName(obj.getString("CityName"));
                roadBasic.setCity(obj.getString("City"));
                roadBasic.setMileLength(obj.getString("MileLength"));
                roadBasic.setLength(new Double(obj.getDouble("Length")));
                roadBasic.setVersion(obj.getString("Version"));
                roadBasic.setUpdateDate(obj.getString("UpdateDate"));
                roadList.add(roadBasic);
            }
        } catch (JSONException | IOException e) {
            // logger.error("Error ocurred form RoadCrawler and detail is : ",
            // e);
            e.printStackTrace();
        }
        return roadList;
    }

}
