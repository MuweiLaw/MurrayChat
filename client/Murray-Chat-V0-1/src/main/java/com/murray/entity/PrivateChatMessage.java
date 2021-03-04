package com.murray.entity;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author Murray Law
 * @describe 用户消息实体类
 * @createTime 2020/10/25
 */
public class PrivateChatMessage extends Packet implements Serializable {


    private static final long serialVersionUID = 1139954604750120658L;
    private String msgNo;

    private String senderUserNo;

    private String msg;

    private String receiverUserNo;

    private Date issueTime;

    private Boolean haveRead;

    private Byte msgType; //0为文本消息,1为图片,2为文件


    @Override
    public Byte getCommand() {
        return Command.MESSAGE;
    }

    public PrivateChatMessage() {
        this.msgNo = UUID.randomUUID().toString();
    }

    public PrivateChatMessage( String senderUserNo, String msg, String receiverUserNo, Date issueTime, Boolean haveRead, Byte msgType) {
        this.msgNo = UUID.randomUUID().toString();
        this.senderUserNo = senderUserNo;
        this.msg = msg;
        this.receiverUserNo = receiverUserNo;
        this.issueTime = issueTime;
        this.haveRead = haveRead;
        this.msgType = msgType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(String msgNo) {
        this.msgNo = msgNo;
    }

    public String getSenderUserNo() {
        return senderUserNo;
    }

    public void setSenderUserNo(String senderUserNo) {
        this.senderUserNo = senderUserNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReceiverUserNo() {
        return receiverUserNo;
    }

    public void setReceiverUserNo(String receiverUserNo) {
        this.receiverUserNo = receiverUserNo;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public Boolean getHaveRead() {
        return haveRead;
    }

    public void setHaveRead(Boolean haveRead) {
        this.haveRead = haveRead;
    }

    public Byte getMsgType() {
        return msgType;
    }

    public void setMsgType(Byte msgType) {
        this.msgType = msgType;
    }
}
