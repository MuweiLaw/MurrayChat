package com.murray.view.vo.frame;

import com.murray.dto.request.OperateGroupRequest;
import com.murray.dto.response.ChatUserInfoResponse;
import com.murray.dto.response.GroupBasic;
import com.murray.handler.ClientPacketCodeCompile;
import com.murray.utils.*;
import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import static com.murray.cache.ClientCache.*;
import static com.murray.utils.PictureManipulationUtil.getImageIcon;

/**
 * @author Murray Law
 * @describe 用户基本信息
 * @createTime 2020/12/23
 */
public class GroupInfoFrame extends JFrame {
    private JScrollPane groupMemberScrollPane;
    public JTextField groupNameLabel;
    private Map<String, Byte> userRoleMap;
    public String groupNo, groupName;
    private JPanel memberDisplayPanel, fillPanel, contentPane = new JPanel(null) {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(ColorUtil.WHITE235);
            g.drawLine(30, 180, 370, 180);
        }
    };
    private GroupBasic groupBasic;

    public GroupInfoFrame(String groupNo, Point point) {
        super();
        setAlwaysOnTop(true);
        this.groupNo = groupNo;
        contentPane.setBorder(BorderUtil.BORDER_LIGHT_GRAY);
        //隐藏windows的窗格边框
        setUndecorated(true);
        setContentPane(contentPane);
        setLocation((int) point.getX() - 355, (int) point.getY() - 25);
        setSize(400, 750);
        contentPane.setBackground(Color.white);

        groupBasic = GROUP_BASIC_MAP.get(groupNo);
        //群设置标签
        JLabel headLabel = new JLabel("群设置");
        headLabel.setFont(FontUtil.MICROSOFT_YA_HEI_24);
        headLabel.setBounds(30, 30, 100, 40);

        //群设置取消
        ImageIcon xImageIcon = PictureManipulationUtil.getImageIcon("v1icon/groupSettingCancel.png", 20, 20);
        JLabel xLabel = new JLabel(xImageIcon);
        xLabel.setFont(FontUtil.MICROSOFT_YA_HEI_24);
        xLabel.setBounds(330, 40, 20, 20);
        xLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!groupName.equals(groupNameLabel.getText())) {
                    //如果修改了群组名称
                    CHANNEL.writeAndFlush(ClientPacketCodeCompile.encode(new OperateGroupRequest(chatUserByLogIn.getChatUserNo(), userRoleMap.get(chatUserByLogIn.getChatUserNo()), groupNo, groupNameLabel.getText(), null, null, StringUtil.UPDATE)));
                }
                CHAT_MAIN_FRAME.groupInfoFrame.dispose();
            }
        });
        //群名称说明
        JLabel groupDesLabel = new JLabel("群名称");
        groupDesLabel.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        groupDesLabel.setForeground(Color.gray);
        groupDesLabel.setBounds(30, 110, 100, 20);

        //群名称
        groupName = groupBasic.getGroupName();
        groupNameLabel = new JTextField(groupName);
        groupNameLabel.setBorder(null);
        groupNameLabel.setFont(FontUtil.MICROSOFT_YA_HEI_20);
        groupNameLabel.setBounds(30, 140, 350, 30);
        //搜索框
        ImageIcon searchImageIcon = PictureManipulationUtil.getImageIcon("v1icon/search.png", 20, 20);
        JLabel searchBorderLabel = new JLabel();
        searchBorderLabel.setBounds(30, 200, 340, 55);
        searchBorderLabel.setBorder(BorderUtil.BORDER_LIGHT_GRAY);
        //搜索图标
        JLabel searchLabel = new JLabel(searchImageIcon, JLabel.LEFT);
        searchLabel.setBounds(45, 218, 20, 20);
        //搜索输入
        JTextField searchTextField = new JTextField("搜索");
        searchTextField.setBounds(70, 210, 290, 35);
        searchTextField.setBorder(null);
        searchTextField.setFont(FontUtil.MICROSOFT_YA_HEI_20);
        searchTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                searchTextField.setText("");
                //todo 群成员按搜索条件显示
            }
        });
        //添加群成员
        ImageIcon addMemberIcon = PictureManipulationUtil.getImageIcon("v1icon/addMember.png", 20, 20);
        JLabel addMemberLabel = new JLabel(addMemberIcon, JLabel.LEFT);
        addMemberLabel.setText("    添加成员");
        addMemberLabel.setFont(FontUtil.MICROSOFT_YA_HEI_20);
        addMemberLabel.setBounds(30, 270, 340, 30);

        //群成员滑动面板
        groupMemberScrollPane = new JScrollPane();
        groupMemberScrollPane.setBounds(1, 320, getWidth() - 2, 410);
        groupMemberScrollPane.setBorder(null);
        //取消水平滑动条
        groupMemberScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //设置垂直滑动条UI
        groupMemberScrollPane.getVerticalScrollBar().setUI(new WindowsScrollBarUI());
        //群成员展示面板
        memberDisplayPanel = new JPanel(GridBagUtil.getGridBagLayout());
        userRoleMap = groupBasic.getUserRoleMap();
        userRoleMap.keySet().forEach(key -> {
            addMember(key);
        });
        fillPanel = new JPanel();
        fillPanel.setBackground(Color.white);
        fillPanel.setPreferredSize(new Dimension(398, 410 - userRoleMap.size() * 70));
        memberDisplayPanel.add(fillPanel, GridBagUtil.wrap());
        groupMemberScrollPane.setViewportView(memberDisplayPanel);

        contentPane.add(headLabel);
        contentPane.add(xLabel);
        contentPane.add(groupDesLabel);
        contentPane.add(groupNameLabel);
        contentPane.add(searchBorderLabel);
        contentPane.add(searchLabel);
        contentPane.add(searchTextField);
        contentPane.add(addMemberLabel);
        contentPane.add(groupMemberScrollPane);
        //添加面板
        setVisible(true);
    }

    private void addMember(String userNo) {
        ChatUserInfoResponse userInfo = CHAT_USER_INFO_RESPONSE_MAP.get(userNo);
        JPanel memberPanel = new JPanel(null);
        memberPanel.setPreferredSize(DimensionUtil.DIM_398_70);
        memberPanel.setBackground(Color.WHITE);
        //用户头像,信息,职级
        ImageIcon avatarFrameIcon = getImageIcon("v1icon/avatarFrame.png", 50, 50);
        JLabel avatarFrameLabel = new JLabel(avatarFrameIcon);
        avatarFrameLabel.setBounds(30, 10, 50, 50);

        ImageIcon userAvatar = PictureManipulationUtil.getUserAvatar(userNo, 50, 50);
        JLabel userInfoLabel = new JLabel(userAvatar, JLabel.LEFT);
        userInfoLabel.setBounds(30, 10, 310, 50);
        userInfoLabel.setText(userInfo.getName() + "-" + userInfo.getCompanyRank());
        userInfoLabel.setFont(FontUtil.MICROSOFT_YA_HEI_18);
        userInfoLabel.setForeground(Color.lightGray);
        //是否群主

        if (groupBasic.getUserRoleMap().get(userNo)==0){
            JLabel lordLabel = new JLabel(PictureManipulationUtil.getImageIcon("v1icon/lord.png",30,15));
            lordLabel.setBounds(80,50,30,15);
            memberPanel.add(lordLabel);
        }
        memberPanel.add(avatarFrameLabel);
        memberPanel.add(userInfoLabel);

        //移除成员按钮
        JLabel removeLabel = new JLabel(REMOVE_MEMBER_ICON);
        removeLabel.setBounds(360, 20, 30, 30);
        removeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeGroupMember(userInfo.getChatUserNo());
            }
        });
        //成员面板鼠标监听
        MouseAdapter panelAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                memberPanel.setBackground(null);
                memberPanel.remove(removeLabel);
                memberPanel.add(removeLabel);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                memberPanel.setBackground(Color.white);
                if (e.getY() < 5 || e.getY() > 65 || e.getX() < 5 || e.getX() > 395) {
                    memberPanel.remove(removeLabel);
                    memberPanel.updateUI();
                }
            }
        };


        memberPanel.addMouseListener(panelAdapter);
        memberDisplayPanel.add(memberPanel, GridBagUtil.wrap());
    }

    private void removeGroupMember(String operateUserNo) {
        CHANNEL.writeAndFlush(ClientPacketCodeCompile.encode(
                new OperateGroupRequest(chatUserByLogIn.getChatUserNo(), userRoleMap.get(chatUserByLogIn.getChatUserNo()), groupNo, groupName, operateUserNo, userRoleMap.get(operateUserNo), StringUtil.DELETE)));
    }

    public void refreshInterface(GroupBasic groupBasic) {
        userRoleMap = groupBasic.getUserRoleMap();
        memberDisplayPanel.removeAll();
        groupBasic.getUserRoleMap().keySet().forEach(userNo -> {
            addMember(userNo);
        });
        fillPanel.setPreferredSize(new Dimension(398, 410 - userRoleMap.size() * 70));
        memberDisplayPanel.add(fillPanel);
        memberDisplayPanel.updateUI();
    }
}
