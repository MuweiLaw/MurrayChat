package com.murray.view.vo.cell;

import com.murray.dto.response.ChatFriendResponse;
import com.murray.utils.PictureManipulationUtil;

import javax.swing.*;
import java.awt.*;

public class FriendCellRenderer extends JLabel implements ListCellRenderer {
    public FriendCellRenderer() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        this.removeAll();//妈的,超大坑
        ChatFriendResponse friendResponse=  ((ChatFriendResponse) value);
        setPreferredSize(new Dimension(360, 50));
        //设置头像资料
        ImageIcon profilePicture = PictureManipulationUtil.getUserAvatar(friendResponse.getFriendUserNo(), 40, 40);
        JLabel profilePictureLabel = new JLabel();
        profilePictureLabel.setIcon(profilePicture);
        profilePictureLabel.setBounds(20, 5, 40, 40);
        add(profilePictureLabel);
        //设置昵称
        JLabel userNameLabel = new JLabel();
        if (null!=friendResponse.getNoteName()){
            userNameLabel.setText(friendResponse.getNoteName());
        }else {
            userNameLabel.setText(friendResponse.getName());
        }
        userNameLabel.setBounds(70, 5, 200, 25);
        userNameLabel.setFont(new Font("楷体", Font.PLAIN, 20));
        add(userNameLabel);
        //设置签名
        JLabel signaturePreviewLabel = new JLabel();
        signaturePreviewLabel.setText(friendResponse.getSignature());
        signaturePreviewLabel.setBounds(70, 30, 200, 15);
        signaturePreviewLabel.setFont(new Font("楷体", Font.PLAIN, 13));
        signaturePreviewLabel.setForeground(Color.gray);
        add(signaturePreviewLabel);


        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());

        }
        return this;
    }

}