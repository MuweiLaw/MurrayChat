package com.murray.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.io.Serializable;

/**
 * @author Murray Law
 * @describe 已读消息的
 * @createTime 2020/10/25
 */
public class HaveReadResponse extends Packet implements Serializable {
    private static final long serialVersionUID = 7873763247057511908L;
    @JSONField(name = "SEDER USER NO", ordinal = 1)
    private String senderUserNO;
    @JSONField(name = "RECEIVE USER NO", ordinal = 2)
    private String receiveUserNO;
    @JSONField(name = "CHAT MESSAGE NO", ordinal = 3)
    private String chatMessageNO;

    @JSONField(name = "CHAT MESSAGE TYPE", ordinal = 4)
    private Byte chatMessageType;
    public HaveReadResponse() {
    }

    public HaveReadResponse(String senderUserNO, String receiveUserNO,String chatMessageNO) {
        this.senderUserNO = senderUserNO;
        this.receiveUserNO = receiveUserNO;
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

    public String getChatMessageNO() {
        return chatMessageNO;
    }

    public void setChatMessageNO(String chatMessageNO) {
        this.chatMessageNO = chatMessageNO;
    }
    public Byte getChatMessageType() {
        return chatMessageType;
    }

    public void setChatMessageType(Byte chatMessageType) {
        this.chatMessageType = chatMessageType;
    }

    @Override
    public Byte getCommand() {
        return Command.HAVE_READ_RESPONSE;
    }
}
