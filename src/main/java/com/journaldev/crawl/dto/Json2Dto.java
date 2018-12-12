package com.journaldev.crawl.dto;

import java.util.List;

import org.json.JSONArray;

public interface Json2Dto {
    public List<?> convertJsonObject(JSONArray jSONArray);
}
