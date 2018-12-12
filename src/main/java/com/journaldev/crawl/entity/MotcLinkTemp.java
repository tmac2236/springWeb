package com.journaldev.crawl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MOTC_LINK_TEMP")
public class MotcLinkTemp {

    @Id
    @Column(name = "linkid")
    private String linkId;
    
    @Column(name = "bearing")
	private String bearing;
	
    @Column(name = "city_code")
    private String cityCode;
    
    @Column(name = "city_name")
    private String cityName;

    @Column(name = "direction")
    private String direction;
    
    @Column(name = "start_node")
    private String startNode;
    
    @Column(name = "end_node")
    private String endNode;
    
    @Column(name = "length")
    private String length;
    
    @Column(name = "mileage")
    private String mileage;
    
    @Column(name = "road_classid")
    private String roadClassId;
    
    @Column(name = "road_class_name")
    private String roadClassName;
    
    @Column(name = "roadid")
    private String roadId;
    
    @Column(name = "road_name")
    private String roadName;
    
    // 120.35504,23.40152 120.35506,23.40125 120.35508,23.40093 120.35514,23.3997
    @Column(name = "polyLine")
    private String polyLine;

    public MotcLinkTemp() {
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
    
}
