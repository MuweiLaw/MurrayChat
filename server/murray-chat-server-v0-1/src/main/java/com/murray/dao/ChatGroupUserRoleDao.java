package com.murray.dao;

import com.murray.entity.ChatGroupUserRole;
import com.murray.entity.example.ChatGroupUserRoleExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ChatGroupUserRoleDao extends MyBatisBaseDao<ChatGroupUserRole, Long, ChatGroupUserRoleExample> {

    int insertList(List<ChatGroupUserRole> chatGroupUserRoles);

    List<ChatGroupUserRole> selectBasicAll();

    List<String> selectUserNoByGroupNo(String groupNo);
}