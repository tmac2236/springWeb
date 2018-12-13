package com.tmac2236.web.auth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tmac2236.web.auth.entity.ACL;

@Repository
public interface ACLDao extends JpaRepository<ACL, String> {

    @Modifying
    @Transactional
    @Query("delete ACL a where a.roleId = ?1")
    int deleteACLByRoleId(String roleId);

    @Query("select a from ACL a where a.roleId = ?1")
    List<ACL> findACLByRoleId(String roleId);

    @Query("select a from ACL a where a.userId = ?1")
    List<ACL> findACLByUserId(String userId);
}
