package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 添加群成员请求
 * @createTime 2020/12/25
 */
public class OperateGroupRequest extends Packet {
    private String requesterNo;

    private Byte requesterRole;

    private String groupNo;

    private String groupName;

    private String operateUserNo;

    private Byte operateUserRole;

    private String operateType;

    public OperateGroupRequest(String requesterNo, Byte requesterRole, String groupNo, String groupName, String operateUserNo, Byte operateUserRole, String operateType) {
        this.requesterNo = requesterNo;
        this.requesterRole = requesterRole;
        this.groupNo = groupNo;
        this.groupName = groupName;
        this.operateUserNo = operateUserNo;
        this.operateUserRole = operateUserRole;
        this.operateType = operateType;
    }

    public String getRequesterNo() {
        return requesterNo;
    }

    public void setRequesterNo(String requesterNo) {
        this.requesterNo = requesterNo;
    }

    public Byte getRequesterRole() {
        return requesterRole;
    }

    public void setRequesterRole(Byte requesterRole) {
        this.requesterRole = requesterRole;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOperateUserNo() {
        return operateUserNo;
    }

    public void setOperateUserNo(String operateUserNo) {
        this.operateUserNo = operateUserNo;
    }

    public Byte getOperateUserRole() {
        return operateUserRole;
    }

    public void setOperateUserRole(Byte operateUserRole) {
        this.operateUserRole = operateUserRole;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    @Override
    public Byte getCommand() {
        return Command.OPERATE_GROUP_REQUEST;
    }
}
