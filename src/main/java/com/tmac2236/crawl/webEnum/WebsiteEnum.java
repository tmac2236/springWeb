package com.tmac2236.crawl.webEnum;

import java.util.EnumSet;


/**
 * 基本用法:
 * 一、取得所有EnumSet EnumSet<WebsiteEnum> websiteEnum = WebsiteEnum.getSet(); 
 *
 */
public enum WebsiteEnum  {
    
    TWSE(1,"TWSE","http://www.twse.com.tw/zh/",1),
    GOODINFO(2,"GOODINFO","https://goodinfo.tw",2),
    ISIN(3,"ISIN","http://isin.twse.com.tw",3);
    
    private int index;
    private String name;
    private String prefix;
    private int type;
    static EnumSet<WebsiteEnum> set;
    
    static{
        set = EnumSet.allOf(WebsiteEnum.class);
    }

    private WebsiteEnum(int index,String name ,String prefix, int type) {
        this.index = index;
        this.name = name;
        this.prefix = prefix;
        this.type = type;
    }
    
    public static EnumSet<WebsiteEnum> getSet() {
        return set;
    }


    public void setSet(EnumSet<WebsiteEnum> set) {
        this.set = set;
    }

    public int getIndex() {
        return index;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
    
}
