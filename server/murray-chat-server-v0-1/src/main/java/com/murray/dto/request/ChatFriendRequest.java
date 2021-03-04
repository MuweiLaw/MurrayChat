package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.io.Serializable;

/**
 * chat_friends
 * @author 
 */
public class ChatFriendRequest extends Packet implements Serializable {
    private Integer friendId;

    /**
     * 聊天用户自己的编号
     */
    private String chatUserNo;

    /**
     * 好友编号
     */
    private String friendUserNO;

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

    private static final long serialVersionUID = 1L;

    @Override
    public Byte getCommand() {
        return Command.FRIEND_REQUEST;
    }

    public ChatFriendRequest() {
    }

    public ChatFriendRequest(Integer friendId, String chatUserNo, String friendUserNO, String noteName, Boolean seeMe, Boolean seeOtherParty) {
        this.friendId = friendId;
        this.chatUserNo = chatUserNo;
        this.friendUserNO = friendUserNO;
        this.noteName = noteName;
        this.seeMe = seeMe;
        this.seeOtherParty = seeOtherParty;
    }

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

    public String getFriendUserNO() {
        return friendUserNO;
    }

    public void setFriendUserNO(String friendUserNO) {
        this.friendUserNO = friendUserNO;
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
}