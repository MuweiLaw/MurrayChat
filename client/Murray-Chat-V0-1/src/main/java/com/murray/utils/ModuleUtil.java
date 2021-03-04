package com.murray.utils;

import com.murray.ClientMainApplication;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * @author Murray Law
 * @describe 图形组件工具类
 * @createTime 2020/11/9
 */
public class ModuleUtil {

    /**
     * @param width
     * @param height
     * @return javax.swing.JPanel
     * @description 搜索区域
     * @author Murray Law
     * @date 2020/11/10 9:14
     */
    public static JPanel searchArea(int width, int height) {
        //搜索布局
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        //文本
        JLabel searchTextLabel = new JLabel("搜索");
        searchTextLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        searchTextLabel.setForeground(Color.gray);
        searchTextLabel.setBounds(40, 5, 40, 40);
        //搜索图标
        JLabel searchIconLabel = new JLabel();
        ImageIcon searchIcon = new ImageIcon(Objects.requireNonNull(ClientMainApplication.class.getClassLoader().getResource("icon/timg.jpg")));
        searchIcon.setImage(searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
        searchIconLabel.setIcon(searchIcon);
        searchIconLabel.setBounds(5, 10, 30, 30);

        //搜索的输入框
        JTextField searchFiled = new JTextField();
        searchFiled.setBounds(85, 5, width - 50, 45);
        searchFiled.setOpaque(false);
        searchFiled.setBorder(null);
        searchPanel.add(searchIconLabel);
        searchPanel.add(searchTextLabel);
        searchPanel.add(searchFiled);
        searchPanel.setForeground(Color.gray);
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBounds(0, 0, width, height);
        searchPanel.setBorder(null);
        searchPanel.setLayout(null);
        return searchPanel;
    }

    public static void showDialog(String title, String message, int dialogType) {
        JOptionPane.showMessageDialog(
                null,
                message,
                title,
                dialogType
        );
    }

    public static String showInputDialog(Component parentComponent, String message, String initialSelectionValue) {
        String inputContent = JOptionPane.showInputDialog(
                // 显示输入对话框, 返回输入的内容
                parentComponent,
                message,
                initialSelectionValue
        );
        return inputContent;
    }

    public static JLabel greaterThanLabel() {
        return new JLabel(">");
    }
}
