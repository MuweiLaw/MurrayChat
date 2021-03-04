package com.murray.dao;

import com.murray.entity.ChatGroup;
import com.murray.entity.example.ChatGroupExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ChatGroupDao extends MyBatisBaseDao<ChatGroup,Long, ChatGroupExample> {}