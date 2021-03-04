package com.murray.view.vo.frame;


import com.murray.cache.ClientCache;
import com.murray.dto.request.AddressBookRequest;
import com.murray.dto.request.ChatUserInfoRequest;
import com.murray.dto.request.GroupBasicRequest;
import com.murray.dto.request.GroupMsgRequest;
import com.murray.dto.response.GroupMsgResponse;
import com.murray.entity.FilePacket;
import com.murray.entity.PrivateChatMessage;
import com.murray.utils.*;
import com.murray.view.dto.Notification;
import com.murray.view.vo.cell.MsgCell;
import com.murray.view.vo.cell.MyFileJPanel;
import com.murray.view.vo.panel.*;
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
import static com.murray.utils.BorderUtil.BORDER_LIGHT_GRAY;
import static com.murray.utils.BorderUtil.BORDER_WHITE_235;
import static com.murray.utils.ColorUtil.MAIN_LEFT_COLOR;
import static com.murray.utils.ColorUtil.WHITE245;
import static com.murray.utils.FontUtil.*;
import static com.murray.utils.JFameUtil.eventHandle;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.Frame.NORMAL;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;


/**
 * @author Murray Law
 * @describe User 用户聊天主面板窗口
 */
public class ChatMainFrame {
    public JFrame mainFrame;
    public OAFrame oaFrame;
    public GroupInfoFrame groupInfoFrame;
    public FriendsListPanel friendsListPanel;
    public JPanel mainPanel = new JPanel(null), headPanel, leftPanel, searchPanel = new JPanel(), chatHead, chatPanel = new JPanel(), centerPanel, underPanel, msgPanel = new JPanel(new GridBagLayout()), sendPanel = new JPanel(null);
    private JScrollPane msgScrollPanel = new JScrollPane();
    private JTextPane textPane;
    private JSplitPane splitPane = new JSplitPane();
    public JButton headSculpture, addFriendButton, zoomBtn, exitBtn, maximizeButton, fileBtn, mailBtn, sendButton;
    public JLabel nameLabel, explainLabel, profilePictureLabel = new JLabel(), objNameLabel = new JLabel(), addressBookBar = new JLabel();
    public ImageIcon headSculptureImageIcon, profilePicture = null;
    public final WindowsScrollBarUI windowsScrollBarUI=new WindowsScrollBarUI();
    private JTabbedPane addressBookTabPane, mainTabbedPane;
    private MouseAdapter mouseAdapter;
    public PrivateMSGPanel privateMSGPanel;
    public GroupMSGPanel groupMSGPanel;

    private RichTextPanel richTextPanel;
    public AddressBookBasicScrollPanel addressBookBasicScrollPanel = new AddressBookBasicScrollPanel();
    public AddressBookMemberScrollPanel addressBookMemberScrollPanel = new AddressBookMemberScrollPanel();

    private final Map<String, Integer> privateChatNoticeMap = new HashMap<>();
    private final Map<String, Integer> groupChatNoticeMap = new HashMap<>();
    public final Map<String, MsgCell> msgCellMap = new HashMap<>();
    private final Map<String, JLabel> xLabelMap = new HashMap<>();
    private final List<String> atUserNoList = new ArrayList<>();
    private static final ArrayList<Notification> notificationArrayList = new ArrayList<>();
    private final String COMMAND_MSG_PANEL = "msgPanel";
    private final String COMMAND_ADDRESS_BOOK_PANEL = "addressBookPanel";
    private final String COMMAND_SEND_MSG = "sendMsg";
    private final String COMMAND_OPEN_FILE_CHOOSER = "openFileChooser";
    private final String COMMAND_OPEN_MAIL_FRAME = "openMailFrame";
    private final String COMMAND_REMOVE_MSG_LIST_CELL = "removeMsgListCell";
    private final String COMMAND_CREATE_GROUP = "createGroup";
    private int frameWid = 1200, frameHei = 800;
    public String clickOnMsgCellNo = null, enteredMsgCellNo = null;
    public Byte chatType = 0;

    // 创建一个动作监听器实例
    public final ActionListener actionListener = e -> {
        // 获取事件源，即触发事件的组件（按钮）本身
        //e.getSource();
        // 获取动作命令
        String command = e.getActionCommand();

        // 根据动作命令区分被点击的按钮
        switch (command) {
            case COMMAND_MSG_PANEL://打开消息面板
                mainFrame.remove(addressBookBar);
                mainFrame.remove(addressBookBasicScrollPanel);
                mainFrame.remove(addressBookMemberScrollPanel);
//                addressBookBasicScrollPanel.setSelected(null);
                //消息列表
                msgList();
                //刷新聊天面板
                refreshMsgScrollPanel();
                if (clickOnMsgCellNo != null && msgCellMap.containsKey(clickOnMsgCellNo)) {
                    msgCellMap.get(clickOnMsgCellNo).setBackground(Color.white);
                }
                //聊天面板
                chat();
                mainFrame.repaint();
                break;
            case COMMAND_ADDRESS_BOOK_PANEL://打开通讯录
                mainFrame.remove(searchPanel);
                mainFrame.remove(msgScrollPanel);
                mainFrame.remove(chatPanel);
                privateMSGPanel = null;
                groupMSGPanel = null;

                addressBook();
                mainFrame.repaint();
                break;
            case COMMAND_CREATE_GROUP://创建群组
                CHANNEL.writeAndFlush(encode(new AddressBookRequest(chatUserByLogIn.getCompany(), chatUserByLogIn.getChatUserNo())));
                new CreateGroupFrame(null);
                break;
            case COMMAND_OPEN_FILE_CHOOSER:
                JFileChooser chooser = new JFileChooser();             //设置选择器
                chooser.setMultiSelectionEnabled(true);             //设为多选
                int returnVal = chooser.showOpenDialog(CHAT_MAIN_FRAME.mainFrame);        //是否打开文件选择框
                if (returnVal == JFileChooser.APPROVE_OPTION) {          //如果符合文件类型
                    File[] selectedFiles = chooser.getSelectedFiles();
                    for (File file : selectedFiles) {
                        richTextPanel.insertComponent(new MyFileJPanel(file));
                    }
                    if (selectedFiles.length > 0) {
                        sendButton.setEnabled(true);
                    }
                }
                textPane.requestFocus();
                textPane.grabFocus();
                break;
            case COMMAND_SEND_MSG:
                sendMsg();
                break;
            case COMMAND_OPEN_MAIL_FRAME:
                CHANNEL.writeAndFlush(encode(new AddressBookRequest(chatUserByLogIn.getCompany(), chatUserByLogIn.getChatUserNo())));
                new SendMailFrame(clickOnMsgCellNo);
                break;

        }
    };


