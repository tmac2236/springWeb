package com.tmac2236.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * 停車場和鄰近ETAG對應表
 */
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "id")
    private int id;
    
    @Column(name = "emp_name")
    private String empName;
    
    @Column(name = "emp_dept")
    private int empDept;

    public Employee(String empName, int empDept) {
        super();
        this.empName = empName;
        this.empDept = empDept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public int getEmpDept() {
        return empDept;
    }

    public void setEmpDept(int empDept) {
        this.empDept = empDept;
    }

}
