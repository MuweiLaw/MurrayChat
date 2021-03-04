package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;


/**
 * @author Murray Law
 * @describe 邮件的响应头
 * @createTime 2020/11/11
 */
public class MailResponse extends Packet {
    private String mailNo;//邮件编号

    private String mailAddressee;//邮件收件人

    private String mailTitle;//主题

    private boolean read;//读取了没

    private String status;//流程状态

    public MailResponse(String mailNo, String mailAddressee, String mailTitle, boolean read, String status) {
        this.mailNo = mailNo;
        this.mailAddressee = mailAddressee;
        this.mailTitle = mailTitle;
        this.read = read;
        this.status = status;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getMailAddressee() {
        return mailAddressee;
    }

    public void setMailAddressee(String mailAddressee) {
        this.mailAddressee = mailAddressee;
    }

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Byte getCommand() {
        return Command.MAIL_RESPONSE;
    }
}