    //富文本面板的快捷键监听
    AbstractAction sendMsgListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendMsg();
        }
    };

    /**
     * @param
     * @return void
     * @description 窗口初始化
     * @author Murray Law
     * @date 2020/10/22 21:43
     */
    public void frameInit() {
        //设置窗口的UI风格和字体
        mainFrame = new JFrame("用户聊天主界面");
        //隐藏windows的窗格边框
        mainFrame.setUndecorated(true);
        /*设置初始窗口的一些性质*/
        mainFrame.setSize(frameWid, frameHei);
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setResizable(false);//不可调整
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setContentPane(mainPanel);


        //消息发送面板
        sendPanel.setBackground(Color.white);
        //聊天表情图标
        JLabel emoticonLabel = new JLabel();
        ImageIcon emoticonIcon = PictureManipulationUtil.getImageIcon("icon/expression.png", 20, 20);
        emoticonLabel.setToolTipText(" 发送表情 ");
        emoticonLabel.setIcon(emoticonIcon);
        emoticonLabel.setBounds(15, 15, 20, 20);
        sendPanel.add(emoticonLabel);
        //聊天文件图标
        fileBtn = new JButton();
        ImageIcon fileIcon = PictureManipulationUtil.getImageIcon("icon/file.png", 20, 20);
        fileBtn.setIcon(fileIcon);
        fileBtn.setText(null);
        fileBtn.setBorder(null);
        fileBtn.setBounds(60, 15, 20, 20);
        fileBtn.addActionListener(actionListener);
        fileBtn.setActionCommand(COMMAND_OPEN_FILE_CHOOSER);
        sendPanel.add(fileBtn);
        //聊天附件图标
        mailBtn = new JButton();
        ImageIcon mailIcon = PictureManipulationUtil.getImageIcon("v1icon/mailIcon.png", 20, 15);
        mailBtn.setToolTipText(" 发送邮件 ");
        mailBtn.setIcon(mailIcon);
        mailBtn.setBackground(null);
        mailBtn.setText(null);
        mailBtn.setBounds(105, 15, 20, 15);
        mailBtn.addActionListener(actionListener);
        mailBtn.setActionCommand(COMMAND_OPEN_MAIL_FRAME);
        sendPanel.add(mailBtn);

        //发送按钮
        sendButton = new JButton("发送");
        sendButton.setBackground(MAIN_LEFT_COLOR);
        sendButton.setBorder(null);
        sendButton.setForeground(Color.white);
        sendButton.setFocusable(false);
        sendButton.addActionListener(actionListener);
        sendButton.setActionCommand(COMMAND_SEND_MSG);
        sendButton.setEnabled(false);
        sendPanel.add(sendButton);
        //示例
        Font font14 = new Font("微软雅黑", Font.PLAIN, 14);
        JLabel sendMailLabel = new JLabel("Enter换行/Ctrl+Enter发送");
        sendMailLabel.setForeground(Color.gray);
        sendMailLabel.setFont(font14);
        //富文本输入框
        richTextPanel = new RichTextPanel(sendPanel, sendButton, 15, 50, mainFrame.getWidth() - 435, 110);
        richTextPanel.setBackground(Color.white);
        richTextPanel.getVerticalScrollBar().setUI(new WindowsScrollBarUI());
        //输入面板
        textPane = richTextPanel.getTextPane();
        textPane.setBackground(Color.white);
        textPane.setBorder(null);
        textPane.setFont(MICROSOFT_YA_HEI_16);
        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (richTextPanel.getMyFileJPanelAttrSum() > 0 || (!textPane.getText().isEmpty() && !textPane.getText().equals(" "))) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }
        });
        textPane.getInputMap().put(KeyStroke.getKeyStroke('\n', java.awt.event.InputEvent.CTRL_MASK), "sendMsg");
        //将"send"和sendMsg Action关联
        textPane.getActionMap().put("sendMsg", sendMsgListener);
        sendPanel.add(sendMailLabel);

