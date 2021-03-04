package com.murray.view.vo.panel;

import com.murray.view.vo.cell.GroupListCellRenderer;
import com.murray.view.vo.frame.CreateGroupFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

import static com.murray.utils.ColorUtil.WHITE235;
import static com.murray.utils.ColorUtil.WHITE245;


/**
 * @author Murray Law
 * @describe 创建群聊和群聊列表面板
 * @createTime 2020/11/8
 */
public class GroupPanel extends JPanel {
    private JList<GroupListCellRenderer> groupList = new JList<>();

    public GroupPanel() {
        //创建群聊按钮
        JButton createGroupBtn = new JButton("+ 创建群聊");
        createGroupBtn.setBounds(0, 0, 385, 40);
        createGroupBtn.setMargin(new Insets(0, 0, 0, 0));//将边框外的上下左右空间设置为0
        createGroupBtn.setBackground(WHITE245);
        createGroupBtn.setBorderPainted(false);//不打印边框
        createGroupBtn.setFocusPainted(false);//除去焦点的框
        createGroupBtn.setBorder(null);//除去边框
        createGroupBtn.addActionListener(e -> {
            new CreateGroupFrame(null);
        });
        groupList.setCellRenderer(new GroupListCellRenderer());
        groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupList.setSelectionBackground(WHITE235);
        groupList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (groupList.getValueIsAdjusting()) {
                    // 获取被选中的选项索引
                    int index = groupList.getSelectedIndex();
//                    ChatFriendResponse chatFriendResponse = showBuddyList.getModel().getElementAt(index);
//
//                    if (openPrivateChatWindows.containsKey(chatFriendResponse.getFriendUserNO())) {
//                        openPrivateChatWindows.get(chatFriendResponse.getFriendUserNO()).setAlwaysOnTop(true);
//                        openPrivateChatWindows.get(chatFriendResponse.getFriendUserNO()).setAlwaysOnTop(false);
//                    } else {
//                        openPrivateChatWindows.put(chatFriendResponse.getFriendUserNO(), new PrivateChatFrame(chatFriendResponse.getFriendUserNO(), chatFriendResponse.getFriendUserNO(), chatFriendResponse.getFriendUserNO()));
//                    }
                    //todo 判断有无备注,优先显示备注
                }
            }
        });

        //好友列表显示列表
        JScrollPane groupListPane = new JScrollPane();
        groupListPane.setViewportView(groupList);
        groupListPane.setBorder(null);
        groupListPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        groupListPane.setBounds(0, 40, 380, 545);
        add(createGroupBtn);
        add(groupListPane);
        setBackground(Color.white);
        setLayout(null);
    }
}
