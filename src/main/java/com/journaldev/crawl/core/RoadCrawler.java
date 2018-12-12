package com.journaldev.crawl.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.journaldev.crawl.dto.RoadBasic;
import com.journaldev.spring.core.util.UrlUtil;

public class RoadCrawler {
    // public static Logger logger = Logger.getLogger(RoadCrawler.class);
    private static String KS_ROAD_URL = "https://link.motc.gov.tw/v2/Road/RoadName/CityRoad/Kaohsiung?$format=JSON";

    public static void main(String[] args) {
        List<RoadBasic> ksList  =crawlingKaoShiung(KS_ROAD_URL);
        System.out.println(ksList);
    }

    public static List<RoadBasic> crawlingKaoShiung(String url) {
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
