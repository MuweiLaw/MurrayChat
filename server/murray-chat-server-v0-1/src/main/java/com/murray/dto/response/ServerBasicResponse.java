package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 服务器响应
 * @createTime 2020/11/13
 */
public class ServerBasicResponse extends Packet {
    private String status;
    private String title;
    private String message;

    public ServerBasicResponse() {
    }

    public ServerBasicResponse(String status, String title, String message) {
        this.status = status;
        this.title = title;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.SERVER_BASIC_RESPONSE;
    }
}
