package com.murray.view.vo.panel;

import com.murray.dto.response.ChatFriendResponse;
import com.murray.view.vo.cell.FriendCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import static com.murray.cache.ClientCache.FRIEND_RESPONSE_MAP;
import static com.murray.utils.ColorUtil.WHITE235;

/**
 * @author Murray Law
 * @describe 好用列表面板
 * @createTime 2020/11/9
 */
public class GroupListPanel extends JPanel {
    public JList<ChatFriendResponse> groupJList;
    private JScrollPane groupScrollPane;

    public GroupListPanel(int width, int height, Component... components) {
        int otherComponentHeight = 0;
        for (Component component : components) {
            otherComponentHeight += component.getHeight();
            add(component);
        }
        //添加加进来的参数
        groupJList = new JList<>();
        groupJList.setCellRenderer(new FriendCellRenderer());
        groupJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupJList.setSelectionBackground(WHITE235);
        groupJList.setListData(getFriendsArray());

        //好友列表显示列表
        groupScrollPane = new JScrollPane();
        groupScrollPane.setBorder(null);
        groupScrollPane.setViewportView(groupJList);
        groupScrollPane.setBounds(0, otherComponentHeight, width, height);
        groupScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(groupScrollPane);
        setBounds(0, 0, width, height);
        setLayout(null);
    }

    /**
     * @param
     * @return void
     * @description 添加鼠标监听器
     * @author Murray Law
     * @date 2020/11/9 7:50
     */
    public void addMouseListener(MouseAdapter mouseAdapter) {
        groupJList.addMouseListener(mouseAdapter);
    }
    public void addListSelectionListener(ListSelectionListener listener) {
        groupJList.addListSelectionListener(listener);
    }

    /**
     * @param
     * @return void
     * @description 刷新表单
     * @author Murray Law
     * @date 2020/11/9 7:51
     */
    public void refreshGroupList() {
        groupJList.setListData(getFriendsArray());
    }

    /**
     * @param
     * @return void
     * @description 刷新整个面板
     * @author Murray Law
     * @date 2020/11/9 7:51
     */
    public void refreshFriendPanel() {
        refreshGroupList();
        groupScrollPane.setViewportView(groupJList);
    }

    /**
     * @param
     * @return com.murray.dto.response.ChatGroupResponse[]
     * @description 获取传入JList的数组
     * @author Murray Law
     * @date 2020/11/9 7:52
     */
    private ChatFriendResponse[] getFriendsArray() {
        ChatFriendResponse[] groupsArray = new ChatFriendResponse[FRIEND_RESPONSE_MAP.size()];
        return new ArrayList<>(FRIEND_RESPONSE_MAP.values()).toArray(groupsArray);
    }
}