//        try { // 使用Windows的界面风格
//            UIManager
//                    .setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //左边导航栏
        left();
        //头部
        head();


        //监听拖动
        splitPane.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
                if (splitPane.getDividerLocation() < 140) {
                    splitPane.setDividerLocation(140);
                }
                int sendY = splitPane.getHeight() - splitPane.getDividerLocation() - 50;
                richTextPanel.setBounds(15, 50, mainFrame.getWidth() - 435, sendY - 60);
                sendMailLabel.setBounds(mainFrame.getWidth() - 700, sendY, 200, 35);
                sendButton.setBounds(mainFrame.getWidth() - 500, sendY, 80, 35);
                splitPane.repaint();
            }
        });

        switch (openPanelType) {
            case 0:
                //消息列表
                msgList();
                //聊天面板
                chat();
                break;
            case 1:
                addressBook();
        }
        mainPanel.setVisible(true);
        //窗口显示
        mainFrame.setVisible(true);
        //设置可拖动
        eventHandle(headPanel, mainFrame);
    }


    /**
     * @param
     * @return void
     * @description 设置头部
     * @author Murray Law
     * @date 2020/11/1 22:22
     */
    private void head() {
        //设置头部panel的布局和大小
        headPanel = new JPanel();
        headPanel.setLayout(null);
        headPanel.setBackground(Color.white);
        //缩小按钮
        zoomBtn = OperationalBtnUtil.getZoomOutButton(mainFrame, headPanel, frameWid - 90, 15, 15, 15);
        //放大按钮
        getMaximizeButton(headPanel, frameWid - 60, 15, 15, 15);
        //退出按钮
        exitBtn = OperationalBtnUtil.getExitButton(mainFrame, headPanel, frameWid - 30, 15, 15, 15);


        //昵称
        nameLabel = new JLabel(chatUserByLogIn.getName());
        nameLabel.setBounds(150, 20, 200, 30);
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        //签名
        explainLabel = new JLabel(chatUserByLogIn.getSignature());
        explainLabel.setBounds(150, 60, 200, 30);

        //头部设置透明并添加标签
//        headPanel.add(headSculpture);
//        headPanel.add(nameLabel);
//        headPanel.add(explainLabel);
        headPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        headPanel.setBounds(0, 0, mainFrame.getWidth(), 50);
        mainPanel.add(headPanel);
    }

    private void left() {
        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 80, mainFrame.getHeight());
