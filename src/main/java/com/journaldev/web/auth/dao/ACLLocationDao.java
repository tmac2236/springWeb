package com.journaldev.web.auth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.journaldev.web.auth.entity.ACL;
import com.journaldev.web.auth.entity.ACLLocation;

@Repository
public interface ACLLocationDao extends JpaRepository<ACL, String> {

    @Query("select a from ACLLocation a where a.resourceId = ?1")
    ACLLocation findACLLocationByResourceId(String resourceId);

}
