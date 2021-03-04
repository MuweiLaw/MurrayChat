package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.util.Date;
import java.util.Map;

/**
 * @author Murray Law
 * @describe 发送邮件请求
 * @createTime 2020/11/11
 */
public class MailRequest extends Packet {
    private String mailNo;//邮件编号

    private String[] recipientArray;//邮件收件人数组

    private String[] ccArray;//邮件抄送人数组

    private String mailTitle;//主题

    private String mailText;//正文

    private String mailSenderNo;//邮件发件人编号

    private Map<String, String> fileMap;//文件编号和文件名称的映射Map

    private Date sendTime;//邮件的发出时间

    private boolean saveDraft;//是否保存草稿

    public MailRequest() {
    }

    public MailRequest( String[] recipientArray, String[] ccArray, String mailTitle, String mailText, String mailSenderNo, Map<String, String> fileMap, Date sendTime, boolean saveDraft) {
        this.recipientArray = recipientArray;
        this.ccArray = ccArray;
        this.mailTitle = mailTitle;
        this.mailText = mailText;
        this.mailSenderNo = mailSenderNo;
        this.fileMap = fileMap;
        this.sendTime = sendTime;
        this.saveDraft = saveDraft;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String[] getRecipientArray() {
        return recipientArray;
    }

    public void setRecipientArray(String[] recipientArray) {
        this.recipientArray = recipientArray;
    }

    public String[] getCcArray() {
        return ccArray;
    }

    public void setCcArray(String[] ccArray) {
        this.ccArray = ccArray;
    }

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getMailText() {
        return mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    public String getMailSenderNo() {
        return mailSenderNo;
    }

    public void setMailSenderNo(String mailSenderNo) {
        this.mailSenderNo = mailSenderNo;
    }

    public Map<String, String> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<String, String> fileMap) {
        this.fileMap = fileMap;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public boolean isSaveDraft() {
        return saveDraft;
    }

    public void setSaveDraft(boolean saveDraft) {
        this.saveDraft = saveDraft;
    }

    @Override
    public Byte getCommand() {
        return Command.MAIL_REQUEST;
    }
}
