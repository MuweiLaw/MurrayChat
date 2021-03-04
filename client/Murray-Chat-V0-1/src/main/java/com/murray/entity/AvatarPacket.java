package com.murray.entity;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.io.File;

/**
 * @author Murray Law
 * @describe 头像数据包
 * @createTime 2020/11/16
 */
public class AvatarPacket extends Packet {
    private File avatar;
    private String avatarNo;
    private String avatarName;

    public AvatarPacket(File avatar) {
        this.avatar = avatar;
    }

    public File getAvatar() {
        return avatar;
    }

    public void setAvatar(File avatar) {
        this.avatar = avatar;
    }

    public String getAvatarNo() {
        return avatarNo;
    }

    public void setAvatarNo(String avatarNo) {
        this.avatarNo = avatarNo;
    }

    public int getAvatarNameLength() {
        return  this.avatarName.length();
    }


    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    @Override
    public Byte getCommand() {
        return Command.AVATAR;
    }
}
