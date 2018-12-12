package com.journaldev.web.auth.dto;

public class UserDto {

    private String userId;
    private String account;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String mail;
    private String role;
    
    
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
    @Override
    public String toString() {
        return "UserDto [userId=" + userId + ", account=" + account + ", password=" + password + ", name=" + name
                + ", phone=" + phone + ", address=" + address + ", mail=" + mail + ", role=" + role + "]";
    }

}
