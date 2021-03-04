package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 请求下载头像
 * @createTime 2020/11/16
 */
public class AvatarRequest extends Packet {
    private String requesterUserNo;
    private String queryUserNo;//要查询的用户编号

    public AvatarRequest() {
    }

    public AvatarRequest(String requesterUserNo, String queryUserNo, String avatarType) {
        this.requesterUserNo = requesterUserNo;
        this.queryUserNo = queryUserNo;

    }

    public String getRequesterUserNo() {
        return requesterUserNo;
    }

    public void setRequesterUserNo(String requesterUserNo) {
        this.requesterUserNo = requesterUserNo;
    }

    public String getQueryUserNo() {
        return queryUserNo;
    }

    public void setQueryUserNo(String queryUserNo) {
        this.queryUserNo = queryUserNo;
    }


    @Override
    public Byte getCommand() {
        return Command.AVATAR_REQUEST;
    }
}
