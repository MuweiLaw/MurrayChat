package com.murray.dao;


import com.murray.entity.ChatUserMeeting;
import com.murray.entity.example.ChatUserMeetingExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ChatUserMeetingDao extends MyBatisBaseDao<ChatUserMeeting, Long, ChatUserMeetingExample> {}