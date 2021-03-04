package com.murray.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * chat_meeting
 * @author 
 */
public class ChatMeeting implements Serializable {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 会议提醒编号
     */
    private String meetingNo;

    /**
     * 主题
     */
    private String title;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getMeetingNo() {
        return meetingNo;
    }

    public void setMeetingNo(String meetingNo) {
        this.meetingNo = meetingNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}