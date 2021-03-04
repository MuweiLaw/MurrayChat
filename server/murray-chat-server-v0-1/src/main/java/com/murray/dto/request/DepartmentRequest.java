package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 部门列表请求
 * @createTime 2020/12/7
 */
public class DepartmentRequest extends Packet {
    /**
     * 公司名称
     */
    private String company;
    /**
     * 请求者
     */
    private String requester;

    @Override
    public Byte getCommand() {
        return Command.DEPARTMENT_REQUEST;
    }
}
