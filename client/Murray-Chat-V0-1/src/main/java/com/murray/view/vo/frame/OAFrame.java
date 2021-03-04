package com.murray.view.vo.frame;

import com.murray.cache.ClientCache;
import com.murray.dto.request.MeetingTableRequest;
import com.murray.handler.ClientPacketCodeCompile;
import com.murray.utils.DateUtil;
import com.murray.utils.FontUtil;
import com.murray.view.vo.cell.MeetingTableCellRender;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.murray.cache.ClientCache.*;
import static com.murray.utils.JFameUtil.eventHandle;

/**
 * @author Murray Law
 * @describe OA窗口
 * @createTime 2020/11/11
 */
public class OAFrame extends JFrame {
    private JPanel mainPanel, headPanel;
    private JTabbedPane mainTabbedPane;
    private CreateMeetRemindFrame createMeetRemindFrame = null;
    private Color fontColor = new Color(30, 80, 150),
            topColor = new Color(190, 220, 240),
            mainColor = new Color(239, 245, 251);
    private static final List<File> fileList = new ArrayList<>();

    private int frameWidth = 1000, frameHeight = 870, fontX = 20, moduleX = 70, moduleWidth = 430;
    private File[] selectedFiles;
    private String ownUserNo;
    private Date sendTime = null;

    public OAFrame(String ownUserNo) {
        this.ownUserNo = ownUserNo;
        //标题
        setResizable(false);//不可调整
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setSize(frameWidth, frameHeight);
        setLocation(480, 100);
        //设置主面板
        mainPanel = new JPanel();
        mainPanel.setBorder(null);
        mainPanel.setLayout(null);
        mainPanel.setBackground(topColor);


        //头部panel
        setHeadPanel();

        //设置文件显示框
        setOAPanel();


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
        exitButton.addActionListener(e -> {
            meetingTable = null;
            dispose();
        });
        headPanel.add(exitButton);
        //设置title,文字,默认居中
        JLabel titleLabel = new JLabel("OA办公", JLabel.CENTER);
        titleLabel.setForeground(fontColor);
        titleLabel.setBounds(0, 10, frameWidth, 30);
        titleLabel.setFont(FontUtil.MICROSOFT_YA_HEI_20);
        headPanel.add(titleLabel);
        mainPanel.add(headPanel);
    }

    /**
     * @param
     * @return void
     * @description 设置办公主面板
     * @author Murray Law
     * @date 2020/11/21 16:18
     */
    private void setOAPanel() {

        // 创建菜单主选项卡面板
        mainTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        mainTabbedPane.setBounds(0, 50, frameWidth, frameHeight - 70);
        // 创建标签选项卡（选项卡只包含 标题）
        mainTabbedPane.addTab("    考勤记录   ", new JPanel());
        mainTabbedPane.addTab("    会议提醒   ", getConferencePanel());
        mainTabbedPane.addTab("    日程管理   ", new JPanel());
        mainTabbedPane.addTab("    部门管理   ", new JPanel());

        mainTabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        mainTabbedPane.setBorder(null);
        mainTabbedPane.setBackground(Color.white);
        mainPanel.add(mainTabbedPane);
    }

    /**
     * @param
     * @return javax.swing.JScrollPane
     * @description 会议提醒滑动面板
     * @author Murray Law
     * @date 2020/11/21 16:40
     */
    private JPanel getConferencePanel() {
        //创建背景面板
        JPanel BGPanel = new JPanel(null);
        BGPanel.setBackground(mainColor);
        //表格菜单操作控件
        JButton createBtn = new JButton("+ 创建");
        createBtn.setBackground(topColor);
        createBtn.setBounds(10, 5, 80, 30);
        createBtn.addActionListener(e -> {
            if (null != createMeetRemindFrame) {
                createMeetRemindFrame.dispose();
                createMeetRemindFrame = null;
            }
            createMeetRemindFrame = new CreateMeetRemindFrame(ownUserNo);
        });
        //标签
        JLabel title = new JLabel("本周会议列表", JLabel.CENTER);
        title.setForeground(fontColor);
        title.setBackground(topColor);
        title.setBounds(0, 5, frameWidth - 120, 30);
        //表格菜单操作控件
        JButton refreshBtn = new JButton("刷新");
        refreshBtn.setBackground(topColor);
        refreshBtn.setBounds(frameWidth - 220, 5, 80, 30);
        refreshBtn.addActionListener(e -> {
            Date now = new Date();
            CHANNEL.writeAndFlush(ClientPacketCodeCompile.encode(new MeetingTableRequest( DateUtil.getThisWeekSunday(now),DateUtil.getNextWeekSunday(now),chatUserByLogIn.getChatUserNo())));
        });
        // 表头（列名）
        Object[] columnNames = {"时间段", "周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        //定义二维数组作为表格数据
        Object[][] tableData = new Object[48][8];
        int H = -1, h = 0;
        String M, m;
        for (int i = 0; i < 48; i++) {
            for (int j = 0; j < 6; j++) {
                tableData[i][j] = null;
            }
            if (i % 2 == 0) {
                H += 1;
                M = "00";
                m = "29";
            } else {
                M = "30";
                h += 1;
                m = "59";
            }
            tableData[i][0] = H + ":" + M + "--" + h + ":" + m;
        }
        //会议表格
        meetingTable = new JTable(tableData, columnNames);
        for (int i = 1; i < meetingTable.getColumnCount(); i++) {
            meetingTable.getColumnModel().getColumn(i).setCellRenderer(new MeetingTableCellRender(new Color(200, 240, 200)));
        }
        // 设置表头
        meetingTable.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        meetingTable.getTableHeader().setForeground(Color.RED);
        meetingTable.setEnabled(false);
        //滑动面板
        JScrollPane conferencePanel = new JScrollPane();
        conferencePanel.setBounds(0, 40, 900, frameHeight - 100);
        conferencePanel.setViewportView(meetingTable);
        conferencePanel.setBorder(null);
        conferencePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //加入到内容
        BGPanel.add(createBtn);
        BGPanel.add(title);
        BGPanel.add(refreshBtn);
        BGPanel.add(conferencePanel);
        return BGPanel;
    }

    public void renderTableData() {
             mainTabbedPane.setComponentAt(1, getConferencePanel());
        //渲染表格数据
        Calendar calendar = Calendar.getInstance();
        ClientCache.meetingTableResponse.getMeetingTableCellList().forEach(meetingTableCell -> {
            calendar.setTime(meetingTableCell.getStartTime());
            int row = 0;
            if (calendar.get(Calendar.MINUTE) > 29) {
                row = calendar.get(Calendar.HOUR_OF_DAY) * 2 + 1;
            } else {
                row = calendar.get(Calendar.HOUR_OF_DAY) * 2;
            }
            //日期时间
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String format = sdf.format(meetingTableCell.getStartTime());
            Object tableValueAt = meetingTable.getValueAt(row, calendar.get(Calendar.DAY_OF_WEEK));
            if (null != tableValueAt) {
                meetingTable.setValueAt(tableValueAt.toString() + "\n" + format + "       " + meetingTableCell.getTitle() + "       " + meetingTableCell.getLocation(), row, calendar.get(Calendar.DAY_OF_WEEK));
            } else {
                meetingTable.setValueAt(format+ "       " + meetingTableCell.getTitle() + "       " + meetingTableCell.getLocation(), row, calendar.get(Calendar.DAY_OF_WEEK));
            }
        });
    }

}
