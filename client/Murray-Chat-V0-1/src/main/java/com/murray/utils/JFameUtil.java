package com.murray.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * @author Murray Law
 * @describe JFrame工具类
 * @createTime 2020/11/1
 */
public class JFameUtil {
    //实现JFrame的拖动
    public static void eventHandle(Component headPanel, JFrame mainFrame) {
        headPanel.addMouseMotionListener(new MouseMotionListener() {
            int old_x;
            int old_y;
            public void mouseDragged(MouseEvent e) {
                mainFrame.setLocation(mainFrame.getLocation().x + e.getX() - old_x,
                        mainFrame.getLocation().y + e.getY() - old_y);
            }

            public void mouseMoved(MouseEvent e) {
                old_x = e.getX();
                old_y = e.getY();
            }

        });
    }
}
