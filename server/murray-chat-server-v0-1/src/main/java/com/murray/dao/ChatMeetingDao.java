package com.murray.dao;

import com.murray.entity.ChatMeeting;
import com.murray.entity.example.ChatMeetingExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ChatMeetingDao extends MyBatisBaseDao<ChatMeeting,Long, ChatMeetingExample> {
}