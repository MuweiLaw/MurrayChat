package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Murray Law
 * @describe 群消息请求头
 * @createTime 2020/11/10
 */
public class GroupMsgRequest extends Packet {

    private String groupMsgNo;

    private String groupNo;

    private String senderUserNo;//消息发出者

    private List<String> atUserNoList;

    private String message;

    private Date issueTime;

    private Byte msgType; //看messageType接口

    public GroupMsgRequest() {
        this.groupMsgNo = UUID.randomUUID().toString();
    }

    public GroupMsgRequest(String groupNo, String senderUserNo, List<String> atUserNoList, String message, Date issueTime, Byte msgType) {
        this.groupMsgNo = UUID.randomUUID().toString();
        this.groupNo = groupNo;
        this.senderUserNo = senderUserNo;
        this.atUserNoList = atUserNoList;
        this.message = message;
        this.issueTime = issueTime;
        this.msgType = msgType;
    }

    public String getGroupMsgNo() {
        return groupMsgNo;
    }

    public void setGroupMsgNo(String groupMsgNo) {
        this.groupMsgNo = groupMsgNo;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getSenderUserNo() {
        return senderUserNo;
    }

    public void setSenderUserNo(String senderUserNo) {
        this.senderUserNo = senderUserNo;
    }

    public List<String> getAtUserNoList() {
        return atUserNoList;
    }

    public void setAtUserNoList(List<String> atUserNoList) {
        this.atUserNoList = atUserNoList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public Byte getMsgType() {
        return msgType;
    }

    public void setMsgType(Byte msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return "GroupMsgRequest{" +
                "groupMsgNo='" + groupMsgNo + '\'' +
                ", groupNo='" + groupNo + '\'' +
                ", senderUserNo='" + senderUserNo + '\'' +
                ", atUserNoList=" + atUserNoList +
                ", message='" + message + '\'' +
                ", issueTime=" + issueTime +
                ", msgType=" + msgType +
                '}';
    }

    @Override
    public Byte getCommand() {
        return Command.GROUP_CHAT_REQUEST;
    }
}
