package com.murray.view.vo.cell;

import com.murray.dto.response.GroupBasic;
import com.murray.utils.DateUtil;
import com.murray.utils.PictureManipulationUtil;
import com.murray.view.dto.Notification;
import io.netty.util.internal.StringUtil;

import javax.swing.*;
import java.awt.*;

import static com.murray.cache.ClientCache.*;
import static com.murray.utils.ColorUtil.AT_COLOR;
import static com.murray.utils.FontUtil.*;

/**
 * @author Murray Law
 * @describe 消息单元
 * @createTime 2020/12/2
 */
public class MsgCell extends JPanel {
    public MsgCell(Notification notification) {
        removeAll();
        setLayout(null);
        setOpaque(true);
        setBackground(Color.white);
        //初始化
        int x = 80, atX = 0;
        this.removeAll();//妈的,超大坑
        switch (notification.getOpenType()) {
            case 0:
                //设置头像 显示备注或昵称或编号
                setUserNameAndAvatar(notification, x);
                //设置预览的最后一条消息时间
                setTimeOfLastMsg(notification);
                //设置预览的最后一条消息内容
                setMsg(x, atX, notification);
                //设置未读消息"红圆点"
                setTotal(notification);
                break;
            case 1:
                setGroupNameAndAvatar(notification, x);
                setTimeOfLastMsg(notification);
                //设置有人@我
                atX = setAt(atX, notification);
                setMsg(x, atX, notification);
                setTotal(notification);
                break;
        }


    }

    /**
     * @param x
     * @param atX
     * @param notification
     * @return void
     * @description 设置消息内容
     * @author Murray Law
     * @date 2020/11/18 20:14
     */
    private void setMsg(int x, int atX, Notification notification) {
        JLabel messagePreviewLabel = new JLabel();
        messagePreviewLabel.setText(notification.getMessage());
        messagePreviewLabel.setBounds(x + atX, 35, 190 - atX, 30);
        messagePreviewLabel.setFont(ITALICS_P_16);
        messagePreviewLabel.setForeground(Color.gray);
        add(messagePreviewLabel);
    }

    private int setAt(int atX, Notification notification) {
        if (notification.getAt()) {
            atX = 80;
            JLabel atLabel = new JLabel("[有人@我]");
            atLabel.setBounds(atX, 35, 80, 30);
            atLabel.setFont(ITALICS_B_15);
            atLabel.setForeground(AT_COLOR);
            add(atLabel);
        }
        return atX;
    }

    private void setTimeOfLastMsg(Notification notification) {
        JLabel dateLabel = new JLabel(DateUtil.getDateString(notification.getIssueTime()),JLabel.CENTER);
        dateLabel.setBounds(235, 5, 100, 30);
        dateLabel.setFont(ITALICS_P_12);
        dateLabel.setForeground(Color.gray);
        add(dateLabel);
    }

    /**
     * @param notification
     * @return void
     * @description 设置通知数量
     * @author Murray Law
     * @date 2020/11/18 18:30
     */
    private void setTotal(Notification notification) {
        ImageIcon dotsBG = PictureManipulationUtil.getImageIcon("icon/circle.jpg", 20, 20);
        JLabel dots = new JLabel(dotsBG);
        //显示99+
        if (notification.getTotal() > 99) {
            dots.setText("99+");
        } else {
            dots.setText(notification.getTotal().toString());
        }
        dots.setHorizontalTextPosition(JLabel.CENTER);
        dots.setVerticalTextPosition(JLabel.CENTER);
        dots.setForeground(Color.white);
        dots.setBounds(275, 35, 30, 30);
        //表示已读
        if (notification.getTotal() == 0) {
            this.remove(dots);
        }else add(dots);

    }

    /**
     * @param notification 通知对象
     * @return void
     * @description 设置通知的用户名
     * @author Murray Law
     * @date 2020/11/18 18:24
     */
    private void setUserNameAndAvatar(Notification notification, int x) {
        //头像框
        JLabel avatarFrameLabel = new JLabel();
        avatarFrameLabel.setIcon(AVATAR_FRAME_WHITE);
        avatarFrameLabel.setBounds(20, 10, 50, 50);
        add(avatarFrameLabel);
        ImageIcon profilePicture = PictureManipulationUtil.getUserAvatar(notification.getNotificationNO(), 50, 50);
        JLabel profilePictureLabel = new JLabel();
        profilePictureLabel.setIcon(profilePicture);
        profilePictureLabel.setBounds(20, 10, 50, 50);
        add(profilePictureLabel);

        //设置用户名
        String userNo = notification.getNotificationNO();//默认显示为账号名
        String userName = userNo;
        //其次是用户名
        if (CHAT_USER_INFO_RESPONSE_MAP.containsKey(userNo)) {
            userName = CHAT_USER_INFO_RESPONSE_MAP.get(userNo).getName();
        }
        if (FRIEND_RESPONSE_MAP.containsKey(userNo)) {
            if (!StringUtil.isNullOrEmpty(FRIEND_RESPONSE_MAP.get(userNo).getNoteName())) {
                userName = FRIEND_RESPONSE_MAP.get(userNo).getNoteName();
            }
        }
        notification.setNickname(userName);
        JLabel userNameLabel = new JLabel();
        userNameLabel.setText(notification.getNickname());
        userNameLabel.setBounds(x, 5, 200, 40);
        userNameLabel.setFont(ITALICS_P_20);
        add(userNameLabel);
    }

    private void setGroupNameAndAvatar(Notification notification, int x) {
        GroupBasic groupBasic = GROUP_BASIC_MAP.get(notification.getNotificationNO());
        JPanel profilePicturePanel = PictureManipulationUtil.getGroupAvatar(groupBasic.getUserRoleMap(), 50, 50);
        profilePicturePanel.setBounds(20, 10, 50, 50);
        add(profilePicturePanel);
        notification.setNickname(groupBasic.getGroupName());
        JLabel userNameLabel = new JLabel();
        userNameLabel.setText(notification.getNickname());
        userNameLabel.setBounds(x, 5, 200, 40);
        userNameLabel.setFont(ITALICS_P_20);
        add(userNameLabel);
    }

}
