package com.tmac2236.crawl.manager;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmac2236.crawl.dao.MotcLinkTempDao;
import com.tmac2236.crawl.dto.Json2Dto;
import com.tmac2236.crawl.dto.MotcLinkTempDto;
import com.tmac2236.crawl.dto.RoadBasic;
import com.tmac2236.crawl.entity.MotcLinkTemp;
import com.tmac2236.spring.core.util.UrlUtil;

@Component
public class LinkIdManager {
    // crawling all roads of KaoShiung
    private static String KS_ROAD_URL = "https://link.motc.gov.tw/v2/Road/RoadName/CityRoad/Kaohsiung?$format=JSON";

    // To capture RoadName API by specific RoadName and City
    private static String ksPrefix = "https://link.motc.gov.tw/v2/Road/Link/CityRoad/Kaohsiung/";
    private static String ksSurffix = "?$format=JSON";

    private static String geoJsonPrefix = "https://link.motc.gov.tw/v2/Road/Link/Shape/Geometry/GeoJson/";
    @Autowired
    MotcLinkTempDao motcLinkTempDao;
    //@PostConstruct
    public void exec() throws IOException, JSONException, InterruptedException {
        JSONArray jArrKSRoad = UrlUtil.readJsonArrayFromUrl(KS_ROAD_URL);
        Json2Dto json2Dto = new RoadBasic();
        List<RoadBasic> list = (List<RoadBasic>) json2Dto.convertJsonObject(jArrKSRoad);
        System.out.println(list);
        
        long limitTime =0;
        for (RoadBasic roadBasic : list) {
            
            String encodeRoadName = URLEncoder.encode(roadBasic.getRoadName());
//測試用        String encodeRoadName = URLEncoder.encode("一心一路");
            StringBuffer buf = new StringBuffer();
            buf.append(ksPrefix);
            buf.append(encodeRoadName);
            buf.append(ksSurffix);
            System.out.println(" 試圖requsest URL :" + buf);
                long start =System.nanoTime();
                    List<MotcLinkTempDto> dtoList = useURLGetMotcLinkTempDto(buf.toString());
                //Unit: nanoSecond = 1000000 millisecond = 1000000000 second
                long elapsedTime = (System.nanoTime() -start)/1000000;    
            System.out.println(" 路名 : " + roadBasic.getRoadName() + "共有 " + dtoList.size() + "個 LinkID");
            List<MotcLinkTemp> motcLinkTemp = convertToMotcLinkTemp(dtoList);

            motcLinkTempDao.save(motcLinkTemp);
            
            System.out.println("運行時間 :"+elapsedTime +"毫秒");
            System.out.println("睡3秒.............................");
            Thread.currentThread().sleep(3000);

        }

    }

    /**
     * 取路某路名的LinkId物件們
     */
    public static List<MotcLinkTempDto> useURLGetMotcLinkTempDto(String url) {
        List<MotcLinkTempDto> proceedList = new ArrayList<MotcLinkTempDto>();
        try {
            JSONArray jArrLinkId = UrlUtil.readJsonArrayFromUrl(url);
            System.out.println(jArrLinkId);
            Json2Dto json2Dto = new MotcLinkTempDto();
            // 將LinkID轉成物件(一個路名有多個LinkID)
            List<MotcLinkTempDto> list = (List<MotcLinkTempDto>) json2Dto.convertJsonObject(jArrLinkId);
            proceedList = assemblePolyLine(list);
            System.out.println(list);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return proceedList;
    }

    /**
     * 串接API查詢LinkID的經緯度並組合成PolyLine
     */
    public static List<MotcLinkTempDto> assemblePolyLine(List<MotcLinkTempDto> list) {
        System.out.println("**************************************");
        System.out.println(list.size());

        List<MotcLinkTempDto> resultList = new ArrayList<MotcLinkTempDto>();

        for (MotcLinkTempDto motcLinkTempDto : list) {
            StringBuffer buf = new StringBuffer();
            buf.append(geoJsonPrefix);
            buf.append(motcLinkTempDto.getLinkId());
            System.out.println(buf);
            try {
                JSONObject jsonStr = UrlUtil.readJsonFromUrl(buf.toString());
                JSONObject object = (JSONObject) jsonStr.getJSONArray("features").get(0);
                JSONArray jsonArray2 = (JSONArray) object.getJSONObject("geometry").get("coordinates");
                // 組合LolyLine e.g. :120.35504,23.40152 120.35506,23.40125
                // 120.35508,23.40093 120.35514,23.3997
                StringBuffer bufPolyLine = new StringBuffer();
                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONArray nestJSONArray = (JSONArray) jsonArray2.get(i);

                    for (int j = 0; j < nestJSONArray.length(); j++) {
                        bufPolyLine.append(nestJSONArray.get(j));
                        if (j < nestJSONArray.length() - 1) {
                            bufPolyLine.append(",");
                        }
                    }
                    if (i < jsonArray2.length() - 1) {
                        bufPolyLine.append(" ");
                    }
                }
                motcLinkTempDto.setPolyLine(bufPolyLine.toString());
                resultList.add(motcLinkTempDto);
                System.out.println("  polyLine  :" + bufPolyLine);

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    /**
     * 將Dto轉成Bean
     */
    public static List<MotcLinkTemp> convertToMotcLinkTemp(List<MotcLinkTempDto> dtoList) {
        List<MotcLinkTemp> motcList = new ArrayList<MotcLinkTemp>();
        for (MotcLinkTempDto dto : dtoList) {
            MotcLinkTemp motcLinkTemp = new MotcLinkTemp();
            motcLinkTemp.setBearing(dto.getBearing());
            motcLinkTemp.setCityCode(dto.getCityCode());
            motcLinkTemp.setCityName(dto.getCityName());
            motcLinkTemp.setDirection(dto.getDirection());
            motcLinkTemp.setEndNode(dto.getEndNode());
            motcLinkTemp.setLength(dto.getLength());
            motcLinkTemp.setLinkId(dto.getLinkId());
            // motcLinkTemp.setMileage(mileage);
            motcLinkTemp.setPolyLine(dto.getPolyLine());
            motcLinkTemp.setRoadClassId(dto.getRoadClassId());
            motcLinkTemp.setRoadClassName(dto.getRoadClassName());
            motcLinkTemp.setRoadId(dto.getRoadId());
            motcLinkTemp.setRoadName(dto.getRoadName());
            motcLinkTemp.setStartNode(dto.getStartNode());
            motcList.add(motcLinkTemp);
        }

        return motcList;
    }

}

