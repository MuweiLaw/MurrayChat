package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.util.Map;

/**
 * @author Murray Law
 * @describe 部门成员信息响应
 * @createTime 2020/12/8
 */
public class DepartmentMemberResponse extends Packet {
    private String DepartmentNo;
    private Map<String, ChatUserInfoResponse>  departmentMemberMap;

    @Override
    public Byte getCommand() {
        return Command.DEPARTMENT_MEMBER_RESPONSE;
    }

    public DepartmentMemberResponse() {
    }

    public DepartmentMemberResponse(String departmentNo, Map<String, ChatUserInfoResponse> departmentMemberMap) {
        DepartmentNo = departmentNo;
        this.departmentMemberMap = departmentMemberMap;
    }

    public String getDepartmentNo() {
        return DepartmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        DepartmentNo = departmentNo;
    }

    public Map<String, ChatUserInfoResponse> getDepartmentMemberMap() {
        return departmentMemberMap;
    }

    public void setDepartmentMemberMap(Map<String, ChatUserInfoResponse> departmentMemberMap) {
        this.departmentMemberMap = departmentMemberMap;
    }
}
