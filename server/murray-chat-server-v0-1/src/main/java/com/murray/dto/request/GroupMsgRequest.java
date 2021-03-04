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

    public static final String GROUP_MSG="group_msg_";

    public static final String FIELD_UNREAD_USER_NO_LIST = "unreadUserNoList";
    public static final String FIELD_AT_USER_NO_LIST = "atUserNoList";

    private String groupMsgNo;

    private String groupNo;

    private String senderUserNo;//消息发出者

    private List<String> atUserNoList;//艾特列表

    private List<String> unreadUserNoList;//未读列表

    private String message;

    private Date issueTime;


    private Byte msgType; //0为文本消息,1为图片,2为文件


    public GroupMsgRequest() {
        this.groupMsgNo = UUID.randomUUID().toString();
    }

    public GroupMsgRequest(String groupNo, String senderUserNo, List<String> atUserNoList, List<String> unreadUserNoList, String message, Date issueTime, Byte msgType) {
        this.groupMsgNo = UUID.randomUUID().toString();
        this.groupNo = groupNo;
        this.senderUserNo = senderUserNo;
        this.atUserNoList = atUserNoList;
        this.unreadUserNoList = unreadUserNoList;
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

    public List<String> getUnreadUserNoList() {
        return unreadUserNoList;
    }

    public void setUnreadUserNoList(List<String> unreadUserNoList) {
        this.unreadUserNoList = unreadUserNoList;
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
                ", unreadUserNoList=" + unreadUserNoList +
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
