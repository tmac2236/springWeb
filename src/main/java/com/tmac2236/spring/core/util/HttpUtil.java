package com.tmac2236.spring.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
    
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String getUrlContent(URL url) {
        int timeout = 10000;
        String charsetName = "UTF-8";
        return getUrlContent(url, timeout, charsetName, null);
    }
    
    public static String getUrlContent(URL url, HashMap<String, String> headerMap) {
        int timeout = 10000;
        String charsetName = "UTF-8";
        return getUrlContent(url, timeout, charsetName, headerMap);
    }

    
    public static String getUrlContent(URL url, int timeout, String charsetName, HashMap<String, String> headerMap) {
        if (url == null) {
            return "";
        }
        
        HttpURLConnection conn = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept-Encoding", "gzip");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=" + charsetName);
            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    conn.setRequestProperty(key, headerMap.get(key));
                }
            }
            conn.setUseCaches(false);
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.connect();
            int status = conn.getResponseCode();
            if (status ==  200 || status == 201) {
                if ("gzip".equals(conn.getContentEncoding())) {
                    br = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream()), charsetName));
                }
                else{
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charsetName));
                }
                
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                if(sb.length() > 0){
                    sb.setLength(sb.length() - 1);
                }
            }
            else{
                logger.warn("error in request url [" + url.toString() + "], http status code = " + status);
            }
        } catch (Exception e) {
            logger.warn("error in getUrlContent()", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.warn("BufferedReader close error", e);
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return sb.toString();

    }
    
}