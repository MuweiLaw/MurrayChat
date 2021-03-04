package com.murray.view.vo.frame;

import com.murray.dto.request.MailRequest;
import com.murray.dto.response.ChatUserInfoResponse;
import com.murray.entity.FilePacket;
import com.murray.entity.PrivateChatMessage;
import com.murray.handler.ClientPacketCodeCompile;
import com.murray.utils.*;
import com.murray.view.vo.cell.MailUserLabel;
import com.murray.view.vo.cell.MyFileJPanel;
import com.murray.view.vo.panel.AddressBookBasicScrollPanel;
import com.murray.view.vo.panel.RichTextPanel;
import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.*;

import static com.murray.agreement.MessageType.*;
import static com.murray.cache.ClientCache.*;
import static com.murray.handler.ClientPacketCodeCompile.encode;
import static com.murray.utils.JFameUtil.eventHandle;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

/**
 * @author Murray Law
 * @describe 发送邮件窗口
 * @createTime 2020/11/11
 */
public class SendMailFrame extends JFrame {
    private JPanel mainPanel, annexPane = new JPanel(), attachmentsPanel;
    private final RichTextPanel receiveListPanel = new RichTextPanel(), ccListPanel = new RichTextPanel();
    private final JTextPane receivePane = receiveListPanel.getTextPane(), ccPane = ccListPanel.getTextPane(), textPane = new JTextPane();
    private JButton confirmBtn = new JButton("发送");
    private final WindowsScrollBarUI windowsScrollBarUI = new WindowsScrollBarUI();
    private ImageIcon editIcon = PictureManipulationUtil.getImageIcon("v1icon/edit.png", 20, 20);
    private ImageIcon selectedIcon = PictureManipulationUtil.getImageIcon("v1icon/selectedState.png", 25, 25);

    private final int frameWidth = 1120, frameHeight = 890, fontX = 20, moduleX = 70, moduleWidth = 430, rightPanelWid = 375;
    private String counterpartChatNO;
    private List<String> selectedList = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();

    private boolean saveDraft;
    private JTextPane selectedTextPanel;

