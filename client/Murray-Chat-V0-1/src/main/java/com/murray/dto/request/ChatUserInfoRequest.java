package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 聊天用户信息请求
 * @createTime 2020/11/16
 */
public class ChatUserInfoRequest extends Packet {
    private String requesterNo;//请求者编号
    private String queryUserNo;//要查询的用户编号

    public ChatUserInfoRequest() {
    }

    public ChatUserInfoRequest(String requesterNo, String queryUserNo) {
        this.requesterNo = requesterNo;
        this.queryUserNo = queryUserNo;
    }

    public String getRequesterNo() {
        return requesterNo;
    }

    public void setRequesterNo(String requesterNo) {
        this.requesterNo = requesterNo;
    }

    public String getQueryUserNo() {
        return queryUserNo;
    }

    public void setQueryUserNo(String queryUserNo) {
        this.queryUserNo = queryUserNo;
    }

    @Override
    public Byte getCommand() {
        return Command.CHAT_USER_INFO_REQUEST;
    }
}
