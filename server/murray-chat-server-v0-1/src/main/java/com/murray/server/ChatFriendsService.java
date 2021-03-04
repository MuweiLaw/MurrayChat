package com.murray.server;

import com.murray.dao.ChatFriendsDao;
import com.murray.dto.response.ChatFriendResponse;
import com.murray.entity.ChatFriends;
import com.murray.entity.example.ChatFriendsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Murray Law
 * @describe 好友信息
 * @createTime 2020/11/2
 */
@Service
public class ChatFriendsService {
    @Autowired
    ChatFriendsDao chatFriendsDao;
    private ChatFriendsExample friendsExample = new ChatFriendsExample();

    /**
     * @param chatUserNo 用户编号
     * @return java.util.List<com.murray.entity.ChatFriends>
     * @description 根据用户编号查找好友信息
     * @author Murray Law
     * @date 2020/11/19 16:17
     */
    public List<ChatFriends> selectByChatUser(String chatUserNo) {
        friendsExample.clear();
        friendsExample.createCriteria().andChatUserNoEqualTo(chatUserNo);
        return chatFriendsDao.selectByExample(friendsExample);
    }

    /**
     * @param chatUserNo 用户编号
     * @return java.util.List<com.murray.entity.ChatFriends>
     * @description 根据用户编号查找好友详细信息
     * @author Murray Law
     * @date 2020/11/19 16:17
     */
    public List<ChatFriendResponse> selectFriendsByChatUserNO(String chatUserNo) {
        return chatFriendsDao.selectFriendsByChatUserNO(chatUserNo);
    }

    /**
     * @param userNo 用户编号
     * @return java.lang.Long
     * @description 根据用户编号查询好友数量
     * @author Murray Law
     * @date 2020/12/8 7:52
     */
    public Long countByUserNo(String userNo) {
        friendsExample.clear();
        friendsExample.createCriteria().andChatUserNoEqualTo(userNo);
        return chatFriendsDao.countByExample(friendsExample);
    }
}
