package com.murray.server;

import com.murray.dao.ChatMeetingDao;
import com.murray.dao.ChatUserMeetingDao;
import com.murray.dto.request.MeetingReminderRequest;
import com.murray.dto.request.MeetingTableRequest;
import com.murray.dto.response.MeetingTableCell;
import com.murray.dto.response.MeetingTableResponse;
import com.murray.entity.ChatMeeting;
import com.murray.entity.ChatUserMeeting;
import com.murray.entity.example.ChatMeetingExample;
import com.murray.entity.example.ChatUserMeetingExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Murray Law
 * @describe 会议service
 * @createTime 2020/11/24
 */
@Service
@CacheConfig(cacheNames = "chatMeeting")
public class ChatMeetingService {
    @Autowired
    private ChatMeetingDao chatMeetingDao;
    @Autowired
    private ChatUserMeetingDao chatUserMeetingDao;
    @Autowired
    private ChatUserMeetingService chatUserMeetingService;
    private ChatMeetingExample meetingExample = new ChatMeetingExample();
    private ChatUserMeetingExample userMeetingExample = new ChatUserMeetingExample();

    /**
     * @param reminderRequest 会议提醒请求
     * @return int
     * @description 会议表 和 用户会议映射表 插入数据
     * @author Murray Law
     * @date 2020/11/24 18:05
     */
    @Transactional
    public int insert(MeetingReminderRequest reminderRequest) {
        Date date = new Date();
        //复制属性并传入数据库
        ChatMeeting chatMeeting = new ChatMeeting();
        BeanUtils.copyProperties(reminderRequest, chatMeeting);
        chatMeeting.setMeetingNo(reminderRequest.getMeetingRemindNo());
        //创建者插入到映射表中
        ChatUserMeeting chatUserMeeting = new ChatUserMeeting(reminderRequest.getCreator(), reminderRequest.getMeetingRemindNo(), false, date);
        chatUserMeetingService.insert(chatUserMeeting);
        //插入数据到用户和会议映射表中
        reminderRequest.getUserList().forEach(userNo -> {
            chatUserMeeting.setMeetingNo(reminderRequest.getMeetingRemindNo());
            chatUserMeeting.setCreateTime(date);
            chatUserMeeting.setUserNo(userNo);
            chatUserMeeting.setIsAnswer(false);
            chatUserMeetingDao.insert(chatUserMeeting);

        });
        //返回建立的会议数量
        return chatMeetingDao.insert(chatMeeting);
    }

    /**
     * @param meetingNo 会议编号
     * @return java.util.List<com.murray.entity.ChatMeeting>
     * @description 根据会议编号查询
     * @author Murray Law
     * @date 2020/11/24 19:00
     */
    @Cacheable(value = {"chatMeeting"}, key = "#meetingNo")
    public List<ChatMeeting> selectByMeetingNo(String meetingNo) {
        meetingExample.clear();
        meetingExample.createCriteria().andMeetingNoEqualTo(meetingNo);
        return chatMeetingDao.selectByExample(meetingExample);
    }
    /**
     * @description 查询出会议表格需要的数据，转换成数据包
     * @author Murray Law
     * @date 2020/11/25 16:58
     * @param MTRequest 请求
     * @return com.murray.dto.response.MeetingTableResponse
     */
    @Transactional
    public MeetingTableResponse getMeetingTable(MeetingTableRequest MTRequest){
        ArrayList<MeetingTableCell> meetingTableCells = new ArrayList<>();
        meetingExample.clear();//清空条件
        //查询时间范围内的会议
        meetingExample.createCriteria().andStartTimeBetween(MTRequest.getStartedTime(),MTRequest.getEndTime());
        List<ChatMeeting> chatMeetings = chatMeetingDao.selectByExample(meetingExample);

        chatMeetings.forEach(chatMeeting -> {
            userMeetingExample.clear();
            userMeetingExample.createCriteria().andUserNoEqualTo(MTRequest.getUserNo())
                    .andMeetingNoEqualTo(chatMeeting.getMeetingNo());
            List<ChatUserMeeting> chatUserMeetings = chatUserMeetingDao.selectByExample(userMeetingExample);
            //如果有此用户编号的会议数据则加入列表
            if (!chatUserMeetings.isEmpty()){
                meetingTableCells.add(new MeetingTableCell(MTRequest.getUserNo(),chatMeeting.getMeetingNo(), chatMeeting.getTitle(),chatMeeting.getLocation(),chatMeeting.getStartTime()));
            }
        });
        return new MeetingTableResponse(meetingTableCells);
    }
}
