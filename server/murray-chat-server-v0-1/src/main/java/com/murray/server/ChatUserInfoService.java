package com.murray.server;

import com.murray.dao.ChatUserInfoDao;
import com.murray.entity.ChatUserInfo;
import com.murray.entity.example.ChatUserInfoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Murray Law
 * @describe 用户信息服务
 * @createTime 2020/11/13
 */
@Service
@CacheConfig()
public class ChatUserInfoService {
    @Autowired
    private ChatUserInfoDao chatUserInfoDao;
    private ChatUserInfoExample chatUserInfoExample = new ChatUserInfoExample();

    /**
     * @param userNo 用户编号
     * @return com.murray.entity.ChatUserInfo
     * @description 根据用户编号查找用户信息
     * @author Murray Law
     * @date 2020/11/19 16:03
     */
    @Cacheable(value = {"chatUserInfo"}, key = "#userNo")
    public ChatUserInfo selectByUserNo(String userNo) {
        chatUserInfoExample.clear();
        chatUserInfoExample.createCriteria().andUserNoEqualTo(userNo);
        List<ChatUserInfo> userInfoList = chatUserInfoDao.selectByExample(chatUserInfoExample);
        if (userInfoList.isEmpty()) {
            return new ChatUserInfo();
        }
        return userInfoList.get(0);
    }

    /**
     * @param mail 邮箱号
     * @return com.murray.entity.ChatUserInfo
     * @description 根据用户编号查找用户信息
     * @author Murray Law
     * @date 2020/11/19 16:03
     */
    public ChatUserInfo selectByMail(String mail) {
        chatUserInfoExample.clear();
        chatUserInfoExample.createCriteria().andMailEqualTo(mail);
        List<ChatUserInfo> userInfoList = chatUserInfoDao.selectByExample(chatUserInfoExample);
        if (userInfoList.isEmpty()) {
            return null;
        }
        return userInfoList.get(0);
    }

    public int insert(ChatUserInfo chatUserInfo) {
       return chatUserInfoDao.insert(chatUserInfo);
    }
}