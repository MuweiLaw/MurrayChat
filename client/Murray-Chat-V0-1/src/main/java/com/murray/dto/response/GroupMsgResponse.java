package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.util.Date;

/**
 * @author Murray Law
 * @describe 群消息请求头
 * @createTime 2020/11/10
 */
public class GroupMsgResponse extends Packet {
    private String groupMsgNo;

    private String groupNo;

    private String senderUserNo;//消息发出者

    private Boolean atMe;//是否@我

    private String message;


    private String receiverUserNo;//接收者

    private Date issueTime;

    private boolean haveRead;//接收者是否读取

    private Byte msgType; //0为文本消息,1为图片,2为文件


    public GroupMsgResponse() {
    }

    public GroupMsgResponse(String groupMsgNo, String groupNo, String senderUserNo, Boolean atMe, String message, String receiverUserNo, Date issueTime, boolean haveRead, Byte msgType) {
        this.groupMsgNo = groupMsgNo;
        this.groupNo = groupNo;
        this.senderUserNo = senderUserNo;
        this.atMe = atMe;
        this.message = message;
        this.receiverUserNo = receiverUserNo;
        this.issueTime = issueTime;
        this.haveRead = haveRead;
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

    public Boolean getAtMe() {
        return atMe;
    }

    public void setAtMe(Boolean atMe) {
        this.atMe = atMe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public boolean isHaveRead() {
        return haveRead;
    }

    public void setHaveRead(boolean haveRead) {
        this.haveRead = haveRead;
    }

    public Byte getMsgType() {
        return msgType;
    }

    public void setMsgType(Byte msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return "GroupMsgResponse{" +
                "groupMsgNo='" + groupMsgNo + '\'' +
                ", groupNo='" + groupNo + '\'' +
                ", senderUserNo='" + senderUserNo + '\'' +
                ", atMe=" + atMe +
                ", message='" + message + '\'' +
                ", receiverUserNo='" + receiverUserNo + '\'' +
                ", issueTime=" + issueTime +
                ", haveRead=" + haveRead +
                ", msgType=" + msgType +
                '}';
    }

    @Override
    public Byte getCommand() {
        return Command.GROUP_CHAT_RESPONSE;
    }
}
