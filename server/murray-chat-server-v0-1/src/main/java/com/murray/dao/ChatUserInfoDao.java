package com.murray.dao;

import com.murray.entity.ChatUserInfo;
import com.murray.entity.example.ChatUserInfoExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ChatUserInfoDao extends MyBatisBaseDao<ChatUserInfo,Long, ChatUserInfoExample>{}