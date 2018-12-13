package com.tmac2236.web.auth.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="WEB_acl")
public class ACL {
    
    @Id
    @Column(name = "aclId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aclId;
    
    @Column(name = "roleId")
    private String roleId;
    
    @Column(name = "userId")
    private String userId;
    
    
    @OneToOne(fetch =FetchType.EAGER)
    @JoinColumn(name="resourceId", referencedColumnName="resourceId")    
    private ACLLocation aCLLocation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinColumn(name="userId", insertable=false, updatable =false)
    private User user;
    
    public ACL() {
    }


    public ACL(int aclId, String roleId, String userId, ACLLocation aCLLocation, User user) {
        super();
        this.aclId = aclId;
        this.roleId = roleId;
        this.userId = userId;
        this.aCLLocation = aCLLocation;
        this.user = user;
    }


    public int getAclId() {
        return aclId;
    }


    public void setAclId(int aclId) {
        this.aclId = aclId;
    }


    public String getRoleId() {
        return roleId;
    }


    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ACLLocation getACLLocation() {
        return aCLLocation;
    }


    public void setACLLocation(ACLLocation aCLLocation) {
        this.aCLLocation = aCLLocation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ACL [aclId=" + aclId + ", roleId=" + roleId + ", userId=" + userId + ", aCLLocation=" + aCLLocation + "]";
    }

}

