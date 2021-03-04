package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 部门成员信息
 * @createTime 2020/12/8
 */
public class DepartmentMemberRequest extends Packet {
    private String requesterNo;
    private String departmentNo;
    private String departmentName;

    @Override
    public Byte getCommand() {
        return Command.DEPARTMENT_MEMBER_REQUEST;
    }

    public DepartmentMemberRequest() {
    }

    public DepartmentMemberRequest(String requesterNo, String departmentNo, String departmentName) {
        this.requesterNo = requesterNo;
        this.departmentNo = departmentNo;
        this.departmentName = departmentName;
    }

    public String getRequesterNo() {
        return requesterNo;
    }

    public void setRequesterNo(String requesterNo) {
        this.requesterNo = requesterNo;
    }

    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
