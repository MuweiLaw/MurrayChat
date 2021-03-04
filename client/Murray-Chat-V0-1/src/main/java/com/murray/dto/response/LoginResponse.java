package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.util.Date;

/**
 * @author Murray Law
 * @describe 登录请求
 * @createTime 2020/11/2
 */
public class LoginResponse extends Packet {
    /**
     * 用户编号
     */
    private String chatUserNO;

    /**
     * 昵称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

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

    private String prompt;//提示登录成功或失败原因

    public LoginResponse() {
    }

    public LoginResponse(String prompt) {
        this.prompt = prompt;
    }

    public LoginResponse(String chatUserNO, String name, String password, Integer age, Date birthday, String signature, String prompt) {
        this.chatUserNO = chatUserNO;
        this.name = name;
        this.password = password;
        this.age = age;
        this.birthday = birthday;
        this.signature = signature;
        this.prompt = prompt;
    }

    public String getChatUserNO() {
        return chatUserNO;
    }

    public void setChatUserNO(String chatUserNO) {
        this.chatUserNO = chatUserNO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
