package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 修改密码请求
 * @createTime 2020/12/31
 */
public class ChangePwdRequest extends Packet {
    private String chatUserNo;
    private String oldPwd;
    private String newPwd;

    public ChangePwdRequest() {
    }

    public ChangePwdRequest(String chatUserNo, String oldPwd, String newPwd) {
        this.chatUserNo = chatUserNo;
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    public String getChatUserNo() {
        return chatUserNo;
    }

    public void setChatUserNo(String chatUserNo) {
        this.chatUserNo = chatUserNo;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    @Override
    public Byte getCommand() {
        return Command.CHANGE_PASSWORD_REQUEST;
    }
}
