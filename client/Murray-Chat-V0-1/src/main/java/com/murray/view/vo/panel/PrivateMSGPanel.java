package com.murray.view.vo.panel;

import com.murray.dto.request.HaveReadRequest;
import com.murray.entity.PrivateChatMessage;
import com.murray.handler.listener.AvatarMouseAdapter;
import com.murray.utils.GridBagUtil;
import com.murray.view.vo.cell.PrivateChatCellRenderer;
import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import static com.murray.agreement.MessageType.PRIVATE_CHAT_TYPE;
import static com.murray.cache.ClientCache.*;
import static com.murray.handler.ClientPacketCodeCompile.encode;
import static com.murray.utils.DateUtil.getDateString;

/**
 * @author Murray Law
 * @describe 私聊面板消息
 * @createTime 2020/11/7
 */
public class PrivateMSGPanel {
    public ConcurrentHashMap<String, PrivateChatCellRenderer> cellMap = new ConcurrentHashMap<>();

    private final JScrollPane privateChatScrollPanel = new JScrollPane();
    private final JPanel chatPane = new JPanel();

    public PrivateMSGPanel() {
        chatPane.setBorder(null);
        chatPane.setLayout(new GridBagLayout());
        privateChatScrollPanel.setBorder(null);
        privateChatScrollPanel.getVerticalScrollBar().setUnitIncrement(20);
        privateChatScrollPanel.getVerticalScrollBar().setUI(new WindowsScrollBarUI());
    }

    public JScrollPane getPrivateMSGPanel() {
        return privateChatScrollPanel;
    }

    /**
     * @param
     * @description 获取消息列表
     * @author Murray Law
     * @date 2020/10/30 3:17
     */
    public void initPrivateChatPanel(String counterpartChatNO) {

        cellMap.clear();
        chatPane.removeAll();
        ArrayList<PrivateChatMessage> messageArrayList = new ArrayList<>(PRIVATE_CHAT_MESSAGE_MAP.values());
        messageArrayList.sort(Comparator.comparing(PrivateChatMessage::getIssueTime));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beforeTime = null;
        try {
            beforeTime = format.parse("1970-01-01 08:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //找到属于两人的聊天记录
        for (PrivateChatMessage chatMessage : messageArrayList) {
            if (chatMessage.getSenderUserNo().equals(counterpartChatNO) || (chatMessage.getReceiverUserNo().equals(counterpartChatNO) && chatMessage.getSenderUserNo().equals(chatUserByLogIn.getChatUserNo()))) {
                long abs = Math.abs(beforeTime.getTime() - chatMessage.getIssueTime().getTime());
                if (abs > 300000) {
                    JLabel timeLabel = new JLabel(getDateString(chatMessage.getIssueTime()), JLabel.CENTER);
                    chatPane.add(timeLabel, GridBagUtil.msgWrap());
                }
                PrivateChatCellRenderer cell = addPrivateChatCell(chatMessage);
                JLabel pictureLabel = cell.getProfilePictureLabel();
                pictureLabel.addMouseListener(new AvatarMouseAdapter(pictureLabel, chatMessage.getSenderUserNo()));
                beforeTime = chatMessage.getIssueTime();
            }
        }
        JTextArea textArea = new JTextArea(" ");//填充,不然滑不到最下
        textArea.setBackground(null);
        chatPane.add(textArea, GridBagUtil.msgWrap());
        chatPane.updateUI();
    }

    public PrivateChatCellRenderer addPrivateChatCell(PrivateChatMessage privateChatMessage) {
        //将未读信息设置为已读
        if (privateChatMessage.getMsgType() != 2 && !privateChatMessage.getSenderUserNo().equals(chatUserByLogIn.getChatUserNo()) && !privateChatMessage.getHaveRead()) {
            privateChatMessage.setHaveRead(true);
            //告诉服务器已读,本人发送的 已读数据包
            CHANNEL.writeAndFlush(encode(new HaveReadRequest(chatUserByLogIn.getChatUserNo(), privateChatMessage.getSenderUserNo(), privateChatMessage.getMsgNo(), PRIVATE_CHAT_TYPE)));

        }
        //转换成聊天信息单元,删除最后的空白区域
        PrivateChatCellRenderer privateChatCellRenderer = new PrivateChatCellRenderer(privateChatMessage);
        chatPane.add(privateChatCellRenderer, GridBagUtil.msgWrap());
        privateChatScrollPanel.setViewportView(chatPane);
        privateChatScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        privateChatScrollPanel.getVerticalScrollBar().setValue(privateChatScrollPanel.getVerticalScrollBar().getMaximum());
        //保存私聊聊天消息的单元
        cellMap.put(privateChatMessage.getMsgNo(), privateChatCellRenderer);
        privateChatCellRenderer.updateUI();
        return privateChatCellRenderer;
    }

    public void refreshCell() {
        chatPane.updateUI();
    }
}