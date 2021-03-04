package com.murray.view.dto;

import java.util.Date;

/**
 * @author Murray Law
 * @describe 主面板上的消息
 * @createTime 2020/10/27
 */
public class Notification {
    private String notificationNO;
    private String nickname;
    private String message;
    private Boolean isAt;
    private Date issueTime;
    private Integer total;
    private Byte openType;//打开的窗口类型

    public Notification() {
    }

    public Notification(String notificationNO, boolean isAt, String message, Date issueTime, Integer total,Byte openType) {
        this.notificationNO = notificationNO;
        this.isAt = isAt;
        this.message = message;
        this.issueTime = issueTime;
        this.total = total;
        this.openType = openType;

    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationNO='" + notificationNO + '\'' +
                ", nickname='" + nickname + '\'' +
                ", nickname='" + isAt + '\'' +
                ", message='" + message + '\'' +
                ", issueTime=" + issueTime +
                ", total='" + total + '\'' +
                '}';
    }

    //实现compareTo方法,按提交时间排序
//    @Override
//    public int compareTo(Object o) {
//        Date issueTime1 = this.issueTime;
//        Date issueTime2 = ((Notification) o).issueTime;
//
//        if (issueTime1.getTime() > issueTime2.getTime()) {
//            return -1;
//        } else if (issueTime1.getTime() < issueTime2.getTime()) {
//            return 1;
//        } else {
//            return 0;
//        }
//    }

    public Boolean getAt() {
        return isAt;
    }

    public void setAt(Boolean at) {
        isAt = at;
    }

    public String getNotificationNO() {
        return notificationNO;
    }

    public void setNotificationNO(String notificationNO) {
        this.notificationNO = notificationNO;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Byte getOpenType() {
        return openType;
    }

    public void setOpenType(Byte openType) {
        this.openType = openType;
    }

}
