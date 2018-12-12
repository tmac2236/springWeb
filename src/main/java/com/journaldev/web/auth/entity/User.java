package com.journaldev.web.auth.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.journaldev.spring.core.util.UUIDUtil;

@Entity
@Table(name = "WEB_user")
public class User {

	@Id
	@Column(name = "userId", nullable = false, length = 50)
	private String userId = UUIDUtil.getSeqULID();

	@Column(name = "account", length = 50, unique = true, nullable = false)
	private String account;

	@Column(name = "password", length = 512, nullable = false)
	@JsonIgnore
	private String password;

	@Column(name = "name", length = 50)
	private String name = "";

	@Column(name = "phone", length = 30)
	private String phone;

	@Column(name = "address", length = 50)
	private String address ;

	@Column(name = "mail")
	private String mail;
	
    @Column(name = "role")
    private String role;	

	@Transient
	private String uuid = UUIDUtil.getUUID();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userId", cascade = CascadeType.ALL)
    private List<ACL> acls = new ArrayList<ACL>();

	public User() {

	}

	public User(String account, String password, String name, String phone, String address, String mail, String role) {
        super();
        this.account = account;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.mail = mail;
        this.role = role;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUUID() {
		return this.uuid;
	}

    public List<ACL> getAcls() {
        return acls;
    }

    public void setAcls(List<ACL> acls) {
        this.acls = acls;
    }

}
