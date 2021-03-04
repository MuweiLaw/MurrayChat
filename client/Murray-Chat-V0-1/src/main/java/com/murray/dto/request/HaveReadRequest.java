package com.murray.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.io.Serializable;

/**
 * @author Murray Law
 * @describe 已读消息的
 * @createTime 2020/10/25
 */
public class HaveReadRequest extends Packet implements Serializable {
    private static final long serialVersionUID = 591640358372814547L;
    @JSONField(name = "SEDER USER NO", ordinal = 1)
    private String senderUserNO;
    @JSONField(name = "RECEIVE USER NO", ordinal = 2)
    private String receiveUserNO;
    @JSONField(name = "CHAT MESSAGE NO", ordinal = 3)
    private String chatMessageNO;
    @JSONField(name = "CHAT MESSAGE TYPE", ordinal = 4)
    private Byte chatMessageType;
    public HaveReadRequest() {
    }

    public HaveReadRequest(String senderUserNO, String receiveUserNO,String chatMessageNO,Byte chatMessageType) {
        this.senderUserNO = senderUserNO;
        this.receiveUserNO = receiveUserNO;
        this.chatMessageNO = chatMessageNO;
        this.chatMessageType = chatMessageType;

    }

    public String getChatMessageNO() {
        return chatMessageNO;
    }

    public void setChatMessageNO(String chatMessageNO) {
        this.chatMessageNO = chatMessageNO;
    }

    public String getSenderUserNO() {
        return senderUserNO;
    }

    public void setSenderUserNO(String senderUserNO) {
        this.senderUserNO = senderUserNO;
    }

    public String getReceiveUserNO() {
        return receiveUserNO;
    }

    public void setReceiveUserNO(String receiveUserNO) {
        this.receiveUserNO = receiveUserNO;
    }

    public Byte getChatMessageType() {
        return chatMessageType;
    }

    public void setChatMessageType(Byte chatMessageType) {
        this.chatMessageType = chatMessageType;
    }

    @Override
    public Byte getCommand() {
        return Command.HAVE_READ_REQUEST;
    }
}
