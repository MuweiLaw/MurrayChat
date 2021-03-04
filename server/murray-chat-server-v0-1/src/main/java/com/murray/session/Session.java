package com.murray.session;

public class Session {
    // 用户唯一性标识
    private String userId;

    public Session(String userId) {
        this.userId = userId;
    }

    public Session() {
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
