package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

/**
 * @author Murray Law
 * @describe 服务器已接受的响应体
 * @createTime 2020/11/5
 */
public class ServerReceivedResponse extends Packet {
    private String msgNO;
    private Byte percentage;
    private String status;

    public ServerReceivedResponse(String msgNO, Byte percentage, String status) {
        this.msgNO = msgNO;
        this.percentage = percentage;
        this.status = status;
    }

    public String getMsgNO() {
        return msgNO;
    }

    public void setMsgNO(String msgNO) {
        this.msgNO = msgNO;
    }

    public Byte getPercentage() {
        return percentage;
    }

    public void setPercentage(Byte percentage) {
        this.percentage = percentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Byte getCommand() {
        return Command.SERVER_RECEIVED_RESPONSE;
    }
}
