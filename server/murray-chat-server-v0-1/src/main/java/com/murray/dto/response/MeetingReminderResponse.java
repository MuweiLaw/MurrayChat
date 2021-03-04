package com.murray.dto.response;

import com.murray.agreement.Command;
import com.murray.agreement.Packet;

import java.util.Date;
import java.util.List;

/**
 * @author Murray Law
 * @describe 会议提醒请求
 * @createTime 2020/11/24
 */
public class MeetingReminderResponse extends Packet {
    /**
     * 会议提醒编号
     */
    private String meetingRemindNo;

    /**
     * 主题
     */
    private String title;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 会议开始时间
     */
    private Date startTime;

    /**
     * 会议内容描述
     */
    private String desc;

    /**
     * 会议地点
     */
    private String location;
    /**
     * 参与者列表
     */
    private List<String> userList;

    @Override
    public Byte getCommand() {
        return Command.MEETING_REMIND_RESPONSE;
    }

    public MeetingReminderResponse() {
    }

    public MeetingReminderResponse(String meetingRemindNo, String title, String creator, Date startTime, String desc, String location, List<String> userList) {
        this.meetingRemindNo = meetingRemindNo;
        this.title = title;
        this.creator = creator;
        this.startTime = startTime;
        this.desc = desc;
        this.location = location;
        this.userList = userList;
    }

    public String getMeetingRemindNo() {
        return meetingRemindNo;
    }

    public void setMeetingRemindNo(String meetingRemindNo) {
        this.meetingRemindNo = meetingRemindNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }
}
