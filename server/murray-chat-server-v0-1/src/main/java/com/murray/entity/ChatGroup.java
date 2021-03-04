package com.murray.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * chat_group
 * @author 
 */
public class ChatGroup implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 群编号
     */
    private String groupNo;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群公告
     */
    private String groupNotice;

    /**
     * 最大可容量人数
     */
    private Integer maximum;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建的时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public ChatGroup() {
    }

    public ChatGroup(Long id, String groupNo, String groupName, String groupNotice, Integer maximum, String creator, Date createTime, Date updateTime) {
        this.id = id;
        this.groupNo = groupNo;
        this.groupName = groupName;
        this.groupNotice = groupNotice;
        this.maximum = maximum;
        this.creator = creator;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupNotice() {
        return groupNotice;
    }

    public void setGroupNotice(String groupNotice) {
        this.groupNotice = groupNotice;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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