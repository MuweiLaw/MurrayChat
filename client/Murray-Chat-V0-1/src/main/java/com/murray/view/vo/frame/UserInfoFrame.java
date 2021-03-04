package com.murray.view.vo.frame;

import com.murray.cache.ClientCache;
import com.murray.dto.response.ChatUserInfoResponse;
import com.murray.utils.BorderUtil;
import com.murray.utils.ColorUtil;
import com.murray.utils.FontUtil;
import com.murray.utils.PictureManipulationUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.murray.agreement.MessageType.PRIVATE_CHAT_TYPE;
import static com.murray.cache.ClientCache.CHAT_MAIN_FRAME;
import static com.murray.cache.ClientCache.CHAT_USER_INFO_RESPONSE_MAP;

/**
 * @author Murray Law
 * @describe 用户信息窗口
 * @createTime 2020/12/11
 */
public class UserInfoFrame extends JFrame {
    private String userNo;
    private JPanel contentPane = new JPanel(null);

    public UserInfoFrame(String userNo) {
        super();
        this.userNo = userNo;
        //隐藏windows的窗格边框
        setUndecorated(true);
        setSize(335, 260);

        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(BorderUtil.BORDER_LIGHT_GRAY);
        setContentPane(contentPane);
        //获取用户信息
        ChatUserInfoResponse userInfo = ClientCache.CHAT_USER_INFO_RESPONSE_MAP.get(userNo);

        //上面头像部分
        contentPane.setBounds(1, 1, getWidth() - 2, 129);
        contentPane.setBackground(Color.white);
        //设置头像资料
        ImageIcon profilePicture = PictureManipulationUtil.getUserAvatar(userNo, 80, 80);
        JLabel profilePictureLabel = new JLabel(profilePicture);
        profilePictureLabel.setBounds(10, 10, 80, 80);
        //设置名字
        JLabel nameLabel = new JLabel(userInfo.getName());
        nameLabel.setFont(FontUtil.MICROSOFT_YA_HEI_B24);
        nameLabel.setBounds(100, 30, 220, 40);
        //设置职别
        JLabel rankLabel = new JLabel(userInfo.getCompanyRank());
        rankLabel.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        rankLabel.setBounds(100, 70, 220, 20);
        rankLabel.setForeground(Color.gray);
        //上部分添加组件
        contentPane.add(profilePictureLabel);
        contentPane.add(nameLabel);
        contentPane.add(rankLabel);

        //下面信息部分
        JPanel middlePanel = new JPanel(null);
        middlePanel.setBounds(1, 130, getWidth() - 2, 129);
        middlePanel.setBackground(ColorUtil.CARD_COLOR);
        //设置部门
        JLabel departmentLabel = new JLabel("部门");
        departmentLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        departmentLabel.setBounds(35, 15, 60, 20);
        departmentLabel.setForeground(Color.gray);
        JLabel departmentNameLabel = new JLabel(userInfo.getCompanyDepartment());
        departmentNameLabel.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        departmentNameLabel.setBounds(100, 15, 220, 20);
        //设置电话
        JLabel phoneLabel = new JLabel("电话");
        phoneLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        phoneLabel.setBounds(35, 55, 60, 20);
        phoneLabel.setForeground(Color.gray);

        StringBuilder stringBuilder = new StringBuilder(userInfo.getPhone());
        stringBuilder.insert(3, " ");
        stringBuilder.insert(8, " ");
        JLabel phoneNoLabel = new JLabel(stringBuilder.toString());
        phoneNoLabel.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        phoneNoLabel.setBounds(100, 55, 220, 20);
        //设置邮箱
        JLabel mailLabel = new JLabel("邮箱");
        mailLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        mailLabel.setBounds(35, 95, 60, 20);
        mailLabel.setForeground(Color.gray);
        JLabel mailNoLabel = new JLabel(userInfo.getMail());
        mailNoLabel.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        mailNoLabel.setBounds(100, 95, 220, 20);

        //下部分添加组件
        middlePanel.add(departmentLabel);
        middlePanel.add(departmentNameLabel);
        middlePanel.add(phoneLabel);
        middlePanel.add(phoneNoLabel);
        middlePanel.add(mailLabel);
        middlePanel.add(mailNoLabel);
        UserInfoFrame userInfoFrame = this;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                //窗口不活动则自动销毁
                userInfoFrame.dispose();
            }
        });

        //添加面板
        contentPane.add(middlePanel);
        contentPane.setVisible(true);
    }

    public void addMsgButton() {
        setSize(335, 360);
        JButton openPrivateBtn = new JButton("发消息");
        openPrivateBtn.setBounds(80,280,175,60);
        openPrivateBtn.setBackground(ColorUtil.MAIN_LEFT_COLOR);
        openPrivateBtn.setForeground(Color.WHITE);
        openPrivateBtn.setBorder(null);//不打印边框
        openPrivateBtn.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        openPrivateBtn.setFocusPainted(false);
        openPrivateBtn.addActionListener(e -> {
            CHAT_MAIN_FRAME.getPrivateChat(CHAT_USER_INFO_RESPONSE_MAP.get(userNo).getName(), userNo);
            CHAT_MAIN_FRAME.refreshMsgScrollPanel();
            CHAT_MAIN_FRAME.chatType=PRIVATE_CHAT_TYPE;
            CHAT_MAIN_FRAME.clickOnMsgCellNo=userNo;

        });
        contentPane.add(openPrivateBtn);
    }
}
