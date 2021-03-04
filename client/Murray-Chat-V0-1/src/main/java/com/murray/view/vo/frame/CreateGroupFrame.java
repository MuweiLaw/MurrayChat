package com.murray.view.vo.frame;

import com.murray.dto.request.CreateGroupRequest;
import com.murray.dto.response.ChatUserInfoResponse;
import com.murray.dto.response.GroupBasic;
import com.murray.handler.ClientPacketCodeCompile;
import com.murray.utils.*;
import com.murray.view.vo.panel.AddressBookBasicScrollPanel;
import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.murray.agreement.MessageType.GROUP_CHAT_TYPE;
import static com.murray.agreement.MessageType.PRIVATE_CHAT_TYPE;
import static com.murray.cache.ClientCache.*;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

/**
 * @author Murray Law
 * @describe 创建群聊的窗口
 * @createTime 2020/11/8
 */
public class CreateGroupFrame extends JFrame {

    private JPanel mainPanel, selectedPanel;
    private JScrollPane selectedScrollPane = new JScrollPane();
    private JLabel selectedLabel;
    private JButton confirmBtn;
    private ImageIcon selectedIcon = PictureManipulationUtil.getImageIcon("v1icon/selectedState.png", 25, 25);


    private List<String> selectedList = new ArrayList<>();

    public CreateGroupFrame(String counterpartChatNO) {
        setResizable(false);//不可调整
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setUndecorated(true);
        setSize(630, 535);
        setLocationRelativeTo(CHAT_MAIN_FRAME.mainFrame);
        //主面板
        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.white);
        mainPanel.setBorder(BorderUtil.BORDER_WHITE_235);
        mainPanel.setBounds(0, 0, getWidth(), getHeight());
        setContentPane(mainPanel);
        //用户选择面板
        SelectUserScrollPanel selectUserScrollPanel = new SelectUserScrollPanel(getWidth() / 2, getHeight());
        selectUserScrollPanel.setBorder(BorderUtil.BORDER_WHITE_235);
        selectUserScrollPanel.setBounds(0, 0, getWidth() / 2, getHeight());

        //右边,已选列表
        JLabel createGroupLabel = new JLabel("创建群组");
        createGroupLabel.setBounds(getWidth() / 2 + 15, 15, getWidth() / 4, 30);
        createGroupLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        mainPanel.add(createGroupLabel);

        //已选好友滑动面板
        selectedScrollPane.setBounds(getWidth() / 2, 60, getWidth() / 2, getHeight() - 120);
        selectedScrollPane.setBorder(null);
        //滑动面板内放置好友信息的容器
        selectedPanel = new JPanel(GridBagUtil.getGridBagLayout());
        selectedPanel.setBackground(Color.white);

