package vn.edu.hcmuaf.fit.laptrinhweb2.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.Date;

public class Account {
    private int id;
    private String username;
    private String accountName;
    private String password;
    private int accountStatus;
    @ColumnName("user_phone_number")
    private String phoneNumber;
    @ColumnName("user_email")
    private String accountEmail;
    private String role;
    private String avatarUrl;
    public Account(int id, String username, String accountName, String password, int accountStatus, String phoneNumber,
                   String accountEmail, String role, String avatarUrl) {
        super();
        this.id = id;
        this.username = username;
        this.accountName = accountName;
        this.password = password;
        this.accountStatus = accountStatus;
        this.phoneNumber = phoneNumber;
        this.accountEmail = accountEmail;
        this.role = role;
        this.avatarUrl = avatarUrl;
    }
    public Account() {
        super();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAccountEmail() {
        return accountEmail;
    }
    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

}


