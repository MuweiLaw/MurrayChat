package com.murray.utils;

import javax.swing.*;

import static java.awt.Frame.ICONIFIED;

/**
 * @author Murray Law
 * @describe 操作按钮
 * @createTime 2020/11/30
 */
public class OperationalBtnUtil {

    public static JButton getZoomOutButton(JFrame jFrame,JPanel panel,int x,int y, int wid,int hei){
        ImageIcon zoomOutIcon=PictureManipulationUtil.getImageIcon("icon/zoomout.png",wid,hei);

        //缩小按钮
        JButton zoomOutButton = new JButton(zoomOutIcon);
        zoomOutButton.setBounds(x, y, wid, hei);
        zoomOutButton.setBorderPainted(false);//不打印边框
        zoomOutButton.setFocusPainted(false);//除去焦点的框
        zoomOutButton.setBorder(null);
        zoomOutButton.setText(null);
        zoomOutButton.setContentAreaFilled(false);
        zoomOutButton.addActionListener(e -> {
            jFrame.setExtendedState(ICONIFIED);//窗口最小化
        });
        panel.add(zoomOutButton);
        return zoomOutButton;
    }


    public static JButton getExitButton(JFrame jFrame,JPanel panel,int x,int y, int wid,int hei){
        ImageIcon closeIcon=PictureManipulationUtil.getImageIcon("icon/close.png",wid,hei);
        //缩小按钮
        JButton exitButton = new JButton(closeIcon);
        exitButton.setBounds(x, y, wid, hei);
        exitButton.setBorderPainted(false);//不打印边框
        exitButton.setFocusPainted(false);//除去焦点的框
        exitButton.setBorder(null);
        exitButton.setText(null);
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener(e -> {
            jFrame.setExtendedState(ICONIFIED);//窗口最小化
        });
        exitButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    jFrame,
                    "确认退出吗？",
                    "提示",
                    JOptionPane.YES_NO_OPTION
            );
            if (result == 0) {
                System.exit(0);//退出程序
            }
        });

        panel.add(exitButton);
        return exitButton;
    }
}
