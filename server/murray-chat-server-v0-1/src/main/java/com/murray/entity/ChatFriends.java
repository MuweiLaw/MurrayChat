package com.murray.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * chat_friends
 * @author 
 */
public class ChatFriends implements Serializable {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 聊天用户自己的编号
     */
    private String chatUserNo;

    /**
     * 好友编号
     */
    private String friendUserNo;

    /**
     * 备注名
     */
    private String noteName;

    /**
     * 允许看我朋友圈,0允许/1不允许
     */
    private Boolean seeMe;

    /**
     * 是否看对方朋友圈,0是/1不是
     */
    private Boolean seeOtherParty;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatUserNo() {
        return chatUserNo;
    }

    public void setChatUserNo(String chatUserNo) {
        this.chatUserNo = chatUserNo;
    }

    public String getFriendUserNo() {
        return friendUserNo;
    }

    public void setFriendUserNo(String friendUserNo) {
        this.friendUserNo = friendUserNo;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public Boolean getSeeMe() {
        return seeMe;
    }

    public void setSeeMe(Boolean seeMe) {
        this.seeMe = seeMe;
    }

    public Boolean getSeeOtherParty() {
        return seeOtherParty;
    }

    public void setSeeOtherParty(Boolean seeOtherParty) {
        this.seeOtherParty = seeOtherParty;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ChatFriends{" +
                "id=" + id +
                ", chatUserNo='" + chatUserNo + '\'' +
                ", friendUserNo='" + friendUserNo + '\'' +
                ", noteName='" + noteName + '\'' +
                ", seeMe=" + seeMe +
                ", seeOtherParty=" + seeOtherParty +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}