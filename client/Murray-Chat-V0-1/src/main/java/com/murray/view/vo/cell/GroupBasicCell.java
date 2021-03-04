package com.murray.view.vo.cell;

import com.murray.dto.response.GroupBasic;
import com.murray.utils.FontUtil;
import com.murray.utils.PictureManipulationUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @author Murray Law
 * @describe 通讯录单元
 * @createTime 2020/12/7
 */
public class GroupBasicCell extends JLabel {

    public GroupBasicCell(GroupBasic groupBasic) {
        super();
        //设置头像资料
        JPanel groupAvatar = PictureManipulationUtil.getGroupAvatar(groupBasic.getUserRoleMap(), 50, 50);
        groupAvatar.setBounds(20, 15, 50, 50);
        add(groupAvatar);
        //设置群名称
        JLabel userNameLabel = new JLabel();
        userNameLabel.setText(groupBasic.getGroupName());
        userNameLabel.setBounds(90, 15, 200, 30);
        userNameLabel.setFont(FontUtil.MICROSOFT_YA_HEI_B16);
        add(userNameLabel);
        //设置最后一条消息
        JLabel lastMsgLabel = new JLabel();
        lastMsgLabel.setText(groupBasic.getLastSpeakingMsg());
        lastMsgLabel.setBounds(90, 45, 200, 30);
        lastMsgLabel.setFont(FontUtil.MICROSOFT_YA_HEI_14);
        lastMsgLabel.setForeground(Color.gray);
        add(lastMsgLabel);
    }

}
