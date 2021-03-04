package com.murray.view.dto;

import java.util.UUID;

/**
 * @author Murray Law
 * @describe 主面板上的消息
 * @createTime 2020/10/27
 */
public class FriendNotification {
    private String friendNotificationNO;
    private String friendUserNO;
    private String avatarPath;
    private String nickname;
    private String noteName;

    /**
     * 签名
     */
    private String signature;

    public FriendNotification() {
        this.friendNotificationNO = UUID.randomUUID().toString();
    }

    public FriendNotification(String friendNotificationNO, String friendUserNO, String avatarPath, String nickname, String signature) {
        this.friendNotificationNO = friendNotificationNO;
        this.friendUserNO = friendUserNO;
        this.avatarPath = avatarPath;
        this.nickname = nickname;
        this.signature = signature;
    }

    public FriendNotification(String friendUserNO, String avatarPath, String nickname) {
        this.friendNotificationNO = UUID.randomUUID().toString();
        this.friendUserNO = friendUserNO;
        this.avatarPath = avatarPath;
        this.nickname = nickname;

    }

    public String getFriendNotificationNO() {
        return friendNotificationNO;
    }

    public void setFriendNotificationNO(String friendNotificationNO) {
        this.friendNotificationNO = friendNotificationNO;
    }

    public String getFriendUserNO() {
        return friendUserNO;
    }

    public void setFriendUserNO(String friendUserNO) {
        this.friendUserNO = friendUserNO;
    }

    public String getAvatarPath() {
        if (null==avatarPath){
            return "/icon/useridicon.jpg";
        }
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

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
