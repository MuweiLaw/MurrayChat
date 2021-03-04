package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.util.Map;

/**
 * @author Murray Law
 * @describe 群聊消息未读情况反馈
 * @createTime 2020/12/19
 */
public class GroupMsgHaveReadResponse extends Packet {
    private String msgNo;//群聊消息编号
    private String senderNo;//发送已读的用户编号
    private String recipientN0;//接收已读的用户编号
    private Map<String,Boolean> atHaveReadMap;//@的已读map

    public GroupMsgHaveReadResponse() {
    }

    public GroupMsgHaveReadResponse(String msgNo, String senderNo, String recipientN0, Map<String, Boolean> atHaveReadMap) {
        this.msgNo = msgNo;
        this.senderNo = senderNo;
        this.recipientN0 = recipientN0;
        this.atHaveReadMap = atHaveReadMap;
    }

    public String getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(String msgNo) {
        this.msgNo = msgNo;
    }

    public String getSenderNo() {
        return senderNo;
    }

    public void setSenderNo(String senderNo) {
        this.senderNo = senderNo;
    }

    public String getRecipientN0() {
        return recipientN0;
    }

    public void setRecipientN0(String recipientN0) {
        this.recipientN0 = recipientN0;
    }

    public Map<String, Boolean> getAtHaveReadMap() {
        return atHaveReadMap;
    }

    public void setAtHaveReadMap(Map<String, Boolean> atHaveReadMap) {
        this.atHaveReadMap = atHaveReadMap;
    }

    @Override
    public Byte getCommand() {
        return Command.GROUP_MSG_HAVE_READ_CHAT_RESPONSE;
    }
}
