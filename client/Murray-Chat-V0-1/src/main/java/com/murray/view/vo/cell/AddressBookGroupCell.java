package com.murray.view.vo.cell;

import com.murray.dto.response.ChatUserInfoResponse;
import com.murray.utils.FontUtil;
import com.murray.utils.PictureManipulationUtil;

import javax.swing.*;

/**
 * @author Murray Law
 * @describe 通讯录单元
 * @createTime 2020/12/7
 */
public class AddressBookGroupCell extends JLabel {

    public AddressBookGroupCell(ChatUserInfoResponse response) {
        super();
        //设置头像资料
        ImageIcon profilePicture = PictureManipulationUtil.getUserAvatar(response.getChatUserNo(), 50, 50);
        JLabel profilePictureLabel = new JLabel();
        profilePictureLabel.setIcon(profilePicture);
        profilePictureLabel.setBounds(20, 15, 50, 50);
        add(profilePictureLabel);
        //设置昵称
        JLabel userNameLabel = new JLabel();
            userNameLabel.setText(response.getName());
        userNameLabel.setBounds(90, 15, 200, 30);
        userNameLabel.setFont(FontUtil.MICROSOFT_YA_HEI_B16);
        add(userNameLabel);
    }

}