    public SendMailFrame(String counterpartChatNO) {
        this.counterpartChatNO = counterpartChatNO;
        setResizable(false);//不可调整
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        //设置主面板
        mainPanel = new JPanel();
        mainPanel.setBorder(null);
        mainPanel.setLayout(null);
        mainPanel.setBackground(ColorUtil.WHITE245);

        //发送邮件标签
        JLabel titleLabel = new JLabel("发送邮件", JLabel.CENTER);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(FontUtil.MICROSOFT_YA_HEI_B20);
        titleLabel.setBounds(0, 0, frameWidth - rightPanelWid, 80);
        mainPanel.add(titleLabel);

        //设置收件人输入框位置
        JLabel receiveLabel = new JLabel("收件人", JLabel.CENTER);
        receiveLabel.setOpaque(true);
        receiveLabel.setBackground(ColorUtil.WHITE235);
        receiveLabel.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        receiveLabel.setBounds(20, 80, 90, 50);

        receiveListPanel.setBounds(110, 80, frameWidth - rightPanelWid - 130, 50);
        receiveListPanel.setBorder(null);
        receiveListPanel.getHorizontalScrollBar().setUI(new WindowsScrollBarUI());
        //输入框事件,只能删除
        receivePane.setBackground(ColorUtil.WHITE235);
        receivePane.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                selectedTextPanel = receivePane;
            }
        });
        receivePane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (keyChar != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                selectedList = receiveListPanel.getMailUserList();
                selectedList.addAll(ccListPanel.getMailUserList());
            }
        });

        //设置收件人输入框位置
        JLabel ccLabel = new JLabel("抄送", JLabel.CENTER);
        ccLabel.setOpaque(true);
        ccLabel.setBackground(ColorUtil.WHITE235);
        ccLabel.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        ccLabel.setBounds(20, 140, 90, 50);

        ccListPanel.setBounds(110, 140, frameWidth - rightPanelWid - 130, 50);
        ccListPanel.setBackground(ColorUtil.WHITE235);
        ccListPanel.getHorizontalScrollBar().setUI(new WindowsScrollBarUI());
        ccListPanel.setBorder(null);

        //输入框事件,只能删除
        ccPane.setBackground(ColorUtil.WHITE235);
        ccPane.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                selectedTextPanel = ccPane;
            }
        });
        ccPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (keyChar != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                selectedList = ccListPanel.getMailUserList();
                selectedList.addAll(receiveListPanel.getMailUserList());
            }
        });
        //预先设置邮件接收人
        if (CHAT_MAIN_FRAME.chatType.equals(PRIVATE_CHAT_TYPE)) {//单聊
            selectedList.add(counterpartChatNO);
            ChatUserInfoResponse userInfoResponse = CHAT_USER_INFO_RESPONSE_MAP.get(counterpartChatNO);
            receivePane.insertComponent(new MailUserLabel(userInfoResponse.getName(), counterpartChatNO, editIcon));
        }
        if (CHAT_MAIN_FRAME.chatType.equals(GROUP_CHAT_TYPE)) {//群聊
            GROUP_BASIC_MAP.get(counterpartChatNO).getUserRoleMap().keySet().forEach(userNo -> {
                selectedList.add(userNo);
                ChatUserInfoResponse userInfoResponse = CHAT_USER_INFO_RESPONSE_MAP.get(userNo);
                receivePane.insertComponent(new MailUserLabel(userInfoResponse.getName(), userInfoResponse.getChatUserNo(), editIcon));
            });
        }
        //主题输入框
        JLabel titLabel = new JLabel("主题", JLabel.CENTER);
        titLabel.setOpaque(true);
        titLabel.setBackground(ColorUtil.WHITE235);
        titLabel.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        titLabel.setBounds(20, 200, 90, 50);
        JTextField titleArea = new JTextField();
        titleArea.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        titleArea.setBorder(null);
        titleArea.setBounds(110, 200, frameWidth - rightPanelWid - 130, 50);
        titleArea.setBackground(ColorUtil.WHITE235);

        //附件列表
        JLabel addAnnexLabel = new JLabel(PictureManipulationUtil.getImageIcon("v1icon/annex.png", 20, 20), JLabel.LEFT);
        addAnnexLabel.setText("点击添加附件 / 拖入附件");
        addAnnexLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        addAnnexLabel.setBounds(20, 260, frameWidth - rightPanelWid - 40, 50);
        addAnnexLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();             //设置选择器
                chooser.setMultiSelectionEnabled(true);             //设为多选
                int returnVal = chooser.showOpenDialog(null);        //是否打开文件选择框
                if (returnVal == JFileChooser.APPROVE_OPTION) {          //如果符合文件类型
                    File[] selectedFiles = chooser.getSelectedFiles();
                    for (File file : selectedFiles) {
                        annexPane.add(new MyFileJPanel(file));
                        annexPane.updateUI();
                        fileList.add(file);
                    }
                    if (selectedFiles.length > 0) {
                        confirmBtn.setEnabled(true);
                    }
                }
                chooser.updateUI();

            }
        });
        //文件滑动面板
        JScrollPane annexScrollPane = new JScrollPane();
        annexScrollPane.setBorder(null);
        annexScrollPane.getHorizontalScrollBar().setUI(new WindowsScrollBarUI());
        annexScrollPane.setViewportView(annexPane);
        annexScrollPane.setBounds(20, 320, frameWidth - rightPanelWid - 40, 90);
        //正文滑动面板
        JScrollPane textScrollPane = new JScrollPane();
        textScrollPane.getVerticalScrollBar().setUI(windowsScrollBarUI);
        textPane.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        textPane.setBackground(Color.white);
        textPane.setForeground(Color.lightGray);
        textPane.setText("请在此处输入正文");
        textPane.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textPane.getText().equals("请在此处输入正文")) {
                    textPane.setForeground(null);
                    textPane.setText(null);
                }
            }
        });
        textScrollPane.setViewportView(textPane);
        textScrollPane.setBorder(null);
        textScrollPane.setBounds(20, 430, frameWidth - rightPanelWid - 40, frameHeight - 500);

        //右侧用户选择面板
        SelectUserScrollPanel selectUserScrollPanel = new SelectUserScrollPanel(375, getHeight());
        selectUserScrollPanel.setBorder(BorderUtil.BORDER_WHITE_235);
        selectUserScrollPanel.setBounds(frameWidth - 375, 0, 375, getHeight());

        //发送按钮
        confirmBtn.setBackground(ColorUtil.UNREAD_COLOR);
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setBounds(20, frameHeight - 55, 100, 45);
        confirmBtn.setBorder(null);
        confirmBtn.setEnabled(true);
        confirmBtn.addActionListener(e -> {
            //要接收的用户编号
            String[] addressee = receiveListPanel.getMailUserArray();
            //要抄送的用户编号
            String[] ccArray = ccListPanel.getMailUserArray();
            String title = titleArea.getText();
            String text = textPane.getText();
            HashMap<String, String> fileNoAndNameMap = new HashMap<>();
            //发送文件消息
            for (File file : fileList) {
                String fileNo = UUID.randomUUID().toString();
                FilePacket filePacket = new FilePacket();
                filePacket.setFile(file);
                //创建邮件对象
                filePacket.setFileNo(fileNo);
                filePacket.setFileName(file.getName());
                //将自己的消息保存本地内存
                fileNoAndNameMap.put(fileNo, file.getName());
                CHANNEL.writeAndFlush(encode(filePacket));
            }

            MailRequest mailRequest = new MailRequest(addressee, ccArray, title, text, chatUserByLogIn.getChatUserNo(), fileNoAndNameMap, new Date(), false);
            CHANNEL.writeAndFlush(ClientPacketCodeCompile.encode(mailRequest));
            for (String s : selectedList) {
                //创建消息对象
                PrivateChatMessage privateMailMessage = new PrivateChatMessage(chatUserByLogIn.getChatUserNo(), "(" + title + ")" + text, s, new Date(), false, PRIVATE_MAIL_TYPE);
                PRIVATE_CHAT_MESSAGE_MAP.put(privateMailMessage.getMsgNo(), privateMailMessage);
                CHANNEL.writeAndFlush(encode(privateMailMessage));
            }
            if (CHAT_MAIN_FRAME.chatType.equals(PRIVATE_CHAT_TYPE)) {
                CHAT_MAIN_FRAME.getPrivateChat(CHAT_USER_INFO_RESPONSE_MAP.get(CHAT_MAIN_FRAME.clickOnMsgCellNo).getName(), CHAT_MAIN_FRAME.clickOnMsgCellNo);
            } else if (CHAT_MAIN_FRAME.chatType.equals(GROUP_CHAT_TYPE)) {
                CHAT_MAIN_FRAME.getGroupChat(GROUP_BASIC_MAP.get(CHAT_MAIN_FRAME.clickOnMsgCellNo).getGroupName(), CHAT_MAIN_FRAME.clickOnMsgCellNo);
            }
            this.dispose();
        });

        //添加组件
        mainPanel.add(receiveLabel);
        mainPanel.add(receiveListPanel);
        mainPanel.add(ccLabel);
        mainPanel.add(ccListPanel);
        mainPanel.add(titLabel);
        mainPanel.add(titleArea);
        mainPanel.add(addAnnexLabel);
        mainPanel.add(annexScrollPane);
        mainPanel.add(textScrollPane);
        mainPanel.add(selectUserScrollPanel);
        mainPanel.add(confirmBtn);
        setContentPane(mainPanel);
        setVisible(true);//一定要放在最后面
        eventHandle(titleLabel, this);//设置可拖动
        receivePane.requestFocus();
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
            jScrollPane.getVerticalScrollBar().setUI(windowsScrollBarUI);
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
            recentLabel.setPreferredSize(DimensionUtil.DIM_375_80);
            recentLabel.setBorder(BorderUtil.BORDER_WHITE_235);
            recentLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
            recentLabel.addMouseListener(new SelectUserScrollPanel.SelectBasicMouseAdapter(recentLabel, 0, beforeRecent, afterRecent));
            userListPanel.add(recentLabel, GridBagUtil.wrap());
            //群聊
            groupChatLabel.setPreferredSize(DimensionUtil.DIM_375_80);
            groupChatLabel.setBorder(BorderUtil.BORDER_WHITE_235);
            groupChatLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
            groupChatLabel.addMouseListener(new SelectUserScrollPanel.SelectBasicMouseAdapter(groupChatLabel, 0, beforeGroupChat, afterGroupChat));
            userListPanel.add(groupChatLabel, GridBagUtil.wrap());
            //群聊基本展示面板
            groupChatPanel.setPreferredSize(DimensionUtil.DIM_375_0);
            userListPanel.add(groupChatPanel, GridBagUtil.wrap());
            //部门
            depLabel.setPreferredSize(DimensionUtil.DIM_375_80);
            depLabel.setBorder(BorderUtil.BORDER_WHITE_235);
            depLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
            depLabel.addMouseListener(new SelectUserScrollPanel.SelectBasicMouseAdapter(depLabel, 0, beforeDepartment, afterDepartment));
            userListPanel.add(depLabel, GridBagUtil.wrap());
            //部门基本展示面板
            depPanel.setPreferredSize(DimensionUtil.DIM_375_0);
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
                            groupChatPanel.setPreferredSize(new Dimension(375, 40 * GROUP_BASIC_MAP.size()));
                            JLabel groupNameLabel = new JLabel(beforeNodePrefix + groupBasic.getGroupName());
                            groupNameLabel.setOpaque(true);
                            groupNameLabel.setBackground(Color.white);
                            groupNameLabel.setBorder(BorderUtil.BORDER_WHITE_235);
                            groupNameLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
                            groupNameLabel.setPreferredSize(DimensionUtil.DIM_375_40);
                            groupChatPanel.add(groupNameLabel, GridBagUtil.wrap());
                            //叶子面板
                            JPanel userPanel = new JPanel(GridBagUtil.getGridBagLayout());
                            userPanel.setPreferredSize(DimensionUtil.DIM_375_0);
                            groupChatPanel.add(userPanel, GridBagUtil.wrap());
                            groupNameLabel.addMouseListener(new SelectUserScrollPanel.GroupDetailedMouseAdapter(groupNameLabel, groupChatPanel, userPanel, 0, beforeNodePrefix + groupBasic.getGroupName(), afterNodePrefix + groupBasic.getGroupName(), groupBasic.getGroupNo()));

                        });
                    } else {
                        groupChatPanel.removeAll();
                        groupChatPanel.setPreferredSize(DimensionUtil.DIM_375_0);
                    }
                }

                if (label.getText().contains("部门")) {
                    if (flag == 0) {
                        AddressBookBasicScrollPanel.departmentMap.forEach((depName, departmentResponse) -> {
                            depPanel.setPreferredSize(new Dimension(375, 40 * AddressBookBasicScrollPanel.departmentMap.size()));
                            //部门名称
                            JLabel depNameLabel = new JLabel(beforeNodePrefix + depName);
                            depNameLabel.setOpaque(true);
                            depNameLabel.setBackground(Color.white);
                            depNameLabel.setBorder(BorderUtil.BORDER_WHITE_235);
                            depNameLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
                            depNameLabel.setPreferredSize(DimensionUtil.DIM_375_40);
                            depPanel.add(depNameLabel, GridBagUtil.wrap());
                            //叶子面板
                            JPanel userPanel = new JPanel(GridBagUtil.getGridBagLayout());
                            userPanel.setPreferredSize(DimensionUtil.DIM_375_0);
                            depPanel.add(userPanel, GridBagUtil.wrap());
                            depNameLabel.addMouseListener(new SelectUserScrollPanel.DepDetailedMouseAdapter(depNameLabel, depPanel, userPanel, 0, beforeNodePrefix + depName, afterNodePrefix + depName, departmentResponse.getDepartmentNo()));
                        });
                    } else {
                        depPanel.removeAll();
                        depPanel.setPreferredSize(DimensionUtil.DIM_375_0);
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

        /**
         * 群聊详细监听器
         */
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
                        parentPanel.setPreferredSize(new Dimension(375, parentPanel.getHeight() + 80 * userRoleMap.size()));
                        userPanel.setPreferredSize(new Dimension(375, 80 * userRoleMap.size()));
                        JPanel userLabelPanel = new JPanel(null);
                        userLabelPanel.setBackground(Color.white);
                        userLabelPanel.setBorder(BorderUtil.BORDER_WHITE_235);
                        userLabelPanel.setPreferredSize(DimensionUtil.DIM_375_80);
                        MouseAdapter userMouseAdapter = new MouseAdapter() {
                            ChatUserInfoResponse chatUserInfoResponse = CHAT_USER_INFO_RESPONSE_MAP.get(userNo);

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (!selectedList.contains(userNo)) {
                                    MailUserLabel receiverLabel = new MailUserLabel(chatUserInfoResponse.getName(), chatUserInfoResponse.getChatUserNo(), editIcon);
                                    selectedTextPanel.insertComponent(receiverLabel);
                                    selectedList.add(userNo);
                                    JLabel selectedIconLabel = new JLabel(selectedIcon);
                                    selectedIconLabel.setBounds(325, 25, 25, 25);
                                    userLabelPanel.add(selectedIconLabel);
                                    userLabelPanel.updateUI();
                                }
                                int size = selectedList.size();
                                if (size > 0) {
                                    confirmBtn.setEnabled(true);
                                } else {
                                    confirmBtn.setEnabled(false);
                                }
                                userLabelPanel.updateUI();
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
                        if (selectedList.contains(userNo)) {
                            JLabel selectedIconLabel = new JLabel(selectedIcon);
                            selectedIconLabel.setBounds(325, 25, 25, 25);
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
                    userPanel.setPreferredSize(DimensionUtil.DIM_375_0);
                    parentPanel.setPreferredSize(new Dimension(375, parentPanel.getHeight() - 80 * userRoleMap.size()));
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
                        parentPanel.setPreferredSize(new Dimension(375, parentPanel.getHeight() + 80 * userInfoResponseMap.size()));
                        userPanel.setPreferredSize(new Dimension(375, 80 * userInfoResponseMap.size()));
                        JPanel userLabelPanel = new JPanel(null);
                        userLabelPanel.setBackground(Color.white);
                        userLabelPanel.setBorder(BorderUtil.BORDER_WHITE_235);
                        userLabelPanel.setPreferredSize(DimensionUtil.DIM_375_80);
                        userLabelPanel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (!selectedList.contains(userInfoResponse.getChatUserNo())) {
                                    selectedList.add(userInfoResponse.getChatUserNo());
                                    JLabel selectedIconLabel = new JLabel(selectedIcon);
                                    selectedIconLabel.setBounds(325, 25, 25, 25);
                                    userLabelPanel.add(selectedIconLabel);
                                    MailUserLabel receiverLabel = new MailUserLabel(userInfoResponse.getName(), userInfoResponse.getChatUserNo(), editIcon);
                                    selectedTextPanel.insertComponent(receiverLabel);
                                }
                                int size = selectedList.size();
                                if (size > 0) {
                                    confirmBtn.setEnabled(true);
                                } else {
                                    confirmBtn.setEnabled(false);
                                }
                                userLabelPanel.updateUI();
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
                        //已读设置
                        if (selectedList.contains(userNo)) {
                            JLabel selectedIconLabel = new JLabel(selectedIcon);
                            selectedIconLabel.setBounds(325, 25, 25, 25);
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
                    userPanel.setPreferredSize(DimensionUtil.DIM_375_0);
                    parentPanel.setPreferredSize(new Dimension(375, parentPanel.getHeight() - 80 * userInfoResponseMap.size()));
                    label.setText(beforeStr);
                }

            }
        }

    }


}
