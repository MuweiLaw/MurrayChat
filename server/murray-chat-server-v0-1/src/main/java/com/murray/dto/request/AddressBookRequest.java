package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 通讯录请求
 * @createTime 2020/12/8
 */
public class AddressBookRequest extends Packet {
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
        return Command.ADDRESS_BOOK_REQUEST;
    }

    public AddressBookRequest() {
    }

    public AddressBookRequest(String company, String requester) {
        this.company = company;
        this.requester = requester;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }
}
