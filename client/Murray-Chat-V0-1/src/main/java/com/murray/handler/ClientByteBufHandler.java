package com.murray.handler;

import com.alibaba.fastjson.JSON;
import com.murray.agreement.Packet;
import com.murray.cache.ClientCache;
import com.murray.dto.request.LoginRequest;
import com.murray.dto.response.*;
import com.murray.entity.LastLoginInfo;
import com.murray.entity.PrivateChatMessage;
import com.murray.utils.ColorUtil;
import com.murray.utils.ModuleUtil;
import com.murray.utils.SessionUtil;
import com.murray.view.vo.cell.GroupChatCellRenderer;
import com.murray.view.vo.cell.PrivateChatCellRenderer;
import com.murray.view.vo.frame.*;
import com.murray.view.vo.panel.AddressBookBasicScrollPanel;
import com.murray.view.vo.panel.AddressBookMemberScrollPanel;
import com.murray.view.vo.panel.GroupMSGPanel;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.murray.agreement.MessageType.GROUP_CHAT_TYPE;
import static com.murray.cache.ClientCache.*;
import static com.murray.utils.ResponseUtil.*;
import static java.lang.Thread.sleep;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class ClientByteBufHandler extends ChannelInboundHandlerAdapter {

    /**
     * @param ctx
     * @return void
     * @description 客户端发起连接请求后启动会立即调用该方法
     * @author Murray Law
     * @date 2020/10/24 2:57
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String userNoText = loginFrame.getUserNameJTextField().getText();
        String pwdText = loginFrame.getUserPasswordField().getText();
        chatUserByLogIn = new ChatUserInfoResponse();
        chatUserByLogIn.setChatUserNo(userNoText);
        //登录 获取本地存储的用户信息,发送账户校验,
        ctx.channel().writeAndFlush(ClientPacketCodeCompile.encode(new LoginRequest(userNoText, pwdText)));
    }

    /**
     * @param ctx
     * @param msg
     * @return void
     * @description 接受到服务端的消息会开始读取消息, 调用该方法
     * @author Murray Law
     * @date 2020/10/24 2:58
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //转化成缓冲区
        ByteBuf byteBuf = (ByteBuf) msg;
        String stringMsg = byteBuf.toString(StandardCharsets.UTF_8);
        System.out.println("服务端发来数据<<<=====" + stringMsg);
        Packet packet = ClientPacketCodeCompile.decode(byteBuf);
        if (null == packet) {
            return;
        }
        switch (packet.getCommand()) {
            case 0:
                //服务端基本响应
                serverBasicResponseHandle((ServerBasicResponse) packet);
                return;
            case 2:
                //登录响应
                loginResponseHandle((LoginResponse) packet);
                return;
            case 3:
                //判断收到消息响应
                messageResponseHandle(packet);
                return;
            case 6:
                //已读消息响应
                haveReadResponseHandle(packet);
                return;
            case 8:
                //聊天好友信息数据包响应
                chatFriendResponseHandle(packet);
                return;
            case 10:
                //文件接受响应
                serverReceivedResponseHandle((ServerReceivedResponse) packet);
                return;
            case 12:
                //群聊消息响应
                createGroupResponseHandle((CreateGroupResponse) packet);
                return;
            case 14:
                //收到群聊消息响应
                groupMsgResponseHandle((GroupMsgResponse) packet);
                return;
            case 18:
                //用户基本信息响应
                chatUserInfoResponseHandle((ChatUserInfoResponse) packet);
                return;
            case 24:
                //群组信息响应
                groupBasicHandle((GroupBasicResponse) packet);
                return;
            case 26:
                //用户基本信息响应
                meetingReminderHandle((MeetingReminderResponse) packet);
                return;
            case 28:
                //会议表单响应
                meetingTableHandle((MeetingTableResponse) packet);
                return;
            case 32:
                //通讯录响应处理
                addressBookHandle((AddressBookResponse) packet);
                return;
            case 34:
                //通讯录详情处理
                departmentMemberHandle((DepartmentMemberResponse) packet);
                return;
            case 35:
                //群聊已读处理
                groupMsgHaveReadHandle((GroupMsgHaveReadResponse) packet);
                return;
            default:
                System.err.println("客户端不支持的解码类型");
        }
    }

    /**
     * @param response
     * @return void
     * @description 群聊消息已读的处理方法
     * @author Murray Law
     * @date 2020/12/19 17:00
     */
    private void groupMsgHaveReadHandle(GroupMsgHaveReadResponse response) {
        GroupMSGPanel groupMSGPanel = CHAT_MAIN_FRAME.groupMSGPanel;
        if (groupMSGPanel != null) {
            Map<String, GroupChatCellRenderer> cellMap = groupMSGPanel.cellMap;
            //判断是否存在
            GROUP_AT_HAVE_READ_MAP.computeIfAbsent(response.getMsgNo(), k -> response.getAtHaveReadMap());
            response.getAtHaveReadMap().forEach((k, v) -> {
                if (v) {
                    GROUP_AT_HAVE_READ_MAP.get(response.getMsgNo()).put(k, v);
                }
            });
            if (cellMap.containsKey(response.getMsgNo())) cellMap.get(response.getMsgNo()).atHaveRead();
        }
    }

    private void groupBasicHandle(GroupBasicResponse response) {

        Map<String, GroupBasic> memberMap = response.getGroupMap();
        for (Map.Entry<String, GroupBasic> entry : memberMap.entrySet()) {
            GROUP_BASIC_MAP.put(entry.getKey(), entry.getValue());

        }
        if (CHAT_MAIN_FRAME == null) {
            return;
        }
        //刷新通讯录
        AddressBookMemberScrollPanel addressBookMemberScrollPanel = CHAT_MAIN_FRAME.addressBookMemberScrollPanel;
        addressBookMemberScrollPanel.setViewportView(addressBookMemberScrollPanel.initGroupBasicPanel(response));
        //刷新群组详情窗口
        GroupInfoFrame groupInfoFrame = CHAT_MAIN_FRAME.groupInfoFrame;
        String groupNo="";
        if (groupInfoFrame != null) {
            groupNo=groupInfoFrame.groupNo;
            GroupBasic groupBasic = response.getGroupMap().get(groupNo);
            //刷新用户列表
            groupInfoFrame.refreshInterface(groupBasic);
            //groupInfoFrame.groupNameLabel.setText(groupBasic.getGroupName());
            //groupInfoFrame.groupName=groupBasic.getGroupName();
        }
        if (CHAT_MAIN_FRAME.chatType.equals(GROUP_CHAT_TYPE)) {
            //改变群名称
            GroupBasic groupBasic = response.getGroupMap().get(groupNo);
            CHAT_MAIN_FRAME.objNameLabel.setText(groupBasic.getGroupName() + "  (" + groupBasic.getUserRoleMap().size() + ")");
        }
    }

    private void departmentMemberHandle(DepartmentMemberResponse response) {
        if (CHAT_MAIN_FRAME != null) {
            AddressBookMemberScrollPanel addressBookMemberScrollPanel = CHAT_MAIN_FRAME.addressBookMemberScrollPanel;
            addressBookMemberScrollPanel.setViewportView(addressBookMemberScrollPanel.initMemberPanel(response));
        }
        DEPARTMENT_MEMBER_RESPONSE_MAP.put(response.getDepartmentNo(), response.getDepartmentMemberMap());
        Map<String, ChatUserInfoResponse> memberMap = response.getDepartmentMemberMap();
        for (Map.Entry<String, ChatUserInfoResponse> entry : memberMap.entrySet()) {
            CHAT_USER_INFO_RESPONSE_MAP.put(entry.getKey(), entry.getValue());
        }
    }

    private void addressBookHandle(AddressBookResponse response) {
        AddressBookBasicScrollPanel addressBookBasicScrollPanel = CHAT_MAIN_FRAME.addressBookBasicScrollPanel;
        addressBookBasicScrollPanel.setViewportView(addressBookBasicScrollPanel.initAddressBookPanel(response));

    }

    /**
     * @param packet
     * @return void
     * @description 会议表单响应处理
     * @author Murray Law
     * @date 2020/11/25 18:00
     */
    private void meetingTableHandle(MeetingTableResponse packet) {
        meetingTableResponse = packet;
        CHAT_MAIN_FRAME.oaFrame.renderTableData();
    }

    /**
     * @param reminderResponse
     * @return void
     * @description 会议提醒响应
     * @author Murray Law
     * @date 2020/11/24 21:09
     */
    private void meetingReminderHandle(MeetingReminderResponse reminderResponse) {
        String meetingRemindNo = reminderResponse.getMeetingRemindNo();
        if (!MEETING_REMINDER_FRAME_MAP.containsKey(meetingRemindNo)) {
            MEETING_REMINDER_FRAME_MAP.put(meetingRemindNo, new MeetingReminderFrame(reminderResponse));
        }
        /**Calendar calendar = Calendar.getInstance();
         calendar.setTime(reminderResponse.getStartTime());
         int row = 0;
         if (calendar.get(Calendar.MINUTE) > 29) {
         row = calendar.get(Calendar.HOUR_OF_DAY) * 2 + 1;
         } else {
         row = calendar.get(Calendar.HOUR_OF_DAY) * 2;
         }
         //日期时间
         SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
         String format = sdf.format(reminderResponse.getStartTime());
         Object tableValueAt;
         if (meetingTable != null) {
         tableValueAt = meetingTable.getValueAt(row, calendar.get(Calendar.DAY_OF_WEEK));

         if (null != tableValueAt) {
         meetingTable.setValueAt(tableValueAt.toString() + "\n" + reminderResponse.getTitle() + "       " + reminderResponse.getLocation() + "       " + format, row, calendar.get(Calendar.DAY_OF_WEEK));
         } else {
         meetingTable.setValueAt(reminderResponse.getTitle() + "       " + reminderResponse.getLocation() + "       " + format, row, calendar.get(Calendar.DAY_OF_WEEK));
         }
         }*/
    }

    /**
     * @param userInfoResponse
     * @return void
     * @description 用户基本信息响应
     * @author Murray Law
     * @date 2020/11/16 11:20
     */
    private void chatUserInfoResponseHandle(ChatUserInfoResponse userInfoResponse) {
        CHAT_USER_INFO_RESPONSE_MAP.put(userInfoResponse.getChatUserNo(), userInfoResponse);
//        if (userInfoResponse.getChatUserNO().equals(chatUserByLogIn.getChatUserNO())){
//            CHAT_MAIN_FRAME.headSculpture.setIcon(PictureManipulationUtil.getAvatar(userInfoResponse.getChatUserNO(),80,80));
//        }
        //下载头像
//        CHANNEL.writeAndFlush(ClientPacketCodeCompile.encode(new AvatarRequest(chatUserByLogIn.getChatUserNO(),userInfoResponse.getChatUserNO())));
    }

    /**
     * @param packet
     * @return void
     * @description 判断是否消息数据包, 并将消息存入缓存
     * @author Murray Law
     * @date 2020/10/26 15:21
     */
    private void messageResponseHandle(Packet packet) {
        msgAudioClip.play();
        ClientCache.openPanelType = 0;
        PrivateChatMessage privateChatMessageResponse = (PrivateChatMessage) packet;
        //将服务器响应的消息存入缓存列表
        PRIVATE_CHAT_MESSAGE_MAP.put(privateChatMessageResponse.getMsgNo(), privateChatMessageResponse);
        //更改消息列表
        if (null == CHAT_MAIN_FRAME) {
            return;
        }
        if (CHAT_MAIN_FRAME.privateMSGPanel != null && CHAT_MAIN_FRAME.clickOnMsgCellNo.equals(((PrivateChatMessage) packet).getSenderUserNo())) {
            CHAT_MAIN_FRAME.privateMSGPanel.addPrivateChatCell(privateChatMessageResponse);
        }
        CHAT_MAIN_FRAME.refreshMsgScrollPanel();

    }


    /**
     * @param response
     * @return void
     * @description 群消息处理
     * @author Murray Law
     * @date 2020/11/13 17:47
     */
    private void groupMsgResponseHandle(GroupMsgResponse response) {
        if (!response.getSenderUserNo().equals(chatUserByLogIn.getChatUserNo())) {
            msgAudioClip.play();
        }
        ClientCache.openPanelType = 0;
        GROUP_MSG_MAP.put(response.getGroupMsgNo(), response);
        //更改消息列表
        if (null == CHAT_MAIN_FRAME) {
            return;
        }

        GroupMSGPanel groupMSGPanel = CHAT_MAIN_FRAME.groupMSGPanel;
        if (groupMSGPanel != null) {
            //如果聊天对象是该群聊
            if (CHAT_MAIN_FRAME.clickOnMsgCellNo.equals(response.getGroupNo())) {
                groupMSGPanel.addGroupChatCell(response);
                CHAT_MAIN_FRAME.refreshMsgScrollPanel();
            }
            //CHAT_MAIN_FRAME.groupMSGPanel.initGroupChatPanel(response.getGroupNo());
        }
        CHAT_MAIN_FRAME.refreshMsgScrollPanel();
    }

    /**
     * @param createGroupResponse
     * @return void
     * @description 创建群聊后的响应头
     * @author Murray Law
     * @date 2020/11/10 9:24
     */
    private void createGroupResponseHandle(CreateGroupResponse createGroupResponse) {
        String msg;
        if (SUCCESS_STATUS.equals(createGroupResponse.getStatus())) {
            msg = "创建群聊成功";
        } else {
            msg = "创建群聊失败";
        }
        ModuleUtil.showDialog("提示", msg, INFORMATION_MESSAGE);

    }

    private void serverReceivedResponseHandle(ServerReceivedResponse packet) {
        //找出聊天记录
        PrivateChatMessage privateChatMessage = PRIVATE_CHAT_MESSAGE_MAP.get(packet.getMsgNO());
        //找出对应的单元渲染格子
        PrivateChatCellRenderer privateChatCellRenderer = CHAT_MAIN_FRAME.privateMSGPanel.cellMap.get(packet.getMsgNO());
        //改变百分比
        if (privateChatCellRenderer != null) {
            privateChatCellRenderer.progressBar.setValue(packet.getPercentage());
        }
        if (packet.getStatus().equals(SUCCESS_STATUS)) {
            privateChatCellRenderer.uploadSuccess();
            uploadedToServerList.add(packet.getMsgNO());
        }
        if (packet.getStatus().equals(FAIL_STATUS)) {
            privateChatCellRenderer.uploadFailed();
        }
    }

    /**
     * @param packet
     * @return void
     * @description 判断是否好友响应数据包
     * @author Murray Law
     * @date 2020/11/2 16:48
     */
    private void chatFriendResponseHandle(Packet packet) {
        ChatFriendResponse chatFriendResponse = (ChatFriendResponse) packet;
        FRIEND_RESPONSE_MAP.put(chatFriendResponse.getFriendUserNo(), chatFriendResponse);
        if (CHAT_MAIN_FRAME != null) {
            CHAT_MAIN_FRAME.friendsListPanel.refreshFriendList();
        }
    }

    /**
     * @param packet
     * @return void
     * @description 判断是否已读数据包
     * @author Murray Law
     * @date 2020/10/31 22:10
     */
    private void haveReadResponseHandle(Packet packet) {
        HaveReadResponse haveReadResponse = (HaveReadResponse) packet;
        if (PRIVATE_CHAT_MESSAGE_MAP.containsKey(haveReadResponse.getChatMessageNO())) {
            //如果响应的接收者编号和是消息缓存中的发出用户编号一致, 设置为已读
            PRIVATE_CHAT_MESSAGE_MAP.get(haveReadResponse.getChatMessageNO()).setHaveRead(true);
        }
        //响应的发送者和消息面板的用户账号一致,并且cellMap有对应的单元
        if (haveReadResponse.getSenderUserNO().equals(CHAT_MAIN_FRAME.clickOnMsgCellNo)
                && CHAT_MAIN_FRAME.privateMSGPanel.cellMap.containsKey(haveReadResponse.getChatMessageNO())) {
            //设置自己发出的消息成已读
            CHAT_MAIN_FRAME.privateMSGPanel.cellMap.get(haveReadResponse.getChatMessageNO()).removeHaveRead();
            CHAT_MAIN_FRAME.privateMSGPanel.cellMap.get(haveReadResponse.getChatMessageNO()).updateUI();
        }
    }


    /**
     * @param loginResponse
     * @return void
     * @description 判断登录响应
     * @author Murray Law
     * @date 2020/10/25 20:05
     */
    private void loginResponseHandle( LoginResponse loginResponse) {
        JLabel promptLabel = loginFrame.getPromptLabel();
        promptLabel.setHorizontalAlignment(JLabel.LEFT);
        promptLabel.setIcon(null);
        if (SUCCESS_STATUS.equals(loginResponse.getPrompt())) {
            chatUserByLogIn = CHAT_USER_INFO_RESPONSE_MAP.get(loginResponse.getChatUserNO());
            //关闭登录窗口
            loginFrame.dispose();
            //打开主聊天面板
            CHAT_MAIN_FRAME = new ChatMainFrame();
            CHAT_MAIN_FRAME.frameInit();
            //写入登录信息
            LastLoginInfo lastLoginInfo = new LastLoginInfo(chatUserByLogIn.getChatUserNo(),chatUserByLogIn.getChatUserNo()+chatUserByLogIn.getAvatarType(),loginResponse.getPassword());
            writeLastLoginInfo(lastLoginInfo);
        }else if (CHANGE_PASSWORD.equals(loginResponse.getPrompt())){
            //关闭登录窗口
            loginFrame.dispose();
            //打开修改密码窗口
            CHANGE_PWD_FRAME = new ChangePasswordFrame(loginFrame.getUserNameJTextField().getText(),loginFrame.getUserPasswordField().getText());
        }
            promptLabel.setText(loginResponse.getPrompt());


    }

    /**
     * @param packet
     * @return void
     * @description 服务器基本响应处理
     * @author Murray Law
     * @date 2020/11/13 17:46
     */
    private void serverBasicResponseHandle(ServerBasicResponse packet) {
        switch (packet.getTitle()){
            //关于修改密码的响应
            case CHANGE_PASSWORD:
                if (CHANGE_PWD_FRAME!=null){
                    JLabel promptLabel = CHANGE_PWD_FRAME.getPromptLabel();
                    promptLabel.setText(packet.getMessage());
                    if (packet.getStatus().equals(FAIL_STATUS)){
                        promptLabel.setForeground(ColorUtil.WARNING_COLOR);
                        return;
                    }
                    promptLabel.setForeground(ColorUtil.UNREAD_COLOR);
                    try {
                        sleep(3000);
                        CHANGE_PWD_FRAME.dispose();
                        System.exit(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + packet.getTitle());
        }
        new PromptFrame(packet.getStatus(), packet.getTitle(), packet.getMessage());
    }


    private  void writeLastLoginInfo(LastLoginInfo loginInfo) {
        byte[] sourceByte = JSON.toJSONBytes(loginInfo);

        if(null != sourceByte){
            try {

                File file = new File(System.getProperty("user.dir") + "\\setting\\lastLogin"); //文件路径（路径+文件名）

                if (!file.exists()) { //文件不存在则创建文件，先创建目录

                    File dir = new File(file.getParent());

                    dir.mkdirs();

                    file.createNewFile();

                }

                FileOutputStream outStream = new FileOutputStream(file); //文件输出流用于将数据写入文件

                outStream.write(sourceByte);

                outStream.close(); //关闭文件输出流

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    // 用户断线之后取消绑定
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unBindSession(ctx.channel());
    }

}