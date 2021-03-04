package com.murray.server;

import com.murray.dao.ChatUserMeetingDao;
import com.murray.dto.request.HaveReadRequest;
import com.murray.entity.ChatUserMeeting;
import com.murray.entity.example.ChatUserMeetingExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Murray Law
 * @describe 用户和会议的关联服务
 * @createTime 2020/11/24
 */
@Service
@CacheConfig
public class ChatUserMeetingService {
    @Autowired
    ChatUserMeetingDao chatUserMeetingDao;

    private ChatUserMeetingExample userMeetingExample = new ChatUserMeetingExample();

    /**
     * @param b 是否回复
     * @return java.util.List<com.murray.entity.ChatUserMeeting>
     * @description 根据是否回复来进行查询
     * @author Murray Law
     * @date 2020/11/24 18:26
     */
    @Cacheable(value = {"chatUserMeeting"}, key = "#b")
    public List<ChatUserMeeting> selectByIsAnswer(boolean b) {
        userMeetingExample.clear();
        userMeetingExample.createCriteria().andIsAnswerEqualTo(b);
        return chatUserMeetingDao.selectByExample(userMeetingExample);
    }
    public List<ChatUserMeeting> selectByUserNoAndIsAnswer(String userNo,boolean b) {
        userMeetingExample.clear();
        userMeetingExample.createCriteria().andIsAnswerEqualTo(b)
        .andUserNoEqualTo(userNo);
        return chatUserMeetingDao.selectByExample(userMeetingExample);
    }
    /**
     * @description 此方法为更新缓存用法,插入的同时刷新
     * @author Murray Law
     * @date 2020/11/25 19:30
     * @param chatUserMeeting
     * @return java.util.List<com.murray.entity.ChatUserMeeting>
     */
    @CachePut(value = "chatUserMeeting", key = "#chatUserMeeting.isAnswer")
    public List<ChatUserMeeting> insert(ChatUserMeeting chatUserMeeting) {
        chatUserMeetingDao.insert(chatUserMeeting);
        List<ChatUserMeeting> chatUserMeetings = new ArrayList<>();
        chatUserMeetings.add(chatUserMeeting);
        return chatUserMeetings;
    }

    /**
     * @param haveReadRequest 已读请求
     * @return java.util.List<com.murray.entity.ChatUserMeeting>
     * @description 根据是否回复来进行查询
     * @author Murray Law
     * @date 2020/11/24 18:26
     */
    @CacheEvict(value = "chatUserMeeting", key = "#delKey",allEntries=true)
    public int updateByUserNoAndMeetingNo(HaveReadRequest haveReadRequest,Boolean delKey) {
        //获取变量
        String userNo = haveReadRequest.getSenderUserNO();
        String meetingNo = haveReadRequest.getChatMessageNO();
        Date date = new Date();
        //例子清空条件
        userMeetingExample.clear();
        //给例子复制用户编号和会议编号
        userMeetingExample.createCriteria()
                .andUserNoEqualTo(userNo)
                .andMeetingNoEqualTo(meetingNo);
        ChatUserMeeting chatUserMeeting = new ChatUserMeeting();
        chatUserMeeting.setUserNo(userNo);
        chatUserMeeting.setIsAnswer(true);
        chatUserMeeting.setAnswerTime(date);
        chatUserMeeting.setUpdateTime(date);
        //按条件更新指定字段
        return chatUserMeetingDao.updateByExampleSelective(chatUserMeeting, userMeetingExample);
    }

}
