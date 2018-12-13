package com.tmac2236.crawl.dto;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tmac2236.crawl.dto.Json2Dto;

/**
 * RoadBasic
 */
public class RoadBasic implements Json2Dto {

    private String roadId;

    private String roadName;

    private String roadNameID;

    private int roadClass;

    private String roadClassName;

    private String cityID;

    private String cityName;

    private String city;

    private String mileLength;

    private Double length;

    private String version;

    private String updateDate;

    public RoadBasic() {
        super();
    }

    public RoadBasic(String roadName, String roadNameID, int roadClass, String roadClassName, String cityID,
            String cityName, String city, String mileLength, Double length, String version, String updateDate) {
        super();
        this.roadName = roadName;
        this.roadNameID = roadNameID;
        this.roadClass = roadClass;
        this.roadClassName = roadClassName;
        this.cityID = cityID;
        this.cityName = cityName;
        this.city = city;
        this.mileLength = mileLength;
        this.length = length;
        this.version = version;
        this.updateDate = updateDate;
    }

    public String getRoadId() {
        return roadId;
    }

    public void setRoadId(String roadId) {
        this.roadId = roadId;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getRoadNameID() {
        return roadNameID;
    }

    public void setRoadNameID(String roadNameID) {
        this.roadNameID = roadNameID;
    }

    public int getRoadClass() {
        return roadClass;
    }

    public void setRoadClass(int roadClass) {
        this.roadClass = roadClass;
    }

    public String getRoadClassName() {
        return roadClassName;
    }

    public void setRoadClassName(String roadClassName) {
        this.roadClassName = roadClassName;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMileLength() {
        return mileLength;
    }

    public void setMileLength(String mileLength) {
        this.mileLength = mileLength;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public List<RoadBasic> convertJsonObject(JSONArray jSONArray) {
        List<RoadBasic> roadList = new ArrayList<RoadBasic>();
        try {
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject obj = jSONArray.getJSONObject(i);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return roadList;
    }

}