        if (counterpartChatNO != null) {
            if (CHAT_MAIN_FRAME.chatType.equals(PRIVATE_CHAT_TYPE)) {
                addSelectedCellByUserNo(counterpartChatNO);
                selectedLabel = new JLabel("已选 1/100");
            }
            if (CHAT_MAIN_FRAME.chatType.equals(GROUP_CHAT_TYPE)) {
                GroupBasic groupBasic = GROUP_BASIC_MAP.get(counterpartChatNO);
                groupBasic.getUserRoleMap().keySet().forEach(this::addSelectedCellByUserNo);
                selectedLabel = new JLabel("已选 " + groupBasic.getUserRoleMap().size() + "/100");
            }


        } else {
            selectedLabel = new JLabel("已选 0/100");
        }
        selectedLabel.setBounds(getWidth() / 4 * 3 + 50, 15, getWidth() / 4, 30);
        selectedLabel.setForeground(Color.lightGray);
        selectedLabel.setFont(FontUtil.MICROSOFT_YA_HEI_14);
        //取消创建按钮
        JButton cancelBtn = new JButton("取消");
        cancelBtn.setBounds(getWidth() / 2 + 30, getHeight() - 55, 115, 40);
        cancelBtn.setBackground(Color.white);
        cancelBtn.setForeground(ColorUtil.MAIN_LEFT_COLOR);
        cancelBtn.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        cancelBtn.setBorder(BorderUtil.BORDER_MAIN_LEFT_COLOR);
        cancelBtn.addActionListener(e -> dispose());
        //确认创建按钮
        confirmBtn = new JButton("创建");
        confirmBtn.setBounds(getWidth() - 145, getHeight() - 55, 115, 40);
        confirmBtn.setBackground(ColorUtil.MAIN_LEFT_COLOR);
        confirmBtn.setForeground(Color.white);
        confirmBtn.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        confirmBtn.setBorder(null);
        if (selectedList.isEmpty()) {
            confirmBtn.setEnabled(false);
        }
        confirmBtn.addActionListener(e -> createGroup());
        //添加元素
        selectedScrollPane.setViewportView(selectedPanel);
        mainPanel.add(selectUserScrollPanel);
        mainPanel.add(selectedLabel);
        mainPanel.add(selectedScrollPane);
        mainPanel.add(cancelBtn);
        mainPanel.add(confirmBtn);
        mainPanel.setVisible(true);
        setVisible(true);//一定要放在最后面
    }

    /**
     * @param chatUserNo
     * @return void
     * @description 根据用户编号添加已选单元
     * @author Murray Law
     * @date 2020/12/21 8:49
     */
    private void addSelectedCellByUserNo(String chatUserNo) {
        selectedList.add(chatUserNo);
        JPanel rightCellPanel = new JPanel(null);
        JLabel rightCellNameLabel = new JLabel("      " + CHAT_USER_INFO_RESPONSE_MAP.get(chatUserNo).getName());
        rightCellPanel.setPreferredSize(DimensionUtil.DIM_315_40);
        rightCellPanel.setBorder(BorderUtil.BORDER_WHITE_235);
        rightCellNameLabel.setIcon(PictureManipulationUtil.getUserAvatar(chatUserNo, 25, 25));
        rightCellNameLabel.setBounds(20, 0, 315, 40);
        rightCellPanel.setBackground(Color.white);
        rightCellPanel.add(rightCellNameLabel);
        selectedPanel.add(rightCellPanel, GridBagUtil.wrap());
    }


    /**
     * @param
     * @return void
     * @description 确认创建群聊
     * @author Murray Law
     * @date 2020/11/9 11:53
     */
    private void createGroup() {
        String groupName = chatUserByLogIn.getName() + "创建的群聊";
        CreateGroupRequest createGroupRequest = new CreateGroupRequest(groupName, 100, chatUserByLogIn.getChatUserNo(), selectedList);
        //发送创建群聊的请求
        CHANNEL.writeAndFlush(ClientPacketCodeCompile.encode(createGroupRequest));
        this.dispose();
    }


    /**
     * @param
     * @return void
     * @description 去除已选择的好友标签
     * @author Murray Law
     * @date 2020/11/9 10:02
     */
    private void removeLabel(JLabel friendLabel, String userNo) {
        selectedList.remove(userNo);
//        selectedLabel.setText("已选择联系人: " + selectedCount);
        selectedPanel.remove(friendLabel);
        selectedScrollPane.setViewportView(selectedPanel);
    }


    /**
     * @author Murray Law
     * @describe 选择用户面板
     * @createTime 2020/12/14
     */
    private class SelectUserScrollPanel extends JPanel {
        private JScrollPane jScrollPane = new JScrollPane();
        private JPanel userListPanel = new JPanel(GridBagUtil.getGridBagLayout()), groupChatPanel = new JPanel(GridBagUtil.getGridBagLayout()), depPanel = new JPanel(GridBagUtil.getGridBagLayout());
        private final String beforeRecent = "   > 最近联系人", afterRecent = "   v  最近联系人",
                beforeGroupChat = "   > 群聊", afterGroupChat = "   v  群聊",
                beforeDepartment = "   > 部门", afterDepartment = "   v  部门",
                beforeNodePrefix = "       > ", afterNodePrefix = "       v  ";
        private JLabel recentLabel = new JLabel(beforeRecent), groupChatLabel = new JLabel(beforeGroupChat), depLabel = new JLabel(beforeDepartment);

        public SelectUserScrollPanel(int wid, int hei) {
            setLayout(null);
            //搜索区域
            setBackground(Color.white);
            userListPanel.setBackground(Color.white);
            userListPanel.setBorder(null);
            //搜索边框
            JLabel border = new JLabel();
            border.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            border.setBounds(15, 15, 280, 35);
            Border roundedBorder = new RoundBorder(Color.lightGray);
            border.setBorder(roundedBorder);
            add(border);
            //搜索图标
            JLabel searchIconLabel = new JLabel();
            ImageIcon searchIcon = PictureManipulationUtil.getImageIcon("icon/timg.jpg", 20, 20);
            searchIconLabel.setIcon(searchIcon);
            searchIconLabel.setBounds(30, 17, 30, 30);
            add(searchIconLabel);
            //搜索文字
            JLabel text = new JLabel("搜索");
            text.setFont(FontUtil.MICROSOFT_YA_HEI_16);
            text.setForeground(Color.lightGray);
            text.setBounds(60, 15, 80, 35);
            add(text);
            //搜索输入框
            JTextField searchFiled = new JTextField();
            searchFiled.setBounds(60, 15, 230, 35);
            searchFiled.setFont(FontUtil.MICROSOFT_YA_HEI_16);
            searchFiled.setOpaque(false);
            searchFiled.setBorder(null);
            searchFiled.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    text.setText(null);
                }

                @Override
                public void focusLost(FocusEvent e) {
                    text.setText("搜索");
                }
            });
            add(searchFiled);
            //滑动面板
            jScrollPane.getVerticalScrollBar().setUnitIncrement(20);
            jScrollPane.getVerticalScrollBar().setUI(new WindowsScrollBarUI());
            jScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
            jScrollPane.setViewportView(internalPanel());
            jScrollPane.setBorder(null);
            jScrollPane.setBounds(0, 60, wid, hei - 60);
            add(jScrollPane);

        }

        /**
         * @return javax.swing.JPanel
         * @description 滑动面板的内部面板
         * @author Murray Law
         * @date 2020/12/14 11:39
         * //     * @param response
         */
        public JPanel internalPanel() {
            userListPanel.removeAll();
            //最近联系人
            recentLabel.setPreferredSize(DimensionUtil.DIM_315_40);
            recentLabel.setBorder(BorderUtil.BORDER_WHITE_235);
            recentLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
            recentLabel.addMouseListener(new SelectBasicMouseAdapter(recentLabel, 0, beforeRecent, afterRecent));
            userListPanel.add(recentLabel, GridBagUtil.wrap());
            //群聊
            groupChatLabel.setPreferredSize(DimensionUtil.DIM_315_40);
            groupChatLabel.setBorder(BorderUtil.BORDER_WHITE_235);
            groupChatLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
            groupChatLabel.addMouseListener(new SelectBasicMouseAdapter(groupChatLabel, 0, beforeGroupChat, afterGroupChat));
            userListPanel.add(groupChatLabel, GridBagUtil.wrap());
            //群聊基本展示面板
            groupChatPanel.setPreferredSize(DimensionUtil.DIM_315_0);
            userListPanel.add(groupChatPanel, GridBagUtil.wrap());
            //部门
            depLabel.setPreferredSize(DimensionUtil.DIM_315_40);
            depLabel.setBorder(BorderUtil.BORDER_WHITE_235);
            depLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
            depLabel.addMouseListener(new SelectBasicMouseAdapter(depLabel, 0, beforeDepartment, afterDepartment));
            userListPanel.add(depLabel, GridBagUtil.wrap());
            //部门基本展示面板
            depPanel.setPreferredSize(DimensionUtil.DIM_315_0);
            userListPanel.add(depPanel, GridBagUtil.wrap());
            return userListPanel;
        }

        private class SelectBasicMouseAdapter extends MouseAdapter {
            private int flag;
            private String beforeStr, afterStr;
            private JLabel label;

            public SelectBasicMouseAdapter(JLabel label, int flag, String beforeStr, String afterStr) {
                this.label = label;
                this.flag = flag;
                this.beforeStr = beforeStr;
                this.afterStr = afterStr;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (label.getText().contains("群聊")) {
                    if (flag == 0) {
                        GROUP_BASIC_MAP.forEach((groupNo, groupBasic) -> {
                            groupChatPanel.setPreferredSize(new Dimension(315, 40 * GROUP_BASIC_MAP.size()));
                            JLabel groupNameLabel = new JLabel(beforeNodePrefix + groupBasic.getGroupName());
                            groupNameLabel.setOpaque(true);
                            groupNameLabel.setBackground(Color.white);
                            groupNameLabel.setBorder(BorderUtil.BORDER_WHITE_235);
                            groupNameLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
                            groupNameLabel.setPreferredSize(DimensionUtil.DIM_315_40);
                            groupChatPanel.add(groupNameLabel, GridBagUtil.wrap());
                            //叶子面板
                            JPanel userPanel = new JPanel(GridBagUtil.getGridBagLayout());
                            userPanel.setPreferredSize(DimensionUtil.DIM_315_0);
                            groupChatPanel.add(userPanel, GridBagUtil.wrap());
                            groupNameLabel.addMouseListener(new GroupDetailedMouseAdapter(groupNameLabel, groupChatPanel, userPanel, 0, beforeNodePrefix + groupBasic.getGroupName(), afterNodePrefix + groupBasic.getGroupName(), groupBasic.getGroupNo()));

                        });
                    } else {
                        groupChatPanel.removeAll();
                        groupChatPanel.setPreferredSize(DimensionUtil.DIM_315_0);
                    }
                }

                if (label.getText().contains("部门")) {
                    if (flag == 0) {
                        AddressBookBasicScrollPanel.departmentMap.forEach((depName, departmentResponse) -> {
                            depPanel.setPreferredSize(new Dimension(315, 40 * AddressBookBasicScrollPanel.departmentMap.size()));
                            //部门名称
                            JLabel depNameLabel = new JLabel(beforeNodePrefix + depName);
                            depNameLabel.setOpaque(true);
                            depNameLabel.setBackground(Color.white);
                            depNameLabel.setBorder(BorderUtil.BORDER_WHITE_235);
                            depNameLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
                            depNameLabel.setPreferredSize(DimensionUtil.DIM_315_40);
                            depPanel.add(depNameLabel, GridBagUtil.wrap());
                            //叶子面板
                            JPanel userPanel = new JPanel(GridBagUtil.getGridBagLayout());
                            userPanel.setPreferredSize(DimensionUtil.DIM_315_0);
                            depPanel.add(userPanel, GridBagUtil.wrap());
                            depNameLabel.addMouseListener(new DepDetailedMouseAdapter(depNameLabel, depPanel, userPanel, 0, beforeNodePrefix + depName, afterNodePrefix + depName, departmentResponse.getDepartmentNo()));
                        });
                    } else {
                        depPanel.removeAll();
                        depPanel.setPreferredSize(DimensionUtil.DIM_315_0);
                    }
                }
                if (flag == 0) {
                    flag = 1;
                    label.setText(afterStr);
                } else {
                    flag = 0;
                    label.setText(beforeStr);
                }

            }
        }


        private class GroupDetailedMouseAdapter extends MouseAdapter {
            private int flag;
            private String beforeStr, afterStr, key;

            private JLabel label;
            private JPanel userPanel, parentPanel;

            public GroupDetailedMouseAdapter(JLabel label, JPanel parentPanel, JPanel userPanel, int flag, String beforeStr, String afterStr, String key) {
                this.parentPanel = parentPanel;
                this.userPanel = userPanel;
                this.label = label;
                this.flag = flag;
                this.beforeStr = beforeStr;
                this.afterStr = afterStr;
                this.key = key;

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Map<String, Byte> userRoleMap = GROUP_BASIC_MAP.get(key).getUserRoleMap();
                if (flag == 0) {
                    userRoleMap.keySet().forEach((userNo) -> {
                        parentPanel.setPreferredSize(new Dimension(315, parentPanel.getHeight() + 75 * userRoleMap.size()));
                        userPanel.setPreferredSize(new Dimension(315, 75 * userRoleMap.size()));
                        JPanel userLabelPanel = new JPanel(null);
                        userLabelPanel.setBackground(Color.white);
                        userLabelPanel.setBorder(BorderUtil.BORDER_WHITE_235);
                        userLabelPanel.setPreferredSize(DimensionUtil.DIM_315_75);
                        //已选图标
                        JLabel selectedIconLabel = new JLabel(selectedIcon);
                        selectedIconLabel.setBounds(265, 25, 25, 25);
                        MouseAdapter userMouseAdapter = new MouseAdapter() {
                            //添加视图
                            JLabel rightCellNameLabel;
                            JPanel rightCellPanel;

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                userTagListeningEvent();
                            }

                            /**
                             * @description 用户标签监听事件
                             * @author Murray Law
                             * @date 2020/12/21 8:38
                             * @param
                             * @return void
                             */
                            private void userTagListeningEvent() {
                                if (!selectedList.contains(userNo)) {
                                    rightCellPanel = new JPanel(null);
                                    rightCellPanel.setPreferredSize(DimensionUtil.DIM_315_40);
                                    rightCellPanel.setBorder(BorderUtil.BORDER_WHITE_235);
                                    rightCellPanel.setBackground(Color.white);

                                    rightCellNameLabel = new JLabel("      " + CHAT_USER_INFO_RESPONSE_MAP.get(userNo).getName());
                                    rightCellNameLabel.setIcon(PictureManipulationUtil.getUserAvatar(userNo, 25, 25));
                                    rightCellNameLabel.setBounds(20, 0, 315, 40);
                                    rightCellPanel.add(rightCellNameLabel);
                                    selectedPanel.add(rightCellPanel, GridBagUtil.wrap());
                                    selectedList.add(userNo);
                                    userLabelPanel.add(selectedIconLabel);
                                } else {
                                    selectedPanel.remove(selectedList.indexOf(userNo));
                                    selectedList.remove(userNo);
                                    userLabelPanel.remove(selectedIconLabel);
                                }
                                int size = selectedList.size();
                                if (size > 0) {
                                    confirmBtn.setEnabled(true);
                                } else {
                                    confirmBtn.setEnabled(false);
                                }
                                selectedLabel.setText("已选 " + size + "/100");
                                userLabelPanel.updateUI();
                                selectedPanel.updateUI();
                            }
                        };
                        userLabelPanel.addMouseListener(userMouseAdapter);

                        //头像
                        JLabel userAvatar = new JLabel(PictureManipulationUtil.getUserAvatar(userNo, 55, 55));
                        userAvatar.setBounds(30, 10, 55, 55);
                        userLabelPanel.add(userAvatar);

                        //名称
                        JLabel userName = new JLabel(CHAT_USER_INFO_RESPONSE_MAP.get(userNo).getName());
                        userName.setBounds(100, 10, 150, 55);
                        userLabelPanel.add(userName);
                        //是否已选
                        if (selectedList.contains(userNo)) {
                            userLabelPanel.add(selectedIconLabel);
                        }
                        userPanel.add(userLabelPanel, GridBagUtil.wrap());
                    });
                    flag = 1;
                    label.setText(afterStr);
                    parentPanel.repaint();

                } else {
                    flag = 0;
                    userPanel.removeAll();
                    userPanel.setPreferredSize(DimensionUtil.DIM_315_0);
                    parentPanel.setPreferredSize(new Dimension(315, parentPanel.getHeight() - 75 * userRoleMap.size()));
                    label.setText(beforeStr);
                }

            }
        }

        private class DepDetailedMouseAdapter extends MouseAdapter {
            private int flag;
            private String beforeStr, afterStr, key;

            private JLabel label;
            private JPanel userPanel, parentPanel;

            public DepDetailedMouseAdapter(JLabel label, JPanel parentPanel, JPanel userPanel, int flag, String beforeStr, String afterStr, String key) {
                this.parentPanel = parentPanel;
                this.userPanel = userPanel;
                this.label = label;
                this.flag = flag;
                this.beforeStr = beforeStr;
                this.afterStr = afterStr;
                this.key = key;

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Map<String, ChatUserInfoResponse> userInfoResponseMap = DEPARTMENT_MEMBER_RESPONSE_MAP.get(key);
                if (flag == 0) {
                    userInfoResponseMap.forEach((userNo, userInfoResponse) -> {
                        parentPanel.setPreferredSize(new Dimension(315, parentPanel.getHeight() + 75 * userInfoResponseMap.size()));
                        userPanel.setPreferredSize(new Dimension(315, 75 * userInfoResponseMap.size()));
                        JPanel userLabelPanel = new JPanel(null);
                        userLabelPanel.setBackground(Color.white);
                        userLabelPanel.setBorder(BorderUtil.BORDER_WHITE_235);
                        userLabelPanel.setPreferredSize(DimensionUtil.DIM_315_75);

                        JLabel selectedIconLabel = new JLabel(selectedIcon);
                        selectedIconLabel.setBounds(265, 25, 25, 25);
                        userLabelPanel.addMouseListener(new MouseAdapter() {
                            JPanel rightCellPanel;
                            JLabel rightCellNameLabel;

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                userTagListeningEvent();
                            }

                            /**
                             * @description 用户标签监听事件
                             * @author Murray Law
                             * @date 2020/12/21 8:38
                             * @param
                             * @return void
                             */
                            private void userTagListeningEvent() {
                                if (!selectedList.contains(userNo)) {
                                    rightCellPanel = new JPanel(null);
                                    rightCellPanel.setPreferredSize(DimensionUtil.DIM_315_40);
                                    rightCellPanel.setBorder(BorderUtil.BORDER_WHITE_235);
                                    rightCellPanel.setBackground(Color.white);

                                    rightCellNameLabel = new JLabel("      " + CHAT_USER_INFO_RESPONSE_MAP.get(userNo).getName());
                                    rightCellNameLabel.setIcon(PictureManipulationUtil.getUserAvatar(userNo, 25, 25));
                                    rightCellNameLabel.setBounds(20, 0, 315, 40);
                                    rightCellPanel.add(rightCellNameLabel);
                                    selectedPanel.add(rightCellPanel, GridBagUtil.wrap());
                                    selectedList.add(userNo);
                                    userLabelPanel.add(selectedIconLabel);
                                } else {
                                    selectedPanel.remove(selectedList.indexOf(userNo));
                                    selectedList.remove(userNo);
                                    userLabelPanel.remove(selectedIconLabel);
                                }
                                int size = selectedList.size();
                                if (size > 0) {
                                    confirmBtn.setEnabled(true);
                                } else {
                                    confirmBtn.setEnabled(false);
                                }
                                selectedLabel.setText("已选 " + size + "/100");
                                userLabelPanel.updateUI();
                                selectedPanel.updateUI();
                            }
                        });
                        //头像
                        JLabel userAvatar = new JLabel(PictureManipulationUtil.getUserAvatar(userInfoResponse.getChatUserNo(), 55, 55));
                        userAvatar.setBounds(30, 10, 55, 55);
                        userLabelPanel.add(userAvatar);

                        //名称
                        JLabel userName = new JLabel(userInfoResponse.getName());
                        userName.setBounds(100, 10, 150, 55);
                        userLabelPanel.add(userName);
                        //是否已选
                        if (selectedList.contains(userNo)) {
                            userLabelPanel.add(selectedIconLabel);
                        }
                        userPanel.add(userLabelPanel, GridBagUtil.wrap());
                    });
                    flag = 1;
                    label.setText(afterStr);
                    parentPanel.repaint();

                } else {
                    flag = 0;
                    userPanel.removeAll();
                    userPanel.setPreferredSize(DimensionUtil.DIM_315_0);
                    parentPanel.setPreferredSize(new Dimension(315, parentPanel.getHeight() - 75 * userInfoResponseMap.size()));
                    label.setText(beforeStr);
                }

            }
        }

    }
}
