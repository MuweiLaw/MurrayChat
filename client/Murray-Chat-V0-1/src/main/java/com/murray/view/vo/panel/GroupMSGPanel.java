package com.murray.view.vo.panel;

import com.murray.dto.request.HaveReadRequest;
import com.murray.dto.response.GroupMsgResponse;
import com.murray.utils.GridBagUtil;
import com.murray.view.vo.cell.GroupChatCellRenderer;
import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import static com.murray.agreement.MessageType.GROUP_CHAT_TYPE;
import static com.murray.agreement.MessageType.GROUP_FILE_TYPE;
import static com.murray.cache.ClientCache.*;
import static com.murray.handler.ClientPacketCodeCompile.encode;
import static com.murray.utils.DateUtil.getDateString;

/**
 * @author Murray Law
 * @describe 群聊面板消息
 * @createTime 2020/11/7
 */
public class GroupMSGPanel {
    public ConcurrentHashMap<String, GroupChatCellRenderer> cellMap = new ConcurrentHashMap<>();

    private final JScrollPane groupChatScrollPanel = new JScrollPane();
    private final JPanel chatPane = new JPanel();
    private final JTextArea filledUp = new JTextArea(" ");

    public GroupMSGPanel() {
        chatPane.setBorder(null);
        chatPane.setLayout(new GridBagLayout());
        groupChatScrollPanel.setBorder(null);
        groupChatScrollPanel.getVerticalScrollBar().setUnitIncrement(20);
        groupChatScrollPanel.getVerticalScrollBar().setUI(new WindowsScrollBarUI());
    }

    public JScrollPane getGroupMSGPanel() {
        return groupChatScrollPanel;
    }
    public JPanel getChatPane() {
        return chatPane;
    }

    /**
     * @param
     * @description 获取消息列表
     * @author Murray Law
     * @date 2020/10/30 3:17
     */
    public void initGroupChatPanel(String groupChatNO) {
        cellMap.clear();
        chatPane.removeAll();
        ArrayList<GroupMsgResponse> messageArrayList = new ArrayList<>(GROUP_MSG_MAP.values());
        messageArrayList.sort(Comparator.comparing(GroupMsgResponse::getIssueTime));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beforeTime = null;
        try {
            beforeTime = format.parse("1970-01-01 08:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //找到属于两人的聊天记录
        for (GroupMsgResponse chatMessage : messageArrayList) {
            if (chatMessage.getGroupNo().equals(groupChatNO)) {
                long abs = Math.abs(beforeTime.getTime() - chatMessage.getIssueTime().getTime());
                if (abs > 300000) {
                    JLabel timeLabel = new JLabel(getDateString(chatMessage.getIssueTime()), JLabel.CENTER);
                    chatPane.add(timeLabel, GridBagUtil.msgWrap());
                }
                addGroupChatCell(chatMessage);
                beforeTime = chatMessage.getIssueTime();
            }
        }
        JTextArea textArea = new JTextArea(" ");
        textArea.setBackground(null);
        chatPane.add(textArea, GridBagUtil.msgWrap());
        chatPane.updateUI();
    }

    public GroupChatCellRenderer addGroupChatCell(GroupMsgResponse groupMsg) {
        //将未读信息设置为已读
        if ((groupMsg.getMsgType().equals(GROUP_CHAT_TYPE)||groupMsg.getMsgType().equals(GROUP_FILE_TYPE)) && !groupMsg.isHaveRead()) {
            groupMsg.setHaveRead(true);
            //告诉服务器已读,本人发送的 已读数据包
            CHANNEL.writeAndFlush(encode(new HaveReadRequest(chatUserByLogIn.getChatUserNo(), groupMsg.getSenderUserNo(), groupMsg.getGroupMsgNo(), GROUP_CHAT_TYPE)));

        }
        //转换成聊天信息单元
        GroupChatCellRenderer groupChatCellRenderer = new GroupChatCellRenderer(groupMsg);
        chatPane.add(groupChatCellRenderer, GridBagUtil.wrap());
//        groupChatCellRenderer.updateUI();
        groupChatScrollPanel.setViewportView(chatPane);
        groupChatScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        groupChatScrollPanel.getVerticalScrollBar().setValue(groupChatScrollPanel.getVerticalScrollBar().getMaximum());
        //保存私聊聊天消息的单元
        cellMap.put(groupMsg.getGroupMsgNo(), groupChatCellRenderer);
        groupChatCellRenderer.updateUI();
        return groupChatCellRenderer;
    }

    public void refreshCell() {
        groupChatScrollPanel.setViewportView(chatPane);
    }
}