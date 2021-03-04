package com.murray.view.dto;

import java.util.Date;

/**
 * @author Murray Law
 * @describe 主面板上的消息
 * @createTime 2020/10/27
 */
public class GroupChatNotification {
    private String msgNo;
    private String groupNO;
    private String senderNo;
    private String senderName;
    private String message;
    private Date issueTime;
    private boolean showTime;//是否显示时间
    private boolean haveRead;//是否读取
    private Byte msgType;//消息类型


    public GroupChatNotification() {

    }

    public GroupChatNotification(String msgNo, String groupNO,  String senderNo, String senderName, String message, Date issueTime, boolean showTime, boolean haveRead, Byte msgType) {
        this.msgNo = msgNo;
        this.groupNO = groupNO;
        this.senderNo = senderNo;
        this.senderName = senderName;
        this.message = message;
        this.issueTime = issueTime;
        this.showTime = showTime;
        this.haveRead = haveRead;
        this.msgType = msgType;
    }

    public String getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(String msgNo) {
        this.msgNo = msgNo;
    }

    public String getGroupNO() {
        return groupNO;
    }

    public void setGroupNO(String groupNO) {
        this.groupNO = groupNO;
    }


    public String getSenderNo() {
        return senderNo;
    }

    public void setSenderNo(String senderNo) {
        this.senderNo = senderNo;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public boolean isShowTime() {
        return showTime;
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
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
}
