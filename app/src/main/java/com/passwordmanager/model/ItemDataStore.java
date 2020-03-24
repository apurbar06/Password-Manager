package com.passwordmanager.model;

public class ItemDataStore {
    private String title;
    private String username;
    private String emailId;
    private String mobileNo;
    private String password;
    private int id;

    public ItemDataStore(int id, String title, String username, String emailId, String mobileNo, String password){
        this.id = id;
        this.title = title;
        this.username = username;
        this.emailId = emailId;
        this.mobileNo = mobileNo;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
