package com.murray.view.dto;

import java.util.Date;

/**
 * @author Murray Law
 * @describe 主面板上的消息
 * @createTime 2020/10/27
 */
public class PrivateChatNotification {
    private String chatUserNO;

    private String avatarPath;
    private String nickname;
    private String chatMessageNo;

    private String message;
    private Date issueTime;
    private boolean showTime;//是否显示时间
    private boolean haveRead;//是否读取
    private Byte msgType;//消息类型


    public PrivateChatNotification() {

    }

    public PrivateChatNotification(String chatUserNO, String avatarPath, String nickname, String chatMessageNo, String message, Date issueTime, boolean showTime, boolean haveRead,Byte msgType) {
        this.chatUserNO = chatUserNO;
        this.avatarPath = avatarPath;
        this.nickname = nickname;
        this.chatMessageNo = chatMessageNo;
        this.message = message;
        this.issueTime = issueTime;
        this.showTime = showTime;
        this.haveRead = haveRead;
        this.msgType = msgType;

    }

    @Override
    public String toString() {
        return "NotificationPrivate{" +
                "chatUserNO='" + chatUserNO + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", nickname='" + nickname + '\'' +
                ", message='" + message + '\'' +
                ", issueTime=" + issueTime +
                ", showTime=" + showTime +
                ", haveRead=" + haveRead +
                '}';
    }

    public String getChatUserNO() {
        return chatUserNO;
    }

    public void setChatUserNO(String chatUserNO) {
        this.chatUserNO = chatUserNO;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getChatMessageNo() {
        return chatMessageNo;
    }

    public void setChatMessageNo(String chatMessageNo) {
        this.chatMessageNo = chatMessageNo;
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


    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }

    public boolean isShowTime() {

        return showTime;
    }

    public boolean isHaveRead() {
        return haveRead;
    }
//默认未读
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
