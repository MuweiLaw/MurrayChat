package com.murray.view.vo.cell;

import com.murray.dto.response.ChatFriendResponse;

import javax.swing.*;
import java.awt.*;

public class GroupListCellRenderer extends JLabel implements ListCellRenderer {
    public GroupListCellRenderer() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        this.removeAll();//妈的,超大坑
        ChatFriendResponse friendResponse=  ((ChatFriendResponse) value);
        setPreferredSize(new Dimension(360, 65));
        //设置头像资料
        ImageIcon profilePicture = new ImageIcon(this.getClass().getResource("/icon/useridicon.jpg").getPath());
        profilePicture.setImage(profilePicture.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
        JLabel profilePictureLabel = new JLabel();
        profilePictureLabel.setIcon(profilePicture);
        profilePictureLabel.setBounds(10, 10, 50, 50);
        add(profilePictureLabel);
        //设置昵称
        JLabel userNameLabel = new JLabel();
        if (null!=friendResponse.getNoteName()){
            userNameLabel.setText(friendResponse.getNoteName());
        }else {
            userNameLabel.setText(friendResponse.getName());
        }
        userNameLabel.setBounds(80, 5, 200, 40);
        userNameLabel.setFont(new Font("楷体", Font.PLAIN, 20));
        add(userNameLabel);
        //设置签名
        JLabel signaturePreviewLabel = new JLabel();
        signaturePreviewLabel.setText(friendResponse.getSignature());
        signaturePreviewLabel.setBounds(80, 35, 200, 30);
        signaturePreviewLabel.setFont(new Font("楷体", Font.PLAIN, 15));
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