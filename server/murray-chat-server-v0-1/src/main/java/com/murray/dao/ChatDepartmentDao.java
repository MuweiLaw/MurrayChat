package com.murray.dao;

import com.murray.entity.ChatDepartment;
import com.murray.entity.example.ChatDepartmentExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ChatDepartmentDao extends MyBatisBaseDao<ChatDepartment,Long, ChatDepartmentExample>{
}