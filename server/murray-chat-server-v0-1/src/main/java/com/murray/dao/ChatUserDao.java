package com.murray.dao;

import com.murray.entity.ChatUser;
import com.murray.entity.example.ChatUserExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ChatUserDao extends MyBatisBaseDao<ChatUser,Long, ChatUserExample>{
}