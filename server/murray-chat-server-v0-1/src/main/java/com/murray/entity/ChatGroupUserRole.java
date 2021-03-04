package com.murray.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * chat_group_user_role
 * @author 
 */
public class ChatGroupUserRole implements Serializable {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 群编号
     */
    private String groupNo;

    /**
     * 用户在群里的角色0/群主  1/管理员  2/普通成员
     */
    private Byte role;

    /**
     * 角色的创建日期
     */
    private Date createDate;

    /**
     * 角色的更新日期
     */
    private Date updateDate;

    private static final long serialVersionUID = 1L;

    public ChatGroupUserRole() {
    }

    public ChatGroupUserRole(Long id, String userNo, String groupNo, Byte role, Date createDate, Date updateDate) {
        this.id = id;
        this.userNo = userNo;
        this.groupNo = groupNo;
        this.role = role;
        this.createDate = createDate;
        this.updateDate = updateDate;
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

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}