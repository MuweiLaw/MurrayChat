package com.murray.view.vo.panel;

import com.murray.agreement.MessageType;
import com.murray.dto.response.ChatUserInfoResponse;
import com.murray.dto.response.DepartmentMemberResponse;
import com.murray.dto.response.GroupBasic;
import com.murray.dto.response.GroupBasicResponse;
import com.murray.utils.BorderUtil;
import com.murray.utils.ColorUtil;
import com.murray.utils.FontUtil;
import com.murray.utils.GridBagUtil;
import com.murray.view.vo.cell.DepartmentMemberCell;
import com.murray.view.vo.cell.GroupBasicCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import static com.murray.cache.ClientCache.*;

/**
 * @author Murray Law
 * @describe 通讯录面板
 * @createTime 2020/12/7
 */
public class AddressBookMemberScrollPanel extends JScrollPane {
    private JPanel addressBookDetailedPanel = new JPanel(new GridBagLayout());

    public AddressBookMemberScrollPanel() {
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    }

    public JPanel initMemberPanel(DepartmentMemberResponse response) {
        Dimension memberDimension = new Dimension(this.getWidth(), 80);
        int count = 0;
        Map<String, ChatUserInfoResponse> departmentMemberMap = response.getDepartmentMemberMap();
        addressBookDetailedPanel.removeAll();
        //通讯录详情列表显示
        for (Map.Entry<String, ChatUserInfoResponse> entry : departmentMemberMap.entrySet()) {
            ChatUserInfoResponse userInfo = entry.getValue();
            CHAT_USER_INFO_RESPONSE_MAP.put(entry.getKey(), userInfo);
            count += 1;
            DepartmentMemberCell departmentMemberCell = new DepartmentMemberCell(userInfo);
            departmentMemberCell.setPreferredSize(memberDimension);
            departmentMemberCell.setOpaque(true);
            departmentMemberCell.setBackground(Color.white);
            addMsgCellMouseListener(departmentMemberCell, userInfo.getName(), userInfo.getChatUserNo());
            departmentMemberCell.setFont(FontUtil.MICROSOFT_YA_HEI_16);
            if (count % 2 == 0) {
                departmentMemberCell.setBorder(BorderUtil.BORDER_WHITE_235);
            }
            addressBookDetailedPanel.add(departmentMemberCell, GridBagUtil.wrap());
        }
        JLabel fillUpLabel = new JLabel();
        fillUpLabel.setPreferredSize(new Dimension(this.getWidth(), CHAT_MAIN_FRAME.mainFrame.getHeight() - 80 * departmentMemberMap.size() - 110));
        addressBookDetailedPanel.add(fillUpLabel);
        addressBookDetailedPanel.setBorder(null);
        return addressBookDetailedPanel;
    }

    public JPanel initGroupBasicPanel(GroupBasicResponse response) {
        Dimension memberDimension = new Dimension(this.getWidth(), 80);
        int count = 0;
        Map<String, GroupBasic> groupBasicMap = response.getGroupMap();
        addressBookDetailedPanel.removeAll();
        //通讯录详情列表显示
        for (Map.Entry<String, GroupBasic> entry : groupBasicMap.entrySet()) {
            GroupBasic groupBasic = entry.getValue();
            GROUP_BASIC_MAP.put(entry.getKey(),groupBasic );
            count += 1;
            GroupBasicCell groupBasicCell = new GroupBasicCell(groupBasic);
            groupBasicCell.setPreferredSize(memberDimension);
            groupBasicCell.setOpaque(true);
            groupBasicCell.setBackground(Color.white);
            addMsgCellMouseListener(groupBasicCell,groupBasic.getGroupName(),groupBasic.getGroupNo());
            groupBasicCell.setFont(FontUtil.MICROSOFT_YA_HEI_16);
            if (count % 2 == 0) {
                groupBasicCell.setBorder(BorderUtil.BORDER_WHITE_235);
            }
            addressBookDetailedPanel.add(groupBasicCell, GridBagUtil.wrap());
        }
        JLabel fillUpLabel = new JLabel();
        fillUpLabel.setPreferredSize(new Dimension(this.getWidth(), CHAT_MAIN_FRAME.mainFrame.getHeight() - 80 * groupBasicMap.size() - 110));
        addressBookDetailedPanel.add(fillUpLabel);
        addressBookDetailedPanel.setBorder(null);
        return addressBookDetailedPanel;
    }

    private void addMsgCellMouseListener(JLabel cell, String name, String no) {
        cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cell.setBackground(Color.white);
                CHAT_MAIN_FRAME.chatPanel.removeAll();
                CHAT_MAIN_FRAME.mainFrame.remove(CHAT_MAIN_FRAME.addressBookBar);
                CHAT_MAIN_FRAME.mainFrame.remove(CHAT_MAIN_FRAME.addressBookBasicScrollPanel);
                CHAT_MAIN_FRAME.mainFrame.remove(CHAT_MAIN_FRAME.addressBookMemberScrollPanel);
                //消息列表
                CHAT_MAIN_FRAME.msgList();
                //聊天面板
                CHAT_MAIN_FRAME.chat();
                if (CHAT_MAIN_FRAME.clickOnMsgCellNo!=null&&CHAT_MAIN_FRAME.msgCellMap.containsKey(CHAT_MAIN_FRAME.clickOnMsgCellNo)){
                    CHAT_MAIN_FRAME.msgCellMap.get(CHAT_MAIN_FRAME.clickOnMsgCellNo).setBackground(Color.white);
                }
                CHAT_MAIN_FRAME.mainFrame.repaint();
                if (cell instanceof DepartmentMemberCell) {
                    CHAT_MAIN_FRAME.chatType= MessageType.PRIVATE_CHAT_TYPE;
                    CHAT_MAIN_FRAME.getPrivateChat(name, no);
                }
                if (cell instanceof GroupBasicCell) {
                    CHAT_MAIN_FRAME.chatType= MessageType.GROUP_CHAT_TYPE;
                    CHAT_MAIN_FRAME.getGroupChat(name, no);
                    //刷新消息面板
                    CHAT_MAIN_FRAME.refreshMsgScrollPanel();
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cell.setBackground(ColorUtil.WHITE235);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cell.setBackground(Color.white);
            }
        });
    }
}
