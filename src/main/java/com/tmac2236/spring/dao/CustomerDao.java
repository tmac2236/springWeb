package com.tmac2236.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmac2236.spring.entity.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {

}