//        leftPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        leftPanel.setBackground(MAIN_LEFT_COLOR);
        //设置头像缩略大小，并面板布局中
        headSculptureImageIcon = PictureManipulationUtil.getUserAvatar(chatUserByLogIn.getChatUserNo(), 40, 40);
        //图片放入头像按钮
        JLabel avatarLabel = new JLabel(AVATAR_FRAME_BLUE);
        avatarLabel.setBounds(20, 40, 40, 40);
        leftPanel.add(avatarLabel);
        headSculpture = new JButton();
        headSculpture.setSize(headSculptureImageIcon.getImage().getWidth(null), headSculptureImageIcon.getImage().getHeight(null));
        headSculpture.setIcon(headSculptureImageIcon);
        headSculpture.setMargin(new Insets(0, 0, 0, 0));//将边框外的上下左右空间设置为0
        headSculpture.setBorderPainted(false);//不打印边框
        headSculpture.setFocusPainted(false);//除去焦点的框
        headSculpture.setBorder(null);//除去边框
        headSculpture.setText(null);//除去按钮的默认名称
        headSculpture.setContentAreaFilled(false);//除去默认的背景填充
        headSculpture.setBounds(20, 40, 40, 40);

        //消息图标按钮
        ImageIcon msgImageIcon = PictureManipulationUtil.getImageIcon("v1icon/chatIcon.png", 30, 30);
        JButton msgBtn = new JButton();
        msgBtn.setSize(msgImageIcon.getImage().getWidth(null), msgImageIcon.getImage().getHeight(null));
        msgBtn.setIcon(msgImageIcon);
        msgBtn.setMargin(new Insets(0, 0, 0, 0));//将边框外的上下左右空间设置为0
        msgBtn.setBorderPainted(false);//不打印边框
        msgBtn.setFocusPainted(false);//除去焦点的框
        msgBtn.setBorder(null);//除去边框
        msgBtn.setText(null);//除去按钮的默认名称
        msgBtn.setContentAreaFilled(false);//除去默认的背景填充
        msgBtn.setBounds(25, 100, 30, 30);
        //默认选中
        msgBtn.addActionListener(actionListener);
        msgBtn.setActionCommand(COMMAND_MSG_PANEL);
        //消息标签
        JLabel msgLabel = new JLabel("消息", JLabel.CENTER);
        msgLabel.setBounds(20, 140, 40, 20);
        msgLabel.setForeground(Color.WHITE);
        //通讯录图标按钮
        ImageIcon addressBookImageIcon = PictureManipulationUtil.getImageIcon("v1icon/addressBook.png", 30, 30);
        JButton addressBookBtn = new JButton();
        addressBookBtn.setSize(addressBookImageIcon.getImage().getWidth(null), addressBookImageIcon.getImage().getHeight(null));
        addressBookBtn.setIcon(addressBookImageIcon);
        addressBookBtn.setMargin(new Insets(0, 0, 0, 0));//将边框外的上下左右空间设置为0
        addressBookBtn.setBorderPainted(false);//不打印边框
        addressBookBtn.setFocusPainted(false);//除去焦点的框
        addressBookBtn.setBorder(null);//除去边框
        addressBookBtn.setText(null);//除去按钮的默认名称
        addressBookBtn.setContentAreaFilled(false);//除去默认的背景填充
        addressBookBtn.setBounds(25, 170, 30, 30);
        addressBookBtn.addActionListener(actionListener);
        addressBookBtn.setActionCommand(COMMAND_ADDRESS_BOOK_PANEL);
        //通讯录标签
        JLabel addressBookLabel = new JLabel("通讯录", JLabel.CENTER);
        addressBookLabel.setBounds(10, 210, 60, 20);
        addressBookLabel.setForeground(Color.WHITE);
        leftPanel.add(headSculpture);
        leftPanel.add(msgBtn);
        leftPanel.add(msgLabel);
        leftPanel.add(addressBookBtn);
        leftPanel.add(addressBookLabel);
        mainPanel.add(leftPanel);
    }

    public void msgList() {
        Font searchFont = MICROSOFT_YA_HEI_B16;
        //搜索区域
        searchPanel.setBackground(Color.white);
        searchPanel.setLayout(null);
        searchPanel.setBounds(79, 49, 326, 82);
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        //搜索边框
        JLabel border = new JLabel();
        border.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        border.setBounds(15, 15, 250, 50);
        Border roundedBorder = new RoundBorder(Color.lightGray);
        border.setBorder(roundedBorder);
        searchPanel.add(border);
        //搜索图标
        JLabel searchIconLabel = new JLabel();
        ImageIcon searchIcon = PictureManipulationUtil.getImageIcon("v1icon/searchMedium.jpg", 20, 20);
        searchIconLabel.setIcon(searchIcon);
        searchIconLabel.setBounds(30, 25, 30, 30);
        searchPanel.add(searchIconLabel);
        //搜索文字
        JLabel text = new JLabel("搜索");
        text.setFont(searchFont);
        text.setForeground(Color.lightGray);
        text.setBounds(60, 15, 80, 50);
        searchPanel.add(text);
        //搜索框
        JTextField searchFiled = new JTextField();
        searchFiled.setBounds(60, 15, 200, 50);
        searchFiled.setFont(searchFont);
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
        searchPanel.add(searchFiled);
        //创建群聊图标
        JButton createGroupBtn = new JButton();
        ImageIcon createGroupIcon = PictureManipulationUtil.getImageIcon("v1icon/leftCreateGroup.png", 20, 23);
        createGroupBtn.setIcon(createGroupIcon);
        createGroupBtn.setFocusPainted(false);
        createGroupBtn.setBackground(null);
        createGroupBtn.setBounds(325 - 45, 27, 30, 30);
        createGroupBtn.setText(null);
        createGroupBtn.setBorder(BORDER_WHITE_235);
        createGroupBtn.addActionListener(actionListener);
        createGroupBtn.setActionCommand(COMMAND_CREATE_GROUP);
        createGroupBtn.setToolTipText(" 创建群组 ");
        searchPanel.add(createGroupBtn);
        //消息列表
        refreshMsgScrollPanel();
        msgScrollPanel.setBounds(79, 130, 326, mainFrame.getHeight() - 127);
        msgScrollPanel.setBorder(BORDER_LIGHT_GRAY);
        msgScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        mainPanel.add(searchPanel);
        mainPanel.add(msgScrollPanel);
    }

    /**
     * @param
     * @return void
     * @description 刷新消息通知面板
     * @author Murray Law
     * @date 2020/12/2 18:56
     */
    public void refreshMsgScrollPanel() {
        //清空
        msgPanel.removeAll();
        Dimension max = new Dimension(324, 75);
        getNotificationArray();
        notificationArrayList.forEach(notification -> {
            MsgCell msgCell = new MsgCell(notification);
            if (notification.getNotificationNO().equals(clickOnMsgCellNo)) {
                msgCell.setBackground(Color.lightGray);
            }
            if (notificationArrayList.indexOf(notification) % 2 == 1) {
                msgCell.setBorder(BORDER_WHITE_235);
            }
            msgCell.setPreferredSize(max);
            JLabel xLabel = new JLabel("x");
            xLabel.setForeground(Color.gray);
            xLabel.setFocusable(false);
            xLabel.setBounds(5, 32, 10, 10);
            //如果点击则清空相关数据
            xLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (notification.getOpenType() == 0) {
                        PRIVATE_CHAT_MESSAGE_MAP.forEach((key, privateChatMessage) -> {
                            if (privateChatMessage.getSenderUserNo().equals(notification.getNotificationNO()))
                                PRIVATE_CHAT_MESSAGE_MAP.remove(key);
                        });
                    }

                    if (notification.getOpenType() == 1) {
                        GROUP_MSG_MAP.forEach((key, groupMsgResponse) -> {
                            if (groupMsgResponse.getGroupNo().equals(notification.getNotificationNO()))
                                GROUP_MSG_MAP.remove(key);
                        });
                    }
                    refreshMsgScrollPanel();
                    chat();
                }
            });
            //添加鼠标监听器,改变状态
            msgCell.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (null != enteredMsgCellNo) {
                        if (!enteredMsgCellNo.equals(clickOnMsgCellNo)) {
                            MsgCell enteredMsgCell = msgCellMap.get(enteredMsgCellNo);
                            enteredMsgCell.setBackground(Color.white);
                            enteredMsgCell.remove(xLabelMap.get(enteredMsgCellNo));
                            enteredMsgCell.repaint();
                        }
                        if (msgCellMap.containsKey(clickOnMsgCellNo)) {
                            MsgCell cMsgCell = msgCellMap.get(clickOnMsgCellNo);
                            cMsgCell.remove(xLabelMap.get(clickOnMsgCellNo));
                            cMsgCell.repaint();
                        }
                    }
                    if (null == clickOnMsgCellNo || !clickOnMsgCellNo.equals(notification.getNotificationNO())) {
//                        msgCellMap.get(clickOnMsgCellNo).remove(clickOnMsgCellXLabel);
                        msgCell.setBackground(null);
                        enteredMsgCellNo = notification.getNotificationNO();
                    }
                    if (notification.getTotal() == 0) {
                        msgCell.add(xLabel);
                        msgCell.repaint();
                    }
                }


                @Override
                public void mouseClicked(MouseEvent e) {
                    if (null != clickOnMsgCellNo) {
                        if (xLabelMap.containsKey(notification.getNotificationNO())&&msgCellMap.containsKey(clickOnMsgCellNo)) {
                            MsgCell cMsgCell = msgCellMap.get(clickOnMsgCellNo);
                            cMsgCell.remove(xLabelMap.get(notification.getNotificationNO()));
                            cMsgCell.setBackground(WHITE245);
                            cMsgCell.repaint();
                        }
                    }
                    msgCell.setBackground(Color.lightGray);
                    switch (notification.getOpenType()) {
                        case 0:
                            getPrivateChat(notification.getNickname(), notification.getNotificationNO());
                            chatType = PRIVATE_CHAT_TYPE;
                            break;
                        case 1:
                            CHANNEL.writeAndFlush(encode(new GroupBasicRequest(chatUserByLogIn.getChatUserNo(), (byte)3)));
                            getGroupChat(notification.getNickname(), notification.getNotificationNO());
                            chatType = GROUP_CHAT_TYPE;
                            //刷新消息面板
                            CHAT_MAIN_FRAME.refreshMsgScrollPanel();
                            break;
                        default:
                    }

                }
            });
            msgPanel.add(msgCell, GridBagUtil.wrap());
            msgCellMap.put(notification.getNotificationNO(), msgCell);
            xLabelMap.put(notification.getNotificationNO(), xLabel);
        });
        JLabel fillUpLabel = new JLabel();
        fillUpLabel.setOpaque(true);
        fillUpLabel.setBackground(WHITE245);
        fillUpLabel.setPreferredSize(new Dimension(322, mainFrame.getHeight() - 130 - 75 * notificationArrayList.size()));
        msgPanel.add(fillUpLabel);
