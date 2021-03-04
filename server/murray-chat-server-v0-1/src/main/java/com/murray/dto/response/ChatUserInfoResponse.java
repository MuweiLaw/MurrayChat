package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.util.Date;

/**
 * @author Murray Law
 * @describe 响应用户信息
 * @createTime 2020/11/13
 */
public class ChatUserInfoResponse extends Packet {
    /**
     * 用户编号
     */
    private String chatUserNo;
    /**
     * 昵称
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;

    /**
     * 生日
     */
    private Date birthday;
    /**
     * 签名
     */
    private String signature;
    /**
     * 电话号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;
    /**
     * 公司职位
     */
    private String companyRank;

    /**
     * 公司职位
     */
    private String companyDepartment;
    /**
     * 头像类型jpg/png等
     */
    private String avatarType;

    /**
     * 所属公司
     */
    private String company;


    @Override
    public Byte getCommand() {
        return Command.CHAT_USER_INFO_RESPONSE;
    }

    public ChatUserInfoResponse() {
    }

    public ChatUserInfoResponse(String chatUserNo, String name, Integer age, Date birthday, String signature, String phone, String mail, String companyRank, String companyDepartment, String avatarType, String company) {
        this.chatUserNo = chatUserNo;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.signature = signature;
        this.phone = phone;
        this.mail = mail;
        this.companyRank = companyRank;
        this.companyDepartment = companyDepartment;
        this.avatarType = avatarType;
        this.company = company;
    }

    public String getChatUserNo() {
        return chatUserNo;
    }

    public void setChatUserNo(String chatUserNo) {
        this.chatUserNo = chatUserNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public String getCompanyRank() {
        return companyRank;
    }

    public void setCompanyRank(String companyRank) {
        this.companyRank = companyRank;
    }

    public String getCompanyDepartment() {
        return companyDepartment;
    }

    public void setCompanyDepartment(String companyDepartment) {
        this.companyDepartment = companyDepartment;
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
}
