package com.murray.view.vo.frame;

import com.murray.dto.request.MeetingReminderRequest;
import com.murray.dto.response.ChatFriendResponse;
import com.murray.handler.ClientPacketCodeCompile;
import com.murray.utils.FontUtil;
import com.murray.utils.MMddHHmmChoose;
import com.murray.utils.ModuleUtil;
import com.murray.view.vo.cell.MyLabel;
import com.murray.view.vo.panel.FriendsListPanel;
import com.murray.view.vo.panel.RichTextPanel;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.murray.cache.ClientCache.CHANNEL;
import static com.murray.cache.ClientCache.chatUserByLogIn;
import static com.murray.utils.JFameUtil.eventHandle;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 * @author Murray Law
 * @describe 创建会议的面板
 * @createTime 2020/11/21
 */
public class CreateMeetRemindFrame extends JFrame {
    private JLabel partLabel, totalLabel;
    private JTextField titleTextField, locationField;
    private JTextArea contentArea;
    private JPanel mainPanel, headPanel;
    private JTextPane partPane;
    private List<String> attrList;
    private MMddHHmmChoose timeChoose;
    private RichTextPanel richTextPanel;
    private FriendsListPanel friendsListPanel;
    private Color fontColor = new Color(30, 80, 150),
            topColor = new Color(190, 220, 240),
            mainColor = new Color(239, 245, 251);

    private int frameWidth = 800, frameHeight = 650, fontX = 20, moduleX = 70, moduleWidth = 430;
    private String ownUserNo;

    public CreateMeetRemindFrame(String ownUserNo) {
        this.ownUserNo = ownUserNo;
        setLocation(650, 250);
        setResizable(false);//不可调整
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setSize(frameWidth, frameHeight);
        //设置主面板
        mainPanel = new JPanel();
        mainPanel.setBorder(null);
        mainPanel.setLayout(null);
        mainPanel.setBackground(mainColor);


        //头部panel
        setHeadPanel();

        //设置文件显示框
        setLeftPanel();

        setRightPanel();
        //脚部Panel
        //setFootPanel();

        setContentPane(mainPanel);
        setVisible(true);//一定要放在最后面
        eventHandle(headPanel, this);//设置可拖动
    }


    /**
     * @param
     * @return void
     * @description 设置头部
     * @author Murray Law
     * @date 2020/11/4 10:43
     */
    private void setHeadPanel() {
        headPanel = new JPanel();
        headPanel.setBounds(0, 0, frameWidth, 50);
        headPanel.setBorder(null);
        headPanel.setLayout(null);
        headPanel.setBackground(topColor);
        //缩小按钮
        JButton zoomOutButton = new JButton("一");
        zoomOutButton.setForeground(Color.black);
        zoomOutButton.setBounds(frameWidth - 50, 0, 25, 25);
        zoomOutButton.setBorder(null);
        zoomOutButton.setContentAreaFilled(false);
        zoomOutButton.addActionListener(e -> {
            this.setExtendedState(ICONIFIED);//窗口最小化
        });
        headPanel.add(zoomOutButton);
        //退出按钮
        JButton exitButton = new JButton("X");
        exitButton.setForeground(Color.black);
        exitButton.setBounds(frameWidth - 25, 0, 25, 25);
        exitButton.setBorder(null);
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener(e -> dispose());
        headPanel.add(exitButton);

        //设置title,文字,默认居中
        JLabel titleLabel = new JLabel("创建会议提醒", JLabel.LEFT);
        titleLabel.setForeground(fontColor);
        titleLabel.setBounds(20, 5, frameWidth, 40);
        titleLabel.setFont(FontUtil.MICROSOFT_YA_HEI_20);
        headPanel.add(titleLabel);
        mainPanel.add(headPanel);
    }

