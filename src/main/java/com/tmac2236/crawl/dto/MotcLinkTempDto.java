package com.tmac2236.crawl.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MotcLinkTempDto implements Json2Dto {

    private String linkId;
    
	private String bearing;
	
    private String cityCode;
    
    private String cityName;

    private String direction;
    
    private String startNode;
    
    private String endNode;
    
    private String length;
    
    private String mileage;
    
    private String roadClassId;
    
    private String roadClassName;
    
    private String roadId;
    
    private String roadName;
    
    // 120.35504,23.40152 120.35506,23.40125 120.35508,23.40093 120.35514,23.3997
    private String polyLine;

    public MotcLinkTempDto() {
        super();
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getBearing() {
        return bearing;
    }

    public void setBearing(String bearing) {
        this.bearing = bearing;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStartNode() {
        return startNode;
    }

    public void setStartNode(String startNode) {
        this.startNode = startNode;
    }

    public String getEndNode() {
        return endNode;
    }

    public void setEndNode(String endNode) {
        this.endNode = endNode;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getRoadClassId() {
        return roadClassId;
    }

    public void setRoadClassId(String roadClassId) {
        this.roadClassId = roadClassId;
    }

    public String getRoadClassName() {
        return roadClassName;
    }

    public void setRoadClassName(String roadClassName) {
        this.roadClassName = roadClassName;
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

    public String getPolyLine() {
        return polyLine;
    }

    public void setPolyLine(String polyLine) {
        this.polyLine = polyLine;
    }

    @Override
    public List<?> convertJsonObject(JSONArray jSONArray) {
        List<MotcLinkTempDto> list = new ArrayList<MotcLinkTempDto>();
        try {
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject obj = jSONArray.getJSONObject(i);
                MotcLinkTempDto motcLinkTempDto = new MotcLinkTempDto();
                
                
                motcLinkTempDto.setLinkId(obj.getString("LinkID"));
                motcLinkTempDto.setRoadId(obj.getString("RoadID"));
                motcLinkTempDto.setRoadName(obj.getString("RoadName"));
                motcLinkTempDto.setRoadClassId(obj.getString("RoadClass"));
                motcLinkTempDto.setRoadClassName(obj.getString("RoadClassName"));
                motcLinkTempDto.setDirection(obj.getString("RoadDirectionID"));
                motcLinkTempDto.setBearing(obj.getString("Bearing"));
                motcLinkTempDto.setStartNode(obj.getString("StartNode"));
                motcLinkTempDto.setEndNode(obj.getString("EndNode"));
                //motcLinkTempDto.setStartMile(obj.getString("StartMile"));
                //motcLinkTempDto.setEndMile(obj.getString("EndMile"));
                //motcLinkTempDto.setMileLength(obj.getString("MileLength"));
                motcLinkTempDto.setLength(String.valueOf(obj.getString("Length")));
                //motcLinkTempDto.setCityId(obj.getString("CityID"));
                motcLinkTempDto.setCityName(obj.getString("CityName"));
                motcLinkTempDto.setCityCode(obj.getString("City"));
                //motcLinkTempDto.setVersion(obj.getString("Version"));
                //motcLinkTempDto.setUpdateDate(obj.getString("UpdateDate"));
                
                list.add(motcLinkTempDto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
