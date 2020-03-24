package com.passwordmanager.model;


import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemDataStore {
    private String title;
    private String username;
    private String emailId;
    private String mobileNo;
    private String password;
    private int id;

    public ItemDataStore(int id, String title, String username, String emailId, String mobileNo, String password) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.emailId = emailId;
        this.mobileNo = mobileNo;
        this.password = password;
    }

    public ItemDataStore(JSONObject object) {
        try {
            this.id = (int) object.get("id");
        } catch (JSONException e) {
            e.printStackTrace();
            this.id = 0;
        }
        try {
            this.title = (String) object.get("title");
        } catch (JSONException e) {
            e.printStackTrace();
            this.title = "";
        }
        try {
            this.username = (String) object.get("username");
        } catch (JSONException e) {
            e.printStackTrace();
            this.username = "";
        }
        try {
            this.emailId = (String) object.get("emailId");
        } catch (JSONException e) {
            e.printStackTrace();
            this.emailId = "";
        }
        try {
            this.mobileNo = (String) object.get("mobileNo");
        } catch (JSONException e) {
            e.printStackTrace();
            this.mobileNo = "";
        }
        try {
            this.password = (String) object.get("password");
        } catch (JSONException e) {
            e.printStackTrace();
            this.password = "";
        }

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

    @NonNull
    public JSONObject toObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("password", this.getPassword());
            obj.put("id", this.getId());
            obj.put("title", this.getTitle());
            obj.put("username", this.getUsername());
            obj.put("emailId", this.getEmailId());
            obj.put("mobileNo", this.getMobileNo());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
