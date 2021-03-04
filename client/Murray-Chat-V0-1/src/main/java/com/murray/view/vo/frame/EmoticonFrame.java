package com.murray.view.vo.frame;

import com.murray.utils.PictureManipulationUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @author Murray Law
 * @describe 表情输入窗口
 * @createTime 2020/12/28
 */
public class EmoticonFrame extends JFrame {

    public EmoticonFrame(){
        setUndecorated(true);
        setLocationRelativeTo(null);
        setSize(420,250);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.white);

        JLabel obsessedLabel = new JLabel(PictureManipulationUtil.getImageIcon("expression/obsessed.png", 40, 40));
        contentPane.add(obsessedLabel);

        setContentPane(contentPane);
        setVisible(true);

    }
}
