package com.journaldev.crawl.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.journaldev.crawl.entity.MotcLinkTemp;


@Repository
public interface MotcLinkTempDao extends JpaRepository<MotcLinkTemp, String> {

}