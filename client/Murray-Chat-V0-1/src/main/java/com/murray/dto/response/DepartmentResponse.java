package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 部门列表响应
 * @createTime 2020/12/7
 */
public class DepartmentResponse extends Packet {
    /**
     * 部门编号
     */
    private String departmentNo;

    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门人数
     */
    private Long sum;

    @Override
    public Byte getCommand() {
        return Command.DEPARTMENT_RESPONSE;
    }

    public DepartmentResponse() {
    }

    public DepartmentResponse(String departmentNo, String name, Long sum) {
        this.departmentNo = departmentNo;
        this.name = name;
        this.sum = sum;
    }

    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

}
