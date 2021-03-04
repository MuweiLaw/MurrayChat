package com.murray.entity;

/**
 * @author Murray Law
 * @describe 最后登录的用户类
 * @createTime 2020/12/29
 */
public class LastLoginInfo {
    private String userNo;
    private String userAvatar;
    private String userPassWord;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public LastLoginInfo() {
    }

    public LastLoginInfo(String userNo, String userAvatar, String userPassWord) {
        this.userNo = userNo;
        this.userAvatar = userAvatar;
        this.userPassWord = userPassWord;
    }

    @Override
    public String toString() {
        return "LastLoginInfo{" +
                "userNo='" + userNo + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", userPassWord='" + userPassWord + '\'' +
                '}';
    }
}
