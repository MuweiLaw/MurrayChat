package com.murray.dao;

import com.murray.dto.response.ChatFriendResponse;
import com.murray.entity.ChatFriends;
import com.murray.entity.example.ChatFriendsExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ChatFriendsDao extends MyBatisBaseDao<ChatFriends, Long, ChatFriendsExample> {
    /**
     * @param chatUserNo
     * @return java.util.List<com.murray.entity.ChatFriends>
     * @description 通过聊天用户编号查询朋友列表
     * @author Murray Law
     * @date 2020/11/19 11:28
     */
    List<ChatFriendResponse> selectFriendsByChatUserNO(String chatUserNo);

}