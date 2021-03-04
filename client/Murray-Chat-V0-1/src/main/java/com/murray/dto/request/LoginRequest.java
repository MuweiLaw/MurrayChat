package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 登录请求
 * @createTime 2020/11/2
 */
public class LoginRequest extends Packet {
    private String chatUserNO;
    private String password;


    public LoginRequest(String chatUserNO, String password) {
        this.chatUserNO = chatUserNO;
        this.password = password;
    }


    public String getChatUserNO() {
        return chatUserNO;
    }

    public void setChatUserNO(String chatUserNO) {
        this.chatUserNO = chatUserNO;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
