package com.tmac2236.crawl.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmac2236.crawl.entity.StockProfile;


@Repository
public interface StockProfileDao extends JpaRepository<StockProfile, String> {

}