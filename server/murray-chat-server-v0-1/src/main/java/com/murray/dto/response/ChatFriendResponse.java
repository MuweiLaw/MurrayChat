package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 好友的响应体
 * @createTime 2020/11/2
 */
public class ChatFriendResponse extends Packet {
    private Integer friendId;

    /**
     * 聊天用户自己的编号
     */
    private String chatUserNo;

    /**
     * 好友编号
     */
    private String friendUserNo;
    /**
     * 备注的名称
     */
    private String noteName;

    /**
     * 备注名
     */
    private String name;

    /**
     * 允许看我朋友圈,0允许/1不允许
     */
    private Boolean seeMe;

    /**
     * 是否看对方朋友圈,0是/1不是
     */
    private Boolean seeOtherParty;

    /**
     * 签名
     */
    private String signature;

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public Byte getCommand() {
        return Command.FRIEND_RESPONSE;
    }

    @Override
    public String toString() {
        return "ChatFriendResponse{" +
                "friendId=" + friendId +
                ", chatUserNo='" + chatUserNo + '\'' +
                ", friendUserNO='" + friendUserNo + '\'' +
                ", noteName='" + noteName + '\'' +
                ", name='" + name + '\'' +
                ", seeMe=" + seeMe +
                ", seeOtherParty=" + seeOtherParty +
                ", signature='" + signature + '\'' +
                '}';
    }
}
