package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.util.Map;

/**
 * @author Murray Law
 * @describe 通讯录请求
 * @createTime 2020/12/8
 */
public class AddressBookResponse extends Packet {
    /**
     * 好友总数
     */
    private Long friendSum;
    /**
     * 群组总数
     */
    private Long groupSum;
    /**
     * 部门map
     */
    private Map<String,DepartmentResponse> departmentMap;
    @Override
    public Byte getCommand() {
        return Command.ADDRESS_BOOK_RESPONSE;
    }

    public AddressBookResponse() {
    }

    public AddressBookResponse(Long friendSum, Long groupSum, Map<String, DepartmentResponse> departmentMap) {
        this.friendSum = friendSum;
        this.groupSum = groupSum;
        this.departmentMap = departmentMap;
    }

    public Long getFriendSum() {
        return friendSum;
    }

    public void setFriendSum(Long friendSum) {
        this.friendSum = friendSum;
    }

    public Long getGroupSum() {
        return groupSum;
    }

    public void setGroupSum(Long groupSum) {
        this.groupSum = groupSum;
    }

    public Map<String, DepartmentResponse> getDepartmentMap() {
        return departmentMap;
    }

    public void setDepartmentMap(Map<String, DepartmentResponse> departmentMap) {
        this.departmentMap = departmentMap;
    }
}
