package com.murray.utils;

import com.murray.ClientMainApplication;
import com.murray.dto.request.AvatarRequest;
import com.murray.handler.ClientPacketCodeCompile;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;
import java.util.Objects;

import static com.murray.cache.ClientCache.*;

/**
 * @author Murray Law
 * @describe 图片处理工具类
 * @createTime 2020/10/29
 */
public class PictureManipulationUtil {
    private static String DEFAULT_USER_AVATAR = "defaultUserAvatar.jpg";

    /**
     * @param path
     * @param width
     * @param height
     * @return javax.swing.ImageIcon
     * @description 获取图标
     * @author Murray Law
     * @date 2020/11/21 15:35
     */
    public static ImageIcon getImageIcon(String path, Integer width, Integer height) {
        ImageIcon imageIcon = new ImageIcon(ClientMainApplication.class.getClassLoader().getResource(path));
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING));
        return imageIcon;
    }

    /**
     * @param frame
     * @param path
     * @param x
     * @param y
     * @param width
     * @param height
     * @return void
     * @description 添加背景图片
     * @author Murray Law
     * @date 2020/11/21 15:35
     */
    public static void addBGImage(JFrame frame, String path, int x, int y, int width, int height) {
        ImageIcon headBGIcon = new ImageIcon(Objects.requireNonNull(ClientMainApplication.class.getClassLoader().getResource(path)));
        //设置图片缩略大小，并面板布局中
        headBGIcon.setImage(headBGIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JLabel backgroundLabel = new JLabel(headBGIcon);
        backgroundLabel.setBounds(x, y, width, height);
        //分层
        frame.getLayeredPane().add(backgroundLabel, new Integer(Integer.MIN_VALUE));
    }

    /**
     * @param chatUserNo
     * @param width
     * @param height
     * @return javax.swing.ImageIcon
     * @description 获取用户头像
     * @author Murray Law
     * @date 2020/11/21 15:36
     */
    public static ImageIcon getUserAvatar(String chatUserNo, int width, int height) {
        String filePth;
        if (CHAT_USER_INFO_RESPONSE_MAP.containsKey(chatUserNo)) {
            filePth = AVATAR_FOLDER + chatUserNo + CHAT_USER_INFO_RESPONSE_MAP.get(chatUserNo).getAvatarType();
            File file = new File(filePth);
            if (!file.exists()) {
                CHANNEL.writeAndFlush(ClientPacketCodeCompile.encode(new AvatarRequest(chatUserByLogIn.getChatUserNo(), chatUserNo)));
            }
        } else {
            filePth = AVATAR_FOLDER + DEFAULT_USER_AVATAR;
//            CHANNEL.writeAndFlush(ClientPacketCodeCompile.encode(new AvatarRequest(chatUserByLogIn.getChatUserNo(), chatUserNo)));
//            CHANNEL.writeAndFlush(ClientPacketCodeCompile.encode(new ChatUserInfoRequest(chatUserByLogIn.getChatUserNo(), chatUserNo)));
        }
        ImageIcon imageIcon = new ImageIcon(filePth);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING));
        return imageIcon;
    }

    /**
     * @param userRoleMap
     * @param width
     * @param height
     * @return javax.swing.ImageIcon
     * @description 获取群组头像
     * @author Murray Law
     * @date 2020/11/21 15:36
     */
    public static JPanel getGroupAvatar(Map<String, Byte> userRoleMap, int width, int height) {
        int count = 0;
        JPanel groupAvatarPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        flowLayout.setHgap(1);//水平间距
        flowLayout.setVgap(1);//组件垂直间距
        groupAvatarPanel.setLayout(flowLayout);
        for (Map.Entry<String, Byte> entry : userRoleMap.entrySet()) {
            //限制4个人的头像
            if (count>4){
                return groupAvatarPanel;
            }
            groupAvatarPanel.add(new JLabel(getUserAvatar(entry.getKey(), (width-5 )/ 2, (height-5) / 2)));
            count+=1;
        }
        return groupAvatarPanel;

    }
}
