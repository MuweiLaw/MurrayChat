package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.util.List;
import java.util.UUID;

/**
 * @author Murray Law
 * @describe 创建群聊请求
 * @createTime 2020/11/8
 */
public class CreateGroupRequest extends Packet {
    private String groupNO;

    private String groupName;

    private Integer maximum;//最大群成员人数

    private String groupOwnerNo;//群主编号

    private List<String> memberList;//成员列表

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }

    public CreateGroupRequest() {
        this.groupNO = UUID.randomUUID().toString();
    }

    public CreateGroupRequest(String groupName, Integer maximum, String groupOwnerNo, List<String> memberList) {
        this.groupNO = UUID.randomUUID().toString();
        this.groupName = groupName;
        this.maximum = maximum;
        this.groupOwnerNo = groupOwnerNo;
        this.memberList = memberList;

    }

    public String getGroupNO() {
        return groupNO;
    }

    public void setGroupNO(String groupNO) {
        this.groupNO = groupNO;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public String getGroupOwnerNo() {
        return groupOwnerNo;
    }

    public void setGroupOwnerNo(String groupOwnerNo) {
        this.groupOwnerNo = groupOwnerNo;
    }

    public List<String> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<String> memberList) {
        this.memberList = memberList;
    }
}
