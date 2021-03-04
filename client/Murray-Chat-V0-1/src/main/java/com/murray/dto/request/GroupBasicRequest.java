package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 部门成员信息
 * @createTime 2020/12/8
 */
public class GroupBasicRequest extends Packet {
    private String requesterNo;
    private Byte GroupType;//群组类型,1我创建的/2我管理的/3我参与的

    @Override
    public Byte getCommand() {
        return Command.GROUP_BASIC_REQUEST;
    }

    public GroupBasicRequest() {
    }

    public GroupBasicRequest(String requesterNo, Byte groupType) {
        this.requesterNo = requesterNo;
        GroupType = groupType;
    }

    public Byte getGroupType() {
        return GroupType;
    }

    public void setGroupType(Byte groupType) {
        GroupType = groupType;
    }

    public String getRequesterNo() {
        return requesterNo;
    }

    public void setRequesterNo(String requesterNo) {
        this.requesterNo = requesterNo;
    }
}