    /**
     * @param
     * @return void
     * @description 设置左部panel
     * @author Murray Law
     * @date 2020/11/9 1:41
     */
    private void setLeftPanel() {
        int width = frameWidth / 2 - 20, height = frameHeight - 100;
        JLabel chooseLabel = new JLabel("选择与会者:");
        chooseLabel.setForeground(fontColor);
        chooseLabel.setBounds(20, 50, 150, 30);
        mainPanel.add(chooseLabel);
        //获取封装好的好友列表
        friendsListPanel = new FriendsListPanel(0, 80, width, height, mainColor, topColor, ModuleUtil.searchArea(width, 40));
        friendsListPanel.refreshFriendList();
        friendsListPanel.addListSelectionListener(e -> addMeetingMembers());
        mainPanel.add(friendsListPanel);
    }


    private void setRightPanel() {
        int rightX = frameWidth / 2;
        //主题
        JLabel title = new JLabel("主题:");
        title.setForeground(fontColor);
        title.setBounds(rightX, 60, 40, 30);
        mainPanel.add(title);

        titleTextField = new JTextField();
        titleTextField.setBounds(rightX + 40, 60, frameWidth / 2 - 50, 30);
        mainPanel.add(titleTextField);
        //日期时间
        JLabel dateLabel = new JLabel("时间:");
        dateLabel.setForeground(fontColor);
        dateLabel.setBounds(rightX, 100, 40, 30);
        mainPanel.add(dateLabel);
        //日期选择面板

        timeChoose = new MMddHHmmChoose(Calendar.getInstance());
        timeChoose.setBackground(mainColor);
        timeChoose.setBounds(rightX + 40, 100, frameWidth / 2 - 50, 30);
        mainPanel.add(timeChoose);
        //描述
        JLabel descLabel = new JLabel("系统将在开会前十分钟提醒与会者");
        descLabel.setBounds(rightX, 140, 200, 30);
        descLabel.setForeground(Color.red);
        mainPanel.add(descLabel);
        //会议内容
        JLabel contentLabel = new JLabel("会议内容:");
        contentLabel.setForeground(fontColor);
        contentLabel.setBounds(rightX, 180, 60, 30);
        mainPanel.add(contentLabel);
        contentArea = new JTextArea();
        contentArea.setLineWrap(true);
        contentArea.setBounds(rightX + 70, 190, 315, 90);
        contentArea.setBorder(BorderFactory.createLineBorder(Color.gray));
        mainPanel.add(contentArea);
        //会议地点
        JLabel locationLabel = new JLabel("会议地点:");
        locationLabel.setForeground(fontColor);
        locationLabel.setBounds(rightX, 290, 60, 30);
        mainPanel.add(locationLabel);
        locationField = new JTextField();
        locationField.setBounds(rightX + 70, 300, 315, 30);
        mainPanel.add(locationField);
        //与会者
        partLabel = new JLabel("与会者:");
        partLabel.setForeground(fontColor);
        partLabel.setBounds(rightX, 330, 60, 30);
        mainPanel.add(partLabel);

        richTextPanel = new RichTextPanel();
        richTextPanel.setBounds(rightX + 70, 340, 315, 260);
        richTextPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        partPane = richTextPanel.getTextPane();
        partPane.setToolTipText("在左侧选择与会人");
        //总人数标签
        attrList = richTextPanel.getMyLabelAttrList();
        totalLabel = new JLabel("已选择"+attrList.size()+"人");
        totalLabel.setForeground(Color.red);
        totalLabel.setBounds(rightX, 420, 60, 30);
        mainPanel.add(totalLabel);
        //输入框事件,只能删除
        partPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (keyChar != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }

            }
            @Override
            public void keyReleased(KeyEvent e) {
                attrList =richTextPanel.getMyLabelAttrList();
                totalLabel.setText("已选择"+ attrList.size()+"人");
            }
        });
        mainPanel.add(richTextPanel);
        //确定按钮
        JButton sureBtn = new JButton("确定");
        sureBtn.setBounds(frameWidth - 65, frameHeight - 40, 50, 30);
        sureBtn.setBackground(topColor);
        sureBtn.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        sureBtn.addActionListener(e -> submit());
        mainPanel.add(sureBtn);

    }

    private void addMeetingMembers() {
        JList<ChatFriendResponse> friendJList = friendsListPanel.friendJList;
        if (friendJList.getValueIsAdjusting()) {
            int index = friendJList.getSelectedIndex();
            ChatFriendResponse chatFriend = friendJList.getModel().getElementAt(index);
            attrList=richTextPanel.getMyLabelAttrList();
            if (!attrList.contains(chatFriend.getFriendUserNo())) {
                //已选择好友的标签
                String labelText;
                if (null == chatFriend.getNoteName()) {
                    labelText = chatFriend.getName();
                } else {
                    labelText = chatFriend.getNoteName();
                }
                MyLabel myLabel = new MyLabel(labelText, chatFriend.getFriendUserNo());
                myLabel.setForeground(Color.red);
                myLabel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
                partPane.insertComponent(myLabel);
                //换行操作
                SimpleAttributeSet set = new SimpleAttributeSet();
                Document doc = partPane.getStyledDocument();
                attrList=richTextPanel.getMyLabelAttrList();
                try {
                    doc.insertString(doc.getLength(), " ", set);
                    if (attrList.size() % 5 == 0) {
                        doc.insertString(doc.getLength(), "\n", set);
                    }
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }

                totalLabel.setText("已选择" + attrList.size() + "人");
            }

        }
        //JList默认不取消选中,此处两个方法重写以自动取消选中
        friendJList.setSelectionModel(new DefaultListSelectionModel() {
            private static final long serialVersionUID = -5155124021223524891L;

            public void setSelectionInterval(int index0, int index1) {
                if (index0 == index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                        return;
                    }
                }
                super.setSelectionInterval(index0, index1);
            }

            @Override
            public void addSelectionInterval(int index0, int index1) {
                if (index0 == index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                        return;
                    }
                    super.addSelectionInterval(index0, index1);
                }
            }

        });
    }

    /**
     * @description 提交信息
     * @author Murray Law
     * @date 2020/11/24 10:51
     */
    private void submit() {
        String prompt = "提示";
        Date dateFormat;
        String title = titleTextField.getText();
        String content = contentArea.getText();
        String location = locationField.getText();
        attrList = richTextPanel.getMyLabelAttrList();
        try {
            dateFormat = timeChoose.getDateFormat();
            if (timeChoose.getDay() > timeChoose.getStringDayOfMonth(timeChoose.getYear() + "-" + timeChoose.getMonth())) {
                ModuleUtil.showDialog("日期输入错误", "日期超过当月天数", WARNING_MESSAGE);
                return;
            }
        } catch (ParseException e) {
            ModuleUtil.showDialog(prompt, "日期格式有误!!", WARNING_MESSAGE);
            return;
        }
        if (timeChoose.getMonth() > 12) {
            ModuleUtil.showDialog(prompt, "月份输入错误", WARNING_MESSAGE);
            return;
        }
        if (timeChoose.getHour() > 24) {
            ModuleUtil.showDialog(prompt, "小时输入错误", WARNING_MESSAGE);
            return;
        }
        if (timeChoose.getMinute() > 59) {
            ModuleUtil.showDialog(prompt, "分钟输入错误", WARNING_MESSAGE);
            return;
        }
        if (title.isEmpty()) {
            ModuleUtil.showDialog(prompt, "主题不能为空", WARNING_MESSAGE);
            return;
        }
        if (content.isEmpty()) {
            ModuleUtil.showDialog(prompt, "会议内容不能为空", WARNING_MESSAGE);
            return;
        }
        if (location.isEmpty()) {
            ModuleUtil.showDialog(prompt, "会议地点不能为空", WARNING_MESSAGE);
            return;
        }
        if (attrList.size()==0) {
            ModuleUtil.showDialog(prompt, "与会人只有您自己", WARNING_MESSAGE);
            return;
        }
        if(dateFormat.getTime()<new Date().getTime()+60000){
            ModuleUtil.showDialog(prompt, "会议开始时间太早了", WARNING_MESSAGE);
            return;
        }

        MeetingReminderRequest meetingReminderRequest = new MeetingReminderRequest(title, chatUserByLogIn.getChatUserNo(), dateFormat, content, location, attrList);
        CHANNEL.writeAndFlush(ClientPacketCodeCompile.encode(meetingReminderRequest));
        this.dispose();
    }


}
