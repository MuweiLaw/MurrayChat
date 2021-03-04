package com.murray.config;

import com.murray.server.MeetingReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 后续备用
 */
@Configuration
public class QuartzConfig {

    /**
     * @description 此方法非Quartz 使用boot自带的定时器
     * @author Murray Law
     * @date 2020/11/24 16:39
     * @return void
     */
    @Autowired
    MeetingReminderService reminderService;

    /**
     * @param
     * @return void
     * @description 每隔10秒钟推送一次
     * @author Murray Law
     * @date 2020/11/24 19:10
     */
    @Scheduled(initialDelay = 10000, fixedRate = 5000)
    public void initialDelay() {
        reminderService.remind();
    }
}