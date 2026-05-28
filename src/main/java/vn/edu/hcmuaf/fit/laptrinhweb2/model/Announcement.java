package vn.edu.hcmuaf.fit.laptrinhweb2.model;

import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.AccountStatus;

import java.time.LocalDateTime;


public class Announcement {
    private int id;
    private AccountStatus accountStatus;
    private String content;
    private String targetUrl;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean isActive;

    public Announcement() {
    }

    public Announcement(
            int id,
            AccountStatus accountStatus,
            String content,
            String targetUrl,
            LocalDateTime createdAt,
            LocalDateTime expiresAt,
            boolean isActive
    ) {
        this.id = id;
        this.accountStatus = accountStatus;
        this.content = content;
        this.targetUrl = targetUrl;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.isActive = isActive;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTargetUrl() {
        return targetUrl;
    }
    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
}
