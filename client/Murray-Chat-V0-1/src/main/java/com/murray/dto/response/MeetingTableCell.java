package com.murray.dto.response;

import java.util.Date;

public  class MeetingTableCell {
        /**
         * 用户编号
         */
        private String userNo;
        /**
         * 会议编号
         */
        private String meetingNo;
        /**
         * 主题
         */
        private String title;

        /**
         * 会议地点
         */
        private String location;
        /**
         * 会议开始时间
         */
        private Date startTime;

        public MeetingTableCell() {
        }

        public MeetingTableCell(String userNo, String meetingNo, String title, String location, Date startTime) {
            this.userNo = userNo;
            this.meetingNo = meetingNo;
            this.title = title;
            this.location = location;
            this.startTime = startTime;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }
    }