package com.murray.view.vo.frame;

import com.murray.cache.ClientCache;
import com.murray.dto.request.HaveReadRequest;
import com.murray.dto.response.MeetingReminderResponse;
import com.murray.handler.ClientPacketCodeCompile;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.murray.cache.ClientCache.MEETING_REMINDER_FRAME_MAP;
import static com.murray.utils.FontUtil.MICROSOFT_YA_HEI_20;
import static com.murray.utils.JFameUtil.eventHandle;
import static java.awt.Frame.ICONIFIED;

/**
 * @author Murray Law
 * @describe 会议提醒框
 * @createTime 2020/11/23
 */
public class MeetingReminderFrame {
    private JFrame mainFrame = new JFrame();
    private JPanel mainPanel, headPanel;
    private Color fontColor = new Color(30, 80, 150),
            topColor = new Color(190, 220, 240),
            mainColor = new Color(239, 245, 251);
    private final Font italicB = new Font("楷体", Font.BOLD, 18);
    private final Font italicI = new Font("楷体", Font.ITALIC, 15);

    private MeetingReminderResponse reminderResponse;
    private int frameWidth = 400, frameHeight = 650;

    public MeetingReminderFrame(MeetingReminderResponse reminderResponse) {
        this.reminderResponse = reminderResponse;
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLocation(650, 250);
        mainFrame.setResizable(false);//不可调整
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setUndecorated(true);
        mainFrame.setSize(frameWidth, frameHeight);

        //设置主面板
        mainPanel = new JPanel();
        mainPanel.setBorder(null);
        mainPanel.setLayout(null);
        mainPanel.setBackground(mainColor);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        //头部panel
        setHeadPanel();


        setRightPanel();
        //脚部Panel

        ClientCache.meetingAudioClip.loop();

        mainFrame.setContentPane(mainPanel);
        mainFrame.setAlwaysOnTop(true);
        mainFrame.setVisible(true);//一定要放在最后面
        eventHandle(headPanel, mainFrame);//设置可拖动
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
        headPanel.setBounds(1, 1, frameWidth - 2, 30);
        headPanel.setLayout(null);
        headPanel.setBackground(topColor);
        //缩小按钮
        JButton zoomOutButton = new JButton("一");
        zoomOutButton.setForeground(fontColor);
        zoomOutButton.setBounds(frameWidth - 50, 0, 25, 25);
        zoomOutButton.setBorder(null);
        zoomOutButton.setContentAreaFilled(false);
        zoomOutButton.addActionListener(e -> {
            mainFrame.setExtendedState(ICONIFIED);//窗口最小化
        });
//        headPanel.add(zoomOutButton);
        //退出按钮
        JButton exitButton = new JButton("X");
        exitButton.setForeground(fontColor);
        exitButton.setBounds(frameWidth - 25, 0, 25, 25);
        exitButton.setBorder(null);
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener(e -> mainFrame.dispose());
//        headPanel.add(exitButton);

        //设置title,文字,默认居中
        JLabel titleLabel = new JLabel("会议邀请", JLabel.LEFT);
        titleLabel.setForeground(fontColor);
        if (new Date().getTime() > reminderResponse.getStartTime().getTime()) {
            titleLabel.setText("未及时回复的会议邀请");
            titleLabel.setForeground(Color.red);
        }
        titleLabel.setBounds(20, 0, frameWidth, 30);
        titleLabel.setFont(MICROSOFT_YA_HEI_20);
        headPanel.add(titleLabel);
        mainPanel.add(headPanel);
    }

    private void setRightPanel() {
        int rightX = 20;
        //主题
        JLabel title = new JLabel(reminderResponse.getTitle(), JLabel.CENTER);
        title.setForeground(fontColor);
        title.setFont(italicB);
        title.setBounds(0, 40, frameWidth, 30);
        mainPanel.add(title);

        //ClientCache.chatUserByLogIn.getName()
        //会议内容
        JLabel contentLabel = new JLabel(ClientCache.chatUserByLogIn.getName() + ",您好：");
        contentLabel.setForeground(fontColor);
        contentLabel.setFont(italicI);
        contentLabel.setBounds(rightX, 80, 100, 30);
        mainPanel.add(contentLabel);
        JTextArea contentArea = new JTextArea("    会议内容：");
        contentArea.append(reminderResponse.getDesc());
        contentArea.setLineWrap(true);
        contentArea.setEditable(false);
        contentArea.setBorder(null);
        contentArea.setBackground(mainColor);
        contentArea.setForeground(fontColor);
        contentArea.setBounds(rightX, 110, frameWidth - 40, 300);
        contentArea.setFont(italicI);
        mainPanel.add(contentArea);

        //日期时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        String format = sdf.format(reminderResponse.getStartTime());
        JLabel dateLabel = new JLabel("会议开始时间:");
        dateLabel.setForeground(fontColor);
        dateLabel.setBounds(rightX + frameWidth / 4, 420, frameWidth / 2, 30);
        JLabel dateLabel2 = new JLabel(format);
        dateLabel2.setForeground(Color.red);
        dateLabel2.setFont(italicI);
        dateLabel2.setBounds(rightX + frameWidth / 2, 422, frameWidth / 2, 30);
        mainPanel.add(dateLabel);
        mainPanel.add(dateLabel2);
        //会议地点
        JLabel locationLabel = new JLabel("地点：");
        locationLabel.setForeground(fontColor);
        locationLabel.setBounds(rightX + frameWidth / 4, 450, frameWidth / 2, 30);
        mainPanel.add(locationLabel);
        JLabel locationLabel2 = new JLabel(reminderResponse.getLocation());
        locationLabel2.setForeground(Color.red);
        locationLabel2.setFont(italicI);
        locationLabel2.setBounds(rightX + frameWidth / 3, 452, frameWidth / 2, 30);
        mainPanel.add(locationLabel2);
        //会议地点
        JLabel founder = new JLabel(reminderResponse.getCreator() + "(职位)");//todo
        founder.setForeground(fontColor);
        founder.setBounds(rightX + frameWidth / 3 * 2, 480, frameWidth / 3, 30);
        mainPanel.add(founder);
        //确定按钮
        JButton sureBtn = new JButton("收到，我已知悉");
        sureBtn.setBounds(frameWidth / 4, frameHeight - 80, frameWidth / 2, 40);
        sureBtn.setBackground(topColor);
        sureBtn.setForeground(fontColor);
        sureBtn.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        sureBtn.addActionListener(e -> submit());
        mainPanel.add(sureBtn);

    }


    private void submit() {
        ClientCache.CHANNEL.writeAndFlush(ClientPacketCodeCompile.encode(new HaveReadRequest(ClientCache.chatUserByLogIn.getChatUserNo(), null, reminderResponse.getMeetingRemindNo(), (byte) 26)));
        MEETING_REMINDER_FRAME_MAP.remove(reminderResponse.getMeetingRemindNo());
        ClientCache.meetingAudioClip.stop();
        mainFrame.dispose();
    }


}
