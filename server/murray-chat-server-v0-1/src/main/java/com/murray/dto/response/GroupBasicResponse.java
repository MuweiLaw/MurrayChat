package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.util.Map;

/**
 * @author Murray Law
 * @describe 群聊基本信息的响应头
 * @createTime 2020/11/10
 */
public class GroupBasicResponse extends Packet {
    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 群map
     */
    private Map<String, GroupBasic> groupMap;

    @Override
    public Byte getCommand() {
        return Command.GROUP_BASIC_RESPONSE;
    }

    public GroupBasicResponse() {
    }

    public GroupBasicResponse(String userNo, Map<String, GroupBasic> groupMap) {
        this.userNo = userNo;
        this.groupMap = groupMap;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Map<String, GroupBasic> getGroupMap() {
        return groupMap;
    }

    public void setGroupMap(Map<String, GroupBasic> groupMap) {
        this.groupMap = groupMap;
    }
}
