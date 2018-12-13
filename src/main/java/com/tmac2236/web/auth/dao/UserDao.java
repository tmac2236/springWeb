package com.tmac2236.web.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tmac2236.web.auth.entity.User;


@Repository
public interface UserDao extends JpaRepository<User, String> {
    
    @Query("from User t where t.account = ?1")
    User findOneByAccount(String account);
    @Query("from User t where t.userId = ?1")
    User findOneByUserId(String userId);
}