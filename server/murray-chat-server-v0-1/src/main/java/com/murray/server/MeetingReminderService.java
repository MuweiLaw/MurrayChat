package com.murray.server;

import com.murray.dto.response.MeetingReminderResponse;
import com.murray.entity.ChatMeeting;
import com.murray.entity.ChatUserMeeting;
import com.murray.utils.SessionUtil;
import io.netty.channel.Channel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.murray.server.netty.ServerPacketCodeCompile.encode;

/**
 * @author Murray Law
 * @describe 会议提醒服务
 * @createTime 2020/11/24
 */
@Component
public class MeetingReminderService {
    @Autowired
    private ChatMeetingService meetingService;
    @Autowired
    private ChatUserMeetingService userMeetingService;


    public void remind() {
        Date now = new Date();
        List<ChatUserMeeting> chatUserMeetings = userMeetingService.selectByIsAnswer(false);
        chatUserMeetings.forEach(chatUserMeeting -> {
            ChatMeeting chatMeeting = meetingService.selectByMeetingNo(chatUserMeeting.getMeetingNo()).get(0);
            long timeDiff = chatMeeting.getStartTime().getTime() - now.getTime();
            if ((0 < timeDiff && timeDiff < 600000)) {
                Channel receiverChannel = SessionUtil.getChannel(chatUserMeeting.getUserNo());
                if (null != receiverChannel) {
                    MeetingReminderResponse meetingReminderResponse = new MeetingReminderResponse();
                    BeanUtils.copyProperties(chatMeeting, meetingReminderResponse);
                    meetingReminderResponse.setMeetingRemindNo(chatMeeting.getMeetingNo());
                    receiverChannel.writeAndFlush(encode(meetingReminderResponse));
                }
            }
        });
    }
}
