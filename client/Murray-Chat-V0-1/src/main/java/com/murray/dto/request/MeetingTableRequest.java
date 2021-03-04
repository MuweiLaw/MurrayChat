package com.murray.dto.request;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.util.Date;

/**
 * @author Murray Law
 * @describe 会议表格请求
 * @createTime 2020/11/25
 */
public class MeetingTableRequest extends Packet {
    private Date startedTime;
    private Date endTime;
    private String userNo;

    public MeetingTableRequest() {
    }

    public MeetingTableRequest(Date startedTime, Date endTime, String userNo) {
        this.startedTime = startedTime;
        this.endTime = endTime;
        this.userNo = userNo;
    }

    public Date getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Date startedTime) {
        this.startedTime = startedTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Override
    public Byte getCommand() {
        return Command.MEETING_TABLE_REQUEST;
    }
}
