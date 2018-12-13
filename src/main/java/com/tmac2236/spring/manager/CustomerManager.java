package com.tmac2236.spring.manager;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmac2236.spring.dao.CustomerDao;

@Component
public class CustomerManager {
    private static Logger logger = LoggerFactory.getLogger(CustomerManager.class);
    
    public CustomerManager() {
        super();
    }

    /**
     * 每小時的40分30秒讀取資料
     * 
     * cron - 固定的時間或週期，週期式行為同 fixedRate
     * 固定六個值：秒(0-59) 分(0-59) 時(0-23) 日(1-31) 月(1-12) 週(1,日-7,六)
     * 日與週互斥，其中之一必須為 ?
     * 可使用的值有：單一數值（26）、範圍（50-55）、清單（9,10）、不指定（*）與週期（* / 3）
     */
//    @Scheduled(cron = "30 11 * * * ?")
//    private void exec() {
//        logger.info("ParkEtagManager 開始執行...");
//        clearnVB();
//        recvData();
//        // 測試配對用
//        // offlineMatching();
//        logger.info("ParkEtagManager 執行完成!");
//    }
    @PostConstruct
    private void exec() {
        logger.info("ParkEtagManager 執行完成!");
    }   

}
