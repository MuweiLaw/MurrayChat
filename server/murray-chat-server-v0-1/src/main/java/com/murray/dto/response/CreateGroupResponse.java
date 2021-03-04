package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 创建群聊的响应
 * @createTime 2020/11/8
 */
public class CreateGroupResponse extends Packet {
    String groupNo;
    String status;//状态码

    public CreateGroupResponse(String groupNo,String status) {
        this.groupNo = groupNo;
        this.status = status;

    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
