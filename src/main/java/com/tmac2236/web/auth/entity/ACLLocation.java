package com.tmac2236.web.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="acl_location")
public class ACLLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "lat")
    private Double lat;
    
    @Column(name = "lng")
    private Double lng;
    
    @Column(name = "resourceId")
    private String resourceId;

    public ACLLocation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public ACLLocation(int id, Double lat, Double lng, String resourceId) {
        super();
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "ACLLocation [id=" + id + ", lat=" + lat + ", lng=" + lng + ", resourceId=" + resourceId + "]";
    }

}

