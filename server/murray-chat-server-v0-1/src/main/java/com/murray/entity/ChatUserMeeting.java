package com.murray.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * chat_user_meeting
 * @author 
 */
public class ChatUserMeeting implements Serializable {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 会议编号
     */
    private String meetingNo;

    /**
     * 0为未回复1为回复了
     */
    private Boolean isAnswer;

    /**
     * 回复时间
     */
    private Date answerTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public ChatUserMeeting() {
    }

    public ChatUserMeeting(String userNo, String meetingNo, Boolean isAnswer,Date createTime) {
        this.userNo = userNo;
        this.meetingNo = meetingNo;
        this.isAnswer = isAnswer;
        this.createTime = createTime;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getMeetingNo() {
        return meetingNo;
    }

    public void setMeetingNo(String meetingNo) {
        this.meetingNo = meetingNo;
    }

    public Boolean getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(Boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
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