package com.murray.view.vo.cell;

import com.murray.utils.FontUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @author Murray Law
 * @describe 邮箱用户标签
 * @createTime 2020/12/18
 */
public class MailUserLabel extends JPanel {
    private String userMailNo;
    private static final Dimension dim = new Dimension(0, 0);

    public MailUserLabel(String userName, String userMailNo, ImageIcon icon) {
        super();
        setLayout(null);
        int width = 20 * userName.length() + 60;
        dim.setSize(width, 44);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setMinimumSize(dim);

        setOpaque(false);
        setBackground(Color.WHITE);
        this.userMailNo = userMailNo;
        //编辑图标
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBounds(width - icon.getIconWidth() - 10, 2, icon.getIconWidth(), 40);
        iconLabel.setBackground(Color.white);
        //文本
        JLabel textLabel = new JLabel(" "+userName,JLabel.LEFT);
        textLabel.setBounds(5, 2, dim.width-10, 40);
        textLabel.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        textLabel.setOpaque(true);
        textLabel.setBackground(Color.WHITE);
        add(iconLabel);
        add(textLabel);
    }

    public String getUserMailNo() {
        return userMailNo;
    }
}
