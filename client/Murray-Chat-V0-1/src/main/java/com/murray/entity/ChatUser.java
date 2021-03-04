package com.murray.entity;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.io.Serializable;
import java.util.Date;

/**
 * chat_user
 *
 * @author
 */
public class ChatUser extends Packet implements Serializable {

    private static final long serialVersionUID = 7202182966513438759L;
    private String chatUserNO;//用户编号

    private String name;

    private String password;

//    private String avatar;//头像路径

    private String signature;//签名
    private Integer age;

    private Date birthday;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

    public ChatUser() {
    }

    public ChatUser(String chatUserNO, String password) {
        this.chatUserNO = chatUserNO;
        this.password = password;
    }

    public ChatUser(String chatUserNO, String name, String password, String signature, Integer age, Date birthday) {
        this.chatUserNO = chatUserNO;
        this.name = name;
        this.password = password;
        this.signature = signature;
        this.age = age;
        this.birthday = birthday;
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


    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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
}