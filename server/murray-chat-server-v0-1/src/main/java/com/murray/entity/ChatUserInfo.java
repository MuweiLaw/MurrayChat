package com.murray.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * chat_user_info
 * @author 
 */
public class ChatUserInfo implements Serializable {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 邮箱授权码
     */
    private String mailAuth;

    /**
     * 公司职位
     */
    private String companyRank;

    /**
     * 头像类型jpg/png等
     */
    private String avatarType;

    /**
     * 所属公司
     */
    private String company;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMailAuth() {
        return mailAuth;
    }

    public void setMailAuth(String mailAuth) {
        this.mailAuth = mailAuth;
    }

    public String getCompanyRank() {
        return companyRank;
    }

    public void setCompanyRank(String companyRank) {
        this.companyRank = companyRank;
    }

    public String getAvatarType() {
        return avatarType;
    }

    public void setAvatarType(String avatarType) {
        this.avatarType = avatarType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}