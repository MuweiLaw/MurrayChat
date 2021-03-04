package com.murray.view.vo.frame;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

import static com.murray.utils.PictureManipulationUtil.getImageIcon;
import static com.murray.utils.ResponseUtil.*;
import static com.sun.awt.AWTUtilities.setWindowOpacity;
import static java.lang.Thread.sleep;

/**
 * @author Murray Law
 * @describe 反馈服务器的响应
 * @createTime 2020/11/13
 */

public class PromptFrame extends JFrame {
    int frameWidth = 400, frameHeight = 200;
    JPanel contentPane;

    public PromptFrame(String status, String title, String msg) {
        setPanel();
        //标题
        JLabel titleLabel = new JLabel(title);
        titleLabel.setBounds(20, 0, frameWidth / 2, 30);
        //图标
        JLabel statusLabel = new JLabel();
        switch (status) {
            case SUCCESS_STATUS:
                statusLabel.setIcon(getImageIcon("icon/smiley.jpg", frameWidth / 4, frameHeight / 2));
                break;
            case WAITING_STATUS:
                statusLabel.setIcon(getImageIcon("icon/bell.jpg", frameWidth / 8, frameHeight / 4));
                break;
            case FAIL_STATUS:
                statusLabel.setIcon(getImageIcon("icon/sad.png", frameWidth / 4, frameHeight / 2));
                break;
        }
        statusLabel.setBounds(20, frameWidth / 8, frameWidth / 4, frameHeight / 2);

        //响应内容
        JTextPane msgTextPane = new JTextPane();
        msgTextPane.setEditable(false);
        StyledDocument styledDocument = msgTextPane.getStyledDocument();
        msgTextPane.setBounds(40 + frameWidth / 4, frameHeight/ 2-30, frameWidth / 2, 60);
        SimpleAttributeSet attrSet = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrSet, "楷体");
        StyleConstants.setBold(attrSet,false );
        StyleConstants.setItalic(attrSet, false);
        StyleConstants.setFontSize(attrSet, 20);
        StyleConstants.setForeground(attrSet, Color.black);
        contentPane.add(titleLabel);
        contentPane.add(statusLabel);
        contentPane.add(msgTextPane);
        //设置可见
        setVisible(true);
        new Thread(() -> {
            try {
                styledDocument.insertString(styledDocument.getLength(),msg,attrSet);
                sleep(2000);
                for (int i = 10; i > 0; i--) {
                    sleep(66);
                    setWindowOpacity(this, (float) (i * 0.1));
                }
                dispose();
            } catch (InterruptedException | BadLocationException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void setPanel() {
        setSize(frameWidth, frameHeight);
        setResizable(false);//不可调整
        setLocationRelativeTo(null);//居中
        setUndecorated(true);
        setBackground(Color.white);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setBackground(Color.white);
        contentPane.setLayout(null);
    }
}