//        msgScrollPanel.repaint();
        //滑动面板
        msgScrollPanel.setBorder(BORDER_LIGHT_GRAY);
        msgScrollPanel.setViewportView(msgPanel);
    }

    /**
     * @return void
     * @description 聊天区域
     * @author Murray Law
     * @date 2020/12/1 18:19
     */
    public void chat() {
        //聊天面板
        chatPanel.removeAll();
        chatPanel.setLayout(null);
        chatPanel.setBounds(404, 49, mainFrame.getWidth() - 404, mainFrame.getHeight() - 48);
        chatPanel.setBackground(WHITE245);
        chatPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        //默认消息面板图标
        JLabel doomLabel = new JLabel();
        ImageIcon doomIcon = PictureManipulationUtil.getImageIcon("v1icon/chatPlaceholder.png", 150, 150);
        doomLabel.setIcon(doomIcon);
        doomLabel.setBounds((mainFrame.getWidth() - 555) / 2, (mainFrame.getHeight() - 200) / 2, 150, 150);
        chatPanel.add(doomLabel);
        //聊天区域
        mainPanel.add(chatPanel);
    }

    public void getPrivateChat(String userName, String userNo) {
        CHANNEL.writeAndFlush(encode(new ChatUserInfoRequest(chatUserByLogIn.getChatUserNo(), userNo)));
        textPane.setText(null);
        clickOnMsgCellNo = userNo;
        //清空相关视图
        chatPanel.removeAll();
        //私聊的头部
        chatHead = new JPanel(null);
        chatHead.setBackground(WHITE245);
        chatHead.setBounds(0, 0, mainFrame.getWidth() - 404, 82);
        chatHead.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        //设置好友昵称
        objNameLabel.setFont(MICROSOFT_YA_HEI_20);
        objNameLabel.setBounds(70, 15, 500, 50);
        chatHead.add(objNameLabel);
        //创建群聊图标
        JLabel createGroupLabel = new JLabel();
        ImageIcon createGroupIcon = PictureManipulationUtil.getImageIcon("v1icon/privateChatCreateGroup.png", 22, 25);
        createGroupLabel.setIcon(createGroupIcon);
        createGroupLabel.setToolTipText(" 添加成员 ");
        createGroupLabel.setBounds(mainFrame.getWidth() - 500, 25, 30, 30);
        createGroupLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CreateGroupFrame(clickOnMsgCellNo);
            }
        });
        chatHead.add(createGroupLabel);
        //聊天记录图标
        JLabel recordLabel = new JLabel();
        ImageIcon recordIcon = PictureManipulationUtil.getImageIcon("v1icon/record.png", 25, 25);
        recordLabel.setIcon(recordIcon);
        recordLabel.setBounds(mainFrame.getWidth() - 450, 25, 30, 30);
        chatHead.add(recordLabel);
        //设置头像资料
        profilePicture = PictureManipulationUtil.getUserAvatar(userNo, 50, 50);
        profilePictureLabel.setBounds(10, 15, 50, 50);
        profilePictureLabel.setIcon(profilePicture);
        chatHead.add(profilePictureLabel);
        objNameLabel.setText(userName);
        //聊天主面板
        privateMSGPanel = new PrivateMSGPanel();
        privateMSGPanel.initPrivateChatPanel(userNo);
        chatPanel.add(chatHead);
        chatPanel.add(splitPane);
        splitPane.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        splitPane.setOrientation(VERTICAL_SPLIT);
        splitPane.setBounds(0, 81, mainFrame.getWidth() - 404, mainFrame.getHeight() - 129);
        splitPane.setTopComponent(privateMSGPanel.getPrivateMSGPanel());
        splitPane.setBottomComponent(sendPanel);
        splitPane.setDividerLocation(frameHei - 340);
        splitPane.setDividerSize(3);
        chatPanel.repaint();
        //刷新消息面板
        CHAT_MAIN_FRAME.refreshMsgScrollPanel();
        textPane.requestFocus();
        textPane.grabFocus();
    }

    public void getGroupChat(String groupName, String groupNo) {
        msgPanel.updateUI();
        chatPanel.removeAll();
        textPane.setText(null);
        clickOnMsgCellNo = groupNo;
        //群聊的头部
        chatHead = new JPanel(null);
        chatHead.setBackground(WHITE245);
        chatHead.setBounds(0, 0, mainFrame.getWidth() - 404, 82);
        chatHead.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        //设置群名称
        objNameLabel.setFont(MICROSOFT_YA_HEI_20);
        objNameLabel.setBounds(70, 15, 500, 50);
        chatHead.add(objNameLabel);
        //创建群聊图标
        JLabel createGroupLabel = new JLabel();
        ImageIcon createGroupIcon = PictureManipulationUtil.getImageIcon("v1icon/privateChatCreateGroup.png", 22, 25);
        createGroupLabel.setIcon(createGroupIcon);
        createGroupLabel.setToolTipText(" 添加成员 ");
        createGroupLabel.setBounds(mainFrame.getWidth() - 550, 25, 30, 30);
        createGroupLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CreateGroupFrame(clickOnMsgCellNo);
            }
        });
        chatHead.add(createGroupLabel);
        //聊天记录图标
        JLabel recordLabel = new JLabel();
        ImageIcon recordIcon = PictureManipulationUtil.getImageIcon("v1icon/record.png", 25, 25);
        recordLabel.setIcon(recordIcon);
        recordLabel.setBounds(mainFrame.getWidth() - 500, 25, 30, 30);
        chatHead.add(recordLabel);
        //群设置图标
        JLabel groupSettingLabel = new JLabel();
        ImageIcon groupSettingIcon = PictureManipulationUtil.getImageIcon("v1icon/groupSetting.png", 25, 3);
        groupSettingLabel.setIcon(groupSettingIcon);
        groupSettingLabel.setBounds(mainFrame.getWidth() - 450, 25, 30, 30);
        //单例群组设置
        groupSettingLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (groupInfoFrame!=null){
                    groupInfoFrame.dispose();
                }
                groupInfoFrame= new GroupInfoFrame(groupNo,groupSettingLabel.getLocationOnScreen());
            }
        });
        chatHead.add(groupSettingLabel);

        //群成员
        Map<String, Byte> userRoleMap = GROUP_BASIC_MAP.get(groupNo).getUserRoleMap();
        JPanel groupAvatar = PictureManipulationUtil.getGroupAvatar(userRoleMap, 50, 50);
        groupAvatar.setBounds(10, 15, 50, 50);
        chatHead.add(groupAvatar);
        objNameLabel.setText(groupName + "  (" + userRoleMap.size() + ")");
        //聊天主面板刷新
        groupMSGPanel = new GroupMSGPanel();
        groupMSGPanel.initGroupChatPanel(groupNo);
        chatPanel.add(chatHead);
        chatPanel.add(splitPane);
        splitPane.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        splitPane.setOrientation(VERTICAL_SPLIT);
        splitPane.setBounds(0, 81, mainFrame.getWidth() - 404, mainFrame.getHeight() - 129);
        splitPane.setTopComponent(groupMSGPanel.getGroupMSGPanel());
        splitPane.setBottomComponent(sendPanel);
        splitPane.setDividerLocation(frameHei - 340);
        splitPane.setDividerSize(3);
        chatHead.updateUI();
        chatPanel.repaint();
        textPane.requestFocus();
        textPane.grabFocus();
    }


    /**
     * @param
     * @return void
     * @description 通讯录面板
     * @author Murray Law
     * @date 2020/11/1 22:55
     */
    private void addressBook() {
        //通讯录栏
        addressBookBar.setText("      通讯录");
        addressBookBar.setOpaque(true);
        addressBookBar.setBackground(Color.white);
        addressBookBar.setFont(MICROSOFT_YA_HEI_20);
        addressBookBar.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        addressBookBar.setBounds(leftPanel.getWidth() - 1, headPanel.getHeight() - 1, mainFrame.getWidth() - leftPanel.getWidth() + 1, 60);
        mainPanel.add(addressBookBar);
        //通讯录预览列表
        addressBookBasicScrollPanel.setBounds(leftPanel.getWidth() - 1, headPanel.getHeight() + addressBookBar.getHeight() - 2, 326, mainFrame.getHeight() - headPanel.getHeight() - addressBookBar.getHeight() + 3);
        addressBookBasicScrollPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        addressBookBasicScrollPanel.getVerticalScrollBar().setUI(windowsScrollBarUI);
        addressBookBasicScrollPanel.getVerticalScrollBar().setUnitIncrement(20);
        mainPanel.add(addressBookBasicScrollPanel);
        CHANNEL.writeAndFlush(encode(new AddressBookRequest(chatUserByLogIn.getCompany(), chatUserByLogIn.getChatUserNo())));
        //通讯录详情列表
        addressBookMemberScrollPanel.setBounds(leftPanel.getWidth() + 324, headPanel.getHeight() + addressBookBar.getHeight() - 2, mainFrame.getWidth() - leftPanel.getWidth() - addressBookBasicScrollPanel.getWidth() + 2, mainFrame.getHeight() - headPanel.getHeight() - addressBookBar.getHeight() + 3);
        addressBookMemberScrollPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        addressBookMemberScrollPanel.getVerticalScrollBar().setUI(windowsScrollBarUI);
        addressBookMemberScrollPanel.getVerticalScrollBar().setUnitIncrement(20);
        mainPanel.add(addressBookMemberScrollPanel);
    }

    private void getMaximizeButton(JPanel panel, int x, int y, int wid, int hei) {
        ImageIcon maximizeIcon = PictureManipulationUtil.getImageIcon("icon/maximize.png", wid, hei);
        //缩小按钮
        maximizeButton = new JButton(maximizeIcon);
        maximizeButton.setBounds(x, y, wid, hei);
        maximizeButton.setBorderPainted(false);//不打印边框
        maximizeButton.setFocusPainted(false);//除去焦点的框
        maximizeButton.setBorder(null);
        maximizeButton.setText(null);
        maximizeButton.setContentAreaFilled(false);
        mouseAdapter = getMaxActionListener();
        maximizeButton.addMouseListener(mouseAdapter);
        panel.add(maximizeButton);
    }

    private MouseAdapter getMaxActionListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.setExtendedState(MAXIMIZED_BOTH);//窗口最大化
                //头部面板变动
                headPanel.setBounds(0, 0, mainFrame.getWidth(), 50);
                zoomBtn.setBounds(mainFrame.getWidth() - 90, 15, 15, 15);
                maximizeButton.setBounds(mainFrame.getWidth() - 60, 15, 15, 15);
                exitBtn.setBounds(mainFrame.getWidth() - 30, 15, 15, 15);
                //左边面板变动
                leftPanel.setBounds(0, 0, 80, mainFrame.getHeight());
                //消息列表面板变动
                msgScrollPanel.setBounds(79, 130, 325, mainFrame.getHeight() - 128);
                maximizeButton.removeMouseListener(mouseAdapter);
                mouseAdapter = getOriginalActionListener();
                maximizeButton.addMouseListener(mouseAdapter);
            }
        };
    }

    /**
     * @param
     * @return java.awt.event.MouseAdapter
     * @description 变动
     * @author Murray Law
     * @date 2020/12/16 23:11
     */
    private MouseAdapter getOriginalActionListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.setExtendedState(NORMAL);//窗口复原
                headPanel.setBounds(0, 0, mainFrame.getWidth(), 50);
                zoomBtn.setBounds(mainFrame.getWidth() - 90, 15, 15, 15);
                exitBtn.setBounds(mainFrame.getWidth() - 30, 15, 15, 15);
                //左边面板变动
                leftPanel.setBounds(0, 0, 80, mainFrame.getHeight());
                //消息列表面板变动
                msgScrollPanel.setBounds(79, 130, 325, mainFrame.getHeight() - 128);
                maximizeButton.setBounds(mainFrame.getWidth() - 60, 15, 15, 15);
                maximizeButton.removeMouseListener(mouseAdapter);
                mouseAdapter = getMaxActionListener();
                maximizeButton.addMouseListener(mouseAdapter);
            }
        };
    }


    /**
     * @param
     * @return java.util.List<com.murray.view.dto.Notification>
     * @description 获取要显示消息通知的模型
     * @author Murray Law
     * @date 2020/12/2 19:27
     */
    private void getNotificationArray() {
        //清空相关数据
        privateChatNoticeMap.clear();
        groupChatNoticeMap.clear();
        notificationArrayList.removeAll(notificationArrayList);
        for (Map.Entry<String, PrivateChatMessage> entry : ClientCache.PRIVATE_CHAT_MESSAGE_MAP.entrySet()) {
            //如果收到的消息不是自己的
            if (!entry.getValue().getSenderUserNo().equals(ClientCache.chatUserByLogIn.getChatUserNo())) {
                //未读的消息
                if (!entry.getValue().getHaveRead()) {
                    if (privateChatNoticeMap.containsKey(entry.getValue().getSenderUserNo())) {
                        privateChatNoticeMap.put(entry.getValue().getSenderUserNo(), privateChatNoticeMap.get(entry.getValue().getSenderUserNo()) + 1);
                    } else {
                        privateChatNoticeMap.put(entry.getValue().getSenderUserNo(), 1);
                    }
                } else {//已读的消息
                    if (!privateChatNoticeMap.containsKey(entry.getValue().getSenderUserNo())) {
                        privateChatNoticeMap.put(entry.getValue().getSenderUserNo(), 0);
                    }
                }

            } else {//是自己的
                privateChatNoticeMap.put(entry.getValue().getReceiverUserNo(), 0);
            }
        }
        //获得Key重复的聊天账号和重复次数,转为list
        Collection<PrivateChatMessage> valueCollection = ClientCache.PRIVATE_CHAT_MESSAGE_MAP.values();
        //排序
        java.util.List<PrivateChatMessage> privateChatMessageList = new ArrayList<>(valueCollection);
        privateChatMessageList.sort(Comparator.comparing(PrivateChatMessage::getIssueTime));
        //直接拿时间最早的消息
        privateChatNoticeMap.forEach((key, value) -> {
            PrivateChatMessage privateChatMessage = null;
            for (PrivateChatMessage msg : privateChatMessageList) {
                if (msg.getSenderUserNo().equals(key) || msg.getReceiverUserNo().equals(key)) {
                    privateChatMessage = msg;
                }
            }
            Notification notification = new Notification(key, false, privateChatMessage.getMsg(), privateChatMessage.getIssueTime(), value, (byte) 0);
            //引用Notification数据
            notificationArrayList.add(notification);
        });


        /**群聊消息提示*/
        HashMap<String, String> haveAtMeMap = new HashMap<>();
        for (Map.Entry<String, GroupMsgResponse> entry : GROUP_MSG_MAP.entrySet()) {
            GroupMsgResponse groupMsg = entry.getValue();
            //计算出聊天主面板的消息数据,屏蔽自己的消息
            if (!groupMsg.getSenderUserNo().equals(ClientCache.chatUserByLogIn.getChatUserNo())) {
                //屏蔽未读的消息
                if (!groupMsg.isHaveRead()) {
                    if (groupChatNoticeMap.containsKey(groupMsg.getGroupNo())) {
                        groupChatNoticeMap.put(groupMsg.getGroupNo(), groupChatNoticeMap.get(groupMsg.getGroupNo()) + 1);
                    } else {
                        groupChatNoticeMap.put(groupMsg.getGroupNo(), 1);
                    }
                    //是否有人@我
                    if (groupMsg.getAtMe()) {
                        haveAtMeMap.put(groupMsg.getGroupNo(), groupMsg.getGroupNo());
                    }
                } else if (!groupChatNoticeMap.containsKey(groupMsg.getGroupNo())) {
                    groupChatNoticeMap.put(groupMsg.getGroupNo(), 0);
                }
            } else {
                groupChatNoticeMap.put(groupMsg.getGroupNo(), 0);
            }
        }
        //获得Key重复的聊天账号和重复次数,转为list
        Collection<GroupMsgResponse> gMsgCollection = GROUP_MSG_MAP.values();
        //排序
        List<GroupMsgResponse> gMsgList = new ArrayList<>(gMsgCollection);
        gMsgList.sort(Comparator.comparing(GroupMsgResponse::getIssueTime));
        //转成Collection,准备判断@我的人
        Collection<String> haveAtMeCollection = haveAtMeMap.values();

        //直接拿时间最早的消息
        groupChatNoticeMap.forEach((name, amount) -> {
            GroupMsgResponse groupMsg = null;
            Notification notification = null;
            for (GroupMsgResponse msg : gMsgList) {
                if (msg.getGroupNo().equals(name)) {
                    groupMsg = msg;
                    boolean haveAt = haveAtMeCollection.contains(groupMsg.getGroupNo());
                    notification = new Notification(name, haveAt, groupMsg.getSenderUserNo() + ":" + groupMsg.getMessage(), groupMsg.getIssueTime(), amount, (byte) 1);
                    //引用Notification数据
                }
            }
            notificationArrayList.add(notification);
        });


        //排序
        notificationArrayList.sort((o1, o2) -> Long.compare(o2.getIssueTime().getTime(), o1.getIssueTime().getTime()));
    }

    /**
     * @return void
     * @description 发送消息
     * @author Murray Law
     * @date 2020/12/4 23:11
     */
    private void sendMsg() {
        //发送文件消息
        for (String filePath : richTextPanel.getFilePaths()) {
            File file = new File(filePath);
            String fileSize;
            double fileKbSize =  file.length() / 1024.00;
            if (fileKbSize<1024.00){
             fileSize= "(" + String.format("%.2f",fileKbSize) + "KB)";
            }else {
                fileSize="(" + String.format("%.2f",fileKbSize/1024.00) + "MB)";
            }
            FilePacket filePacket = new FilePacket();
            filePacket.setFile(file);
            //创建文件发送请求对象
            if (chatType.equals(PRIVATE_CHAT_TYPE)) {
                PrivateChatMessage privateChatMessage = new PrivateChatMessage(chatUserByLogIn.getChatUserNo(), file.getName() + fileSize, clickOnMsgCellNo, new Date(), false, PRIVATE_FILE_TYPE);
                CHANNEL.writeAndFlush(encode(privateChatMessage));
                filePacket.setFileNo(privateChatMessage.getMsgNo());
                filePacket.setFileName(file.getName());
                //将自己的消息保存本地内存
                PRIVATE_CHAT_MESSAGE_MAP.put(privateChatMessage.getMsgNo(), privateChatMessage);
                privateMSGPanel.addPrivateChatCell(privateChatMessage);
                CHANNEL.writeAndFlush(encode(filePacket));
            }
            if (chatType.equals(GROUP_CHAT_TYPE)) {
                GroupMsgRequest groupMsgRequest = new GroupMsgRequest(clickOnMsgCellNo, chatUserByLogIn.getChatUserNo(), atUserNoList, file.getName() + fileSize, new Date(), GROUP_FILE_TYPE);
                CHANNEL.writeAndFlush(encode(groupMsgRequest));
                filePacket.setFileNo(groupMsgRequest.getGroupMsgNo());
                filePacket.setFileName(file.getName());
                //将自己的消息保存本地内存
                CHANNEL.writeAndFlush(encode(filePacket));
            }
        }
        String message = richTextPanel.getPaneText();
        int count0 = StringUtil.appearNumber(message, " ");
        if (!message.isEmpty() && count0 != message.length()) {
            //创建消息对象
            if (chatType.equals(PRIVATE_CHAT_TYPE)) {
                PrivateChatMessage privateChatMsg = new PrivateChatMessage(chatUserByLogIn.getChatUserNo(), message, clickOnMsgCellNo, new Date(), false, PRIVATE_CHAT_TYPE);
                //将自己的消息保存本地内存
                PRIVATE_CHAT_MESSAGE_MAP.put(privateChatMsg.getMsgNo(), privateChatMsg);
                CHANNEL.writeAndFlush(encode(privateChatMsg));
                getPrivateChat(CHAT_USER_INFO_RESPONSE_MAP.get(clickOnMsgCellNo).getName(), clickOnMsgCellNo);
                CHAT_MAIN_FRAME.refreshMsgScrollPanel();
            }
            if (chatType.equals(GROUP_CHAT_TYPE)) {
                List<String> atUserNoList = richTextPanel.getMyLabelAttrList();
                StringBuilder atStr= new StringBuilder();
                for (String s:richTextPanel.getMyLabelTextList()){
                  atStr.append(s);
                }
                GroupMsgRequest groupMsgRequest = new GroupMsgRequest(clickOnMsgCellNo, chatUserByLogIn.getChatUserNo(), atUserNoList, atStr+message, new Date(), GROUP_CHAT_TYPE);
                CHANNEL.writeAndFlush(encode(groupMsgRequest));
                if (!atUserNoList.isEmpty()){
                    GROUP_AT_HAVE_READ_MAP.put(groupMsgRequest.getGroupMsgNo(),null);
                }
                //getGroupChat(GROUP_BASIC_MAP.get(clickOnMsgCellNo).getGroupName(), clickOnMsgCellNo);
            }
        }
        textPane.setText("");
        sendButton.setEnabled(false);
    }

    /**
     * @description 获取聊天输入面板
     * @author Murray Law
     * @date 2020/12/19 2:35
      * @param
     * @return javax.swing.JTextPane
     */
    public JTextPane getTextPane(){
        return textPane;
    }
}
