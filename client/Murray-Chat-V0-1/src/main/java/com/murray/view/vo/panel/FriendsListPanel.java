package com.murray.view.vo.panel;

import com.murray.dto.response.ChatFriendResponse;
import com.murray.view.vo.cell.FriendCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import static com.murray.cache.ClientCache.FRIEND_RESPONSE_MAP;

/**
 * @author Murray Law
 * @describe 好友列表面板
 * @createTime 2020/11/9
 */
public class FriendsListPanel extends JPanel {
    public JList<ChatFriendResponse> friendJList;
    private JScrollPane friendScrollPane;

    public FriendsListPanel(int x,int y, int width, int height, Color listColor,Color SelectionColor,Component... components) {
        int otherComponentHeight = 0;
        for (Component component : components) {
            otherComponentHeight += component.getHeight();
            add(component);
        }
        //添加加进来的参数
        friendJList = new JList<>();
        friendJList.setCellRenderer(new FriendCellRenderer());
        friendJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        friendJList.setBackground(listColor);
        friendJList.setSelectionBackground(SelectionColor);
        friendJList.setListData(getFriendsArray());

        //好友列表显示列表
        friendScrollPane = new JScrollPane();
        friendScrollPane.setBorder(null);
        friendScrollPane.setViewportView(friendJList);
        friendScrollPane.setBounds(x, otherComponentHeight, width, height);
        friendScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(friendScrollPane);
        setBounds(x, y, width, height);
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
        friendJList.addMouseListener(mouseAdapter);
    }
    public void addListSelectionListener(ListSelectionListener listener) {
        friendJList.addListSelectionListener(listener);
    }

    /**
     * @param
     * @return void
     * @description 刷新表单
     * @author Murray Law
     * @date 2020/11/9 7:51
     */
    public void refreshFriendList() {
        friendJList.setListData(getFriendsArray());
    }

    /**
     * @param
     * @return void
     * @description 刷新整个面板
     * @author Murray Law
     * @date 2020/11/9 7:51
     */
    public void refreshFriendPanel() {
        refreshFriendList();
        friendScrollPane.setViewportView(friendJList);
    }

    /**
     * @param
     * @return com.murray.dto.response.ChatFriendResponse[]
     * @description 获取传入JList的数组
     * @author Murray Law
     * @date 2020/11/9 7:52
     */
    private ChatFriendResponse[] getFriendsArray() {
        ChatFriendResponse[] friendsArray = new ChatFriendResponse[FRIEND_RESPONSE_MAP.size()];
        return new ArrayList<>(FRIEND_RESPONSE_MAP.values()).toArray(friendsArray);
    }
}
