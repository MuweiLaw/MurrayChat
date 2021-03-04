package com.murray.handler.listener;

import com.murray.view.vo.frame.UserInfoFrame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Murray Law
 * @describe 头像鼠标监听器
 * @createTime 2020/12/11
 */
public class AvatarMouseAdapter extends MouseAdapter {
    private Component component;
    private String userNo;

    public AvatarMouseAdapter(Component component, String userNo) {
        this.component = component;
        this.userNo = userNo;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point locationOnScreen = component.getLocationOnScreen();
        UserInfoFrame userInfoFrame = new UserInfoFrame(userNo);
        userInfoFrame.setLocation((int) locationOnScreen.getX() - userInfoFrame.getWidth() - 10, (int) locationOnScreen.getY() - userInfoFrame.getHeight() / 2);
        userInfoFrame.setVisible(true);

    }
}
