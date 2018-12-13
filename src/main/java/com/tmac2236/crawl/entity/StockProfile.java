package com.tmac2236.crawl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * StockProfile
 */

@Entity
@Table(name = "stock_profile")
// 轉換Entity時忽略json中不存在的字段
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockProfile {

    @Id
    @Column(name = "code")
    @JsonProperty("code")
    private String code;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;
    
    /**
     * 1:上市
     * 2:興櫃
     */
    @Column(name = "type")
    @JsonProperty("type")
    private String type;
    
    public StockProfile() {
        super();
    }

    public StockProfile(String code, String name, String type) {
        super();
        this.code = code;
        this.name = name;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
