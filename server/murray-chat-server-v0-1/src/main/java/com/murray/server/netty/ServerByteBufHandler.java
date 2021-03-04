package com.murray.server.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.murray.agreement.Packet;
import com.murray.config.RedisConfig;
import com.murray.dto.request.*;
import com.murray.dto.response.*;
import com.murray.entity.*;
import com.murray.server.*;
import com.murray.session.Session;
import com.murray.utils.ClassUtil;
import com.murray.utils.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.murray.agreement.MessageType.GROUP_CHAT_TYPE;
import static com.murray.config.ServerCache.SERVER_AVATAR_SAVE_PATH;
import static com.murray.config.ServerCache.SERVER_FILE_SAVE_PATH;
import static com.murray.server.netty.ServerPacketCodeCompile.encode;
import static com.murray.utils.ClassUtil.parseObj2Map;
import static com.murray.utils.ResponseUtil.*;
import static com.murray.utils.StringUtil.DELETE;
import static com.murray.utils.StringUtil.UPDATE;

@ChannelHandler.Sharable
public class ServerByteBufHandler extends ChannelInboundHandlerAdapter {

    //注入Redis
    private RedisTemplate<String, Object> redisTemplate = ManageSpringBeans.getBean(RedisTemplate.class);
    private StringRedisTemplate stringRedisTemplate = ManageSpringBeans.getBean(StringRedisTemplate.class);

    //注入jackson 映射器
    private ObjectMapper objectMapper = ManageSpringBeans.getBean(ObjectMapper.class);
    //邮件
    private JavaMailSender mailSender = ManageSpringBeans.getBean(JavaMailSender.class);

    //注入Service Bean
    private ChatUserService chatUserService = ManageSpringBeans.getBean(ChatUserService.class);
    private ChatUserInfoService chatUserInfoService = ManageSpringBeans.getBean(ChatUserInfoService.class);
    private ChatFriendsService chatFriendsService = ManageSpringBeans.getBean(ChatFriendsService.class);
    private ChatGroupService chatGroupService = ManageSpringBeans.getBean(ChatGroupService.class);
    private ChatGroupRoleService chatGroupRoleService = ManageSpringBeans.getBean(ChatGroupRoleService.class);
    private ChatMeetingService chatMeetingService = ManageSpringBeans.getBean(ChatMeetingService.class);
    private ChatUserMeetingService chatUserMeetingService = ManageSpringBeans.getBean(ChatUserMeetingService.class);
    private ChatDepartmentService chatDepartmentService = ManageSpringBeans.getBean(ChatDepartmentService.class);
    private ChatUserDepartmentService chatUserDepartmentService = ManageSpringBeans.getBean(ChatUserDepartmentService.class);

    private AddressBookService addressBookService = ManageSpringBeans.getBean(AddressBookService.class);
    private DepartmentMemberService departmentMemberService = ManageSpringBeans.getBean(DepartmentMemberService.class);


    private final HashOperations hashOperations = redisTemplate.opsForHash();

    /**
     * @param ctx
     * @param msg
     * @return void
     * @description 有消息发来将触发此处内容
     * @author Murray Law
     * @date 2020/11/1 20:19
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        Channel channel = ctx.channel();
        String stringMsg = byteBuf.toString(StandardCharsets.UTF_8);
        System.out.println("收到客户端发来请求 <<<===" + stringMsg);
        Packet packet = ServerPacketCodeCompile.decode(byteBuf);
        if (null != packet) {
            switch (packet.getCommand()) {
                case 1:
                    //登录请求
                    loginRequestHandle(channel, packet);
                    return;
                case 3:
                    //私聊消息请求
                    messageRequestHandle(packet);
                    return;
                case 5:
                    //已读数据包请求
                    haveReadRequestHandle(packet);
                    return;
                case 11:
                    //建群请求
                    createGroupRequestHandle(packet);
                    return;
                case 13:
                    //群聊消息请求
                    groupMsgRequestHandle(packet);
                    return;
                case 15:
                    //邮箱请求
                    mailRequestHandle(ctx, (MailRequest) packet);
                    return;
                case 17:
                    //用户信息请求
                    chatInfoRequestHandle(channel, (ChatUserInfoRequest) packet);
                    return;
                case 21:
                    //用户头像请求
                    avatarRequestHandle(channel, (AvatarRequest) packet);
                    return;
                case 23:
                    //群组基本信息请求
                    groupBasicRequest(channel, (GroupBasicRequest) packet);
                    return;
                case 25:
                    //会议提醒请求
                    meetingReminderRequestHandle(channel, (MeetingReminderRequest) packet);
                    return;
                case 27:
                    //会议表格请求
                    meetingTableRequestHandle(channel, (MeetingTableRequest) packet);
                    return;
                case 31:
                    //通讯录请求处理
                    addressBookRequestHandle(channel, (AddressBookRequest) packet);
                    return;
                case 33:
                    //通讯录请求处理
                    departmentMemberRequestHandle(channel, (DepartmentMemberRequest) packet);
                    return;
                case 36:
                    //通讯录请求处理
                    operateGroupRequestHandle(channel, (OperateGroupRequest) packet);
                    return;
                case 37:
                    //修改密码请求处理
                    changePwdRequestHandle(channel, (ChangePwdRequest) packet);
                    return;
                default:
                    System.err.println("没有相关指令");
            }
        }
    }

    /**
     * @param channel
     * @param request
     * @return void
     * @description 修改密码
     * @author Murray Law
     * @date 2020/12/31 7:24
     */
    private void changePwdRequestHandle(Channel channel, ChangePwdRequest request) {
        ChatUser chatUser = new ChatUser();
        chatUser.setPassword(request.getNewPwd());
        //更新数据库
        int i = chatUserService.updateByUserNoAndPwd(chatUser, request.getChatUserNo(), request.getOldPwd());
        //反馈修改是否成功
        if (i > 0) {
            channel.writeAndFlush(encode(new ServerBasicResponse(SUCCESS_STATUS, CHANGE_PASSWORD, "修改密码成功")));
        } else {
            channel.writeAndFlush(encode(new ServerBasicResponse(FAIL_STATUS, CHANGE_PASSWORD, "修改密码失败")));
        }
    }

    /**
     * @param channel
     * @param request
     * @return void
     * @description 操作群组的请求处理
     * @author Murray Law
     * @date 2020/12/25 5:40
     */
    private void operateGroupRequestHandle(Channel channel, OperateGroupRequest request) {
        switch (request.getOperateType()) {
            case DELETE:
                //如果是删除请求,角色值小于被操作的用户编号才可以操作
                if (request.getRequesterRole() != 2 && request.getRequesterRole() < request.getOperateUserRole()) {
                    int delCount = chatGroupRoleService.deleteByGroupNoAndUserNo(request.getGroupNo(), request.getOperateUserNo());
                    if (delCount > 0) {
                        channel.writeAndFlush(encode(chatGroupService.selectByUserNo(request.getRequesterNo())));
                    }
                }
                return;
            case UPDATE:
                //如果是更新请求
                if (request.getRequesterRole() != 2) {
                    chatGroupService.updateByGroupNoAndGroupName(request.getGroupNo(), request.getGroupName());
                    channel.writeAndFlush(encode(chatGroupService.selectByUserNo(request.getRequesterNo())));
                }
        }
    }

    /**
     * @param channel
     * @param request
     * @return void
     * @description 群组的基本信息请求
     * @author Murray Law
     * @date 2020/12/25 5:39
     */
    private void groupBasicRequest(Channel channel, GroupBasicRequest request) {
        channel.writeAndFlush(encode(chatGroupService.selectByUserNo(request.getRequesterNo())));
    }

    /**
     * @param channel
     * @param request
     * @return void
     * @description 部门成员请求
     * @author Murray Law
     * @date 2020/12/8 22:09
     */
    private void departmentMemberRequestHandle(Channel channel, DepartmentMemberRequest request) {
        channel.writeAndFlush(encode(departmentMemberService.getDepartmentMemberResponse(request.getRequesterNo(), request.getDepartmentNo(), request.getDepartmentName())));
    }

    /**
     * @param channel
     * @param request
     * @return void
     * @description 通讯录基本信息请求
     * @author Murray Law
     * @date 2020/12/8 22:09
     */
    private void addressBookRequestHandle(Channel channel, AddressBookRequest request) {
        channel.writeAndFlush(encode(addressBookService.getAddressBookBasic(request)));
    }

    /**
     * @param channel             请求者频道
     * @param meetingTableRequest 请求体
     * @return void
     * @description 会议表请求处理
     * @author Murray Law
     * @date 2020/11/25 17:56
     */
    private void meetingTableRequestHandle(Channel channel, MeetingTableRequest meetingTableRequest) {
        //服务器响应已收到
        channel.writeAndFlush(ServerPacketCodeCompile.encode(chatMeetingService.getMeetingTable(meetingTableRequest)));
    }

    /**
     * @param reminderRequest 会议提醒的请求体
     * @return void
     * @description 收到会议提醒的请求
     * @author Murray Law
     * @date 2020/11/24 9:38
     */
    private void meetingReminderRequestHandle(Channel channel, MeetingReminderRequest reminderRequest) {
        ServerBasicResponse serverBasicResponse = new ServerBasicResponse(SUCCESS_STATUS, "状态", "创建会议提醒成功!");
        chatMeetingService.insert(reminderRequest);
        //服务器响应已收到
        channel.writeAndFlush(ServerPacketCodeCompile.encode(serverBasicResponse));
    }

    /**
     * @param channel       频道
     * @param avatarRequest 头像请求
     * @return void
     * @description 头像请求的消息处理
     * @author Murray Law
     * @date 2020/11/16 18:56
     */
    private void avatarRequestHandle(Channel channel, AvatarRequest avatarRequest) {
        ChatUserInfo chatUserInfo = chatUserInfoService.selectByUserNo(avatarRequest.getQueryUserNo());
        File file = new File(SERVER_AVATAR_SAVE_PATH + avatarRequest.getQueryUserNo() + chatUserInfo.getAvatarType());
        if (!file.exists()) {
            file = new File(SERVER_AVATAR_SAVE_PATH + "defaultUserAvatar.jpg");
        }
        channel.writeAndFlush(encode(new AvatarPacket(file)));
    }

    /**
     * @param channel     频道
     * @param infoRequest 信息请求
     * @return void
     * @Description 聊天用户的信息处理
     * @author Murray Law
     * @Date 2020/11/16 9:52
     */
    private void chatInfoRequestHandle(Channel channel, ChatUserInfoRequest infoRequest) {
        writeAndFlushUserInfo(channel, infoRequest.getQueryUserNo());
    }

    /**
     * @param channel
     * @param queryUserNo
     * @return void
     * @description 推送用户信息
     * @author Murray Law
     * @date 2020/11/17 8:36
     */
    private ChatUserInfoResponse writeAndFlushUserInfo(Channel channel, String queryUserNo) {
        ChatUserInfoResponse chatUserInfoResponse = new ChatUserInfoResponse();
        ChatUser chatUser = chatUserService.selectChatUserByNo(queryUserNo);
        ChatUserInfo chatUserInfo = chatUserInfoService.selectByUserNo(queryUserNo);
        chatUserInfoResponse.setCompanyDepartment(chatUserDepartmentService.selectDepartmentNameByUserNo(queryUserNo));
        BeanUtils.copyProperties(chatUser, chatUserInfoResponse);
        BeanUtils.copyProperties(chatUserInfo, chatUserInfoResponse);
        chatUserInfoResponse.setCompany(chatUserInfo.getCompany());
        channel.writeAndFlush(encode(chatUserInfoResponse));
        return chatUserInfoResponse;
    }

    /**
     * @param packet
     * @return void
     * @description 邮件处理
     * @author Murray Law
     * @date 2020/11/12 21:56
     */
    private void mailRequestHandle(ChannelHandlerContext ctx, MailRequest packet) {
        MimeMessageHelper messageHelper = null;
        Date now = new Date();
        String title = "邮件状态提醒";
        String[] recipientArray = new String[packet.getRecipientArray().length];
        String[] ccArray = new String[packet.getCcArray().length];

        for (int i = 0; i < packet.getRecipientArray().length; i++) {
            String receiverNo = packet.getRecipientArray()[i];
            ChatUserInfo chatUserInfo = chatUserInfoService.selectByUserNo(receiverNo);
            if (null != chatUserInfo) {
                recipientArray[i] = chatUserInfo.getMail();
            }
        }
        for (int i = 0; i < packet.getCcArray().length; i++) {
            String ccUserNo = packet.getCcArray()[i];
            ChatUserInfo chatUserInfo = chatUserInfoService.selectByUserNo(ccUserNo);
            if (null != chatUserInfo) {
                ccArray[i] = chatUserInfo.getMail();
            }
        }

        //发送者
        ChatUserInfo userInfo = chatUserInfoService.selectByUserNo(packet.getMailSenderNo());
        String fromMail = userInfo.getMail();//发送者邮箱
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        String mail = userInfo.getMail();
        String mailSuffix = mail.substring(mail.lastIndexOf("@") + 1, mail.length());
//        mailSuffix = mail.substring(mailSuffix.length());
        javaMailSender.setHost("smtp." + mailSuffix);
        javaMailSender.setUsername(fromMail); // s根据自己的情况,设置username
        javaMailSender.setPassword(userInfo.getMailAuth()); // 根据自己的情况, 设置password口令
//		javaMailSender.setPort(465);
        javaMailSender.setDefaultEncoding("UTF-8");
        //传入相关参数
        MimeMessage mailMessage = javaMailSender.createMimeMessage();

        try {
            messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            messageHelper.setTo(recipientArray);
            messageHelper.setCc(ccArray);
            messageHelper.setFrom(fromMail);
            messageHelper.setSubject(packet.getMailTitle());
            // true 表示启动HTML格式的邮件
            messageHelper.setText(packet.getMailText(), false);
        } catch (MessagingException e) {
            ctx.channel().writeAndFlush(encode(new ServerBasicResponse(FAIL_STATUS, "邮件发送异常", e.getMessage())));
        }
        //反馈信息给客户端
        ServerBasicResponse serverBasicResponse = new ServerBasicResponse();
        serverBasicResponse.setTitle(title);
        //邮件提取附件
        MimeMessageHelper finalMessageHelper = messageHelper;
        packet.getFileMap().forEach((fileUUID, fileName) -> {
            FileSystemResource file = new FileSystemResource(new File(SERVER_FILE_SAVE_PATH + fileUUID));
            try {
                finalMessageHelper.addAttachment(fileName, file);
            } catch (MessagingException e) {
                serverBasicResponse.setStatus(FAIL_STATUS);
                serverBasicResponse.setMessage("附件错误:" + e.getMessage());
                ctx.channel().writeAndFlush(encode(serverBasicResponse));
            }
        });
        serverBasicResponse.setStatus(WAITING_STATUS);
        serverBasicResponse.setTitle(title);
        serverBasicResponse.setMessage("邮件已上传到服务器");
        ctx.channel().writeAndFlush(encode(serverBasicResponse));
        javaMailSender.send(mailMessage);
        serverBasicResponse.setStatus(SUCCESS_STATUS);
        serverBasicResponse.setTitle(title);
        serverBasicResponse.setMessage("邮件发送成功");
        ctx.channel().writeAndFlush(encode(serverBasicResponse));

    }

    /**
     * @param packet
     * @return void
     * @description 群消息处理器
     * @author Murray Law
     * @date 2020/11/10 17:13
     */
    private void groupMsgRequestHandle(Packet packet) {
        // 处理消息并缓存
        GroupMsgRequest groupMsgRequest = ((GroupMsgRequest) packet);
        //设置未读列表
        List<String> unreadUserNoList = chatGroupRoleService.selectUserNoByGroupNo(groupMsgRequest.getGroupNo());
        //缓存消息
        hashOperations.putAll(GroupMsgRequest.GROUP_MSG + groupMsgRequest.getGroupMsgNo(), ClassUtil.parseObj2Map(groupMsgRequest));
        //群聊的最后一条消息
        stringRedisTemplate.opsForValue().set(groupMsgRequest.getGroupNo(), groupMsgRequest.getMessage());//缓存最后一条消息
        //如果有群成员在线就推送群消息
        for (String unreadUserNo : unreadUserNoList) {
            //将消息发送给其他群成员消息
            Channel receiverChannel = SessionUtil.getChannel(unreadUserNo);
            if (receiverChannel != null) {
                //推送群消息
                pushGroupMessage(receiverChannel, groupMsgRequest, unreadUserNo);
            }
        }
        //去除自己未读
        unreadUserNoList.remove(groupMsgRequest.getSenderUserNo());
        String msgKey = GroupMsgRequest.GROUP_MSG + groupMsgRequest.getGroupMsgNo();
        //缓存
        hashOperations.put(msgKey, GroupMsgRequest.FIELD_UNREAD_USER_NO_LIST, unreadUserNoList);
        unreadUserNoList.forEach(uuReaderNo -> hashOperations.put(uuReaderNo + RedisConfig.KEY_HAVE_READ, msgKey, false));
//    todo   反馈发送者服务器已收到
    }

    /**
     * @param channel
     * @param groupMsgRequest
     * @param unreadUserNo
     * @return void
     * @description 推送群消息
     * @author Murray Law
     * @date 2020/11/15 3:52
     */
    private void pushGroupMessage(Channel channel, GroupMsgRequest groupMsgRequest, String unreadUserNo) {
        //复制属性并且发给其他人
        GroupMsgResponse groupMsgResponse = new GroupMsgResponse();
        BeanUtils.copyProperties(groupMsgRequest, groupMsgResponse);
        groupMsgResponse.setReceiverUserNo(unreadUserNo);
        //判断@的人列表有没有我
        if (unreadUserNo.equals(groupMsgRequest.getSenderUserNo())) {
            groupMsgResponse.setHaveRead(true);
        }
        //判断@的人列表有没有我
        if (groupMsgRequest.getAtUserNoList().contains(unreadUserNo)) {
            groupMsgResponse.setAtMe(true);
        } else {
            groupMsgResponse.setAtMe(false);
        }
        // 1. 转换数据并推送群消息给客户端
        channel.writeAndFlush(encode(groupMsgResponse));
    }

    /**
     * @param packet
     * @return void
     * @description 建群请求处理
     * @author Murray Law
     * @date 2020/11/15 3:27
     */

    private void createGroupRequestHandle(Packet packet) {
        Date date = new Date();
        CreateGroupRequest createGroupRequest = (CreateGroupRequest) packet;
        ChatGroup chatGroup = new ChatGroup(null, createGroupRequest.getGroupNO(), createGroupRequest.getGroupName(), null, createGroupRequest.getMaximum(), createGroupRequest.getGroupOwnerNo(), date, date);
        List<String> memberList = createGroupRequest.getMemberList();
        ArrayList<ChatGroupUserRole> chatGroupUserRoles = new ArrayList<>();
        //获取要存入DB的List信息
        memberList.forEach(userNo -> {
            if (!userNo.equals(createGroupRequest.getGroupOwnerNo())) {
                chatGroupUserRoles.add(new ChatGroupUserRole(null, userNo, chatGroup.getGroupNo(), (byte) 2, date, date));
            }
        });
        //创建响应头
        CreateGroupResponse createGroupResponse = new CreateGroupResponse(chatGroup.getGroupNo(), FAIL_STATUS);
        createGroupResponse.setStatus(FAIL_STATUS);
        //DB保存创建群信息,
        if (chatGroupService.insert(chatGroup) > 0) {
            //设置群主
            chatGroupRoleService.insert(new ChatGroupUserRole(null, createGroupRequest.getGroupOwnerNo(), chatGroup.getGroupNo(), (byte) 0, date, date));
            //保存群成员信息
            if (!chatGroupUserRoles.isEmpty()) {
                chatGroupRoleService.insertList(chatGroupUserRoles);
            }
            createGroupResponse.setStatus(SUCCESS_STATUS);
        }
        Channel channel = SessionUtil.getChannel(createGroupRequest.getGroupOwnerNo());
        channel.writeAndFlush(encode(createGroupResponse));
    }

    /**
     * @param packet
     * @return void
     * @description 判断是否已读包数据
     * @author Murray Law
     * @date 2020/11/1 20:18
     */
    private void haveReadRequestHandle(Packet packet) {
        HaveReadRequest haveReadRequest = (HaveReadRequest) packet;
        //将对应消息设置为已读
        switch (haveReadRequest.getChatMessageType()) {
            case 0:
                String msgKey = PrivateChatMessage.PRIVATE_MSG + haveReadRequest.getChatMessageNO();
                //redis 中设置已读
                hashOperations.put(msgKey, RedisConfig.FIELD_HAVE_READ, true);
                hashOperations.put(haveReadRequest.getSenderUserNO() + RedisConfig.KEY_HAVE_READ, msgKey, true);
                //设置消息过期
                redisTemplate.expire(msgKey, 7, TimeUnit.DAYS);
                break;
            case 1:
                //去除群消息的列表未读
                String groupMsgKey = GroupMsgRequest.GROUP_MSG + haveReadRequest.getChatMessageNO();
                Object unreadListObj = hashOperations.get(groupMsgKey, GroupMsgRequest.FIELD_UNREAD_USER_NO_LIST);
                Object atListObj = hashOperations.get(groupMsgKey, GroupMsgRequest.FIELD_AT_USER_NO_LIST);

                List<String> unreadUserNoList = objectMapper.convertValue(unreadListObj, List.class);
                List<String> atUserNoList = objectMapper.convertValue(atListObj, List.class);
                unreadUserNoList.remove(haveReadRequest.getSenderUserNO());
                if (atUserNoList.contains(haveReadRequest.getSenderUserNO())) {
                    HashMap<String, Boolean> atUserHaveReadMap = new HashMap<>();
                    atUserNoList.forEach(atUser -> {
                        if (unreadUserNoList.contains(atUser)) {
                            atUserHaveReadMap.put(atUser, false);
                        } else {
                            atUserHaveReadMap.put(atUser, true);
                        }
                    });
                    //查看响应的接受收者是否在线
                    Channel receiveChannel = SessionUtil.getChannel(haveReadRequest.getReceiveUserNO());
                    //如果在线,并且接收者不是自己,告诉接收者,对方已经读取.
                    if (receiveChannel != null && haveReadRequest.getChatMessageType().equals(GROUP_CHAT_TYPE)) {
                        receiveChannel.writeAndFlush(encode(
                                new GroupMsgHaveReadResponse(haveReadRequest.getChatMessageNO(), haveReadRequest.getSenderUserNO(), haveReadRequest.getReceiveUserNO(), atUserHaveReadMap)));
                    }
                }
                hashOperations.put(groupMsgKey, GroupMsgRequest.FIELD_UNREAD_USER_NO_LIST, unreadUserNoList);
                hashOperations.put(haveReadRequest.getSenderUserNO() + RedisConfig.KEY_HAVE_READ, groupMsgKey, true);

                break;
            case 2:
                //如果消息类型是私聊文件类型,则向请求者发送文件下载
                Channel senderChannel = SessionUtil.getChannel(haveReadRequest.getSenderUserNO());
                //序列化传输文件给请求下载者
                senderChannel.writeAndFlush(encode(new FilePacket(haveReadRequest.getChatMessageNO(), haveReadRequest.getChatMessageNO())));
                //不是自己读自己的才能设置已读已下载
                if (!haveReadRequest.getReceiveUserNO().equals(haveReadRequest.getSenderUserNO())) {
                    String fileKey = PrivateChatMessage.PRIVATE_MSG + haveReadRequest.getChatMessageNO();
                    //redis 中设置已读
                    hashOperations.put(fileKey, RedisConfig.FIELD_HAVE_READ, true);
                    hashOperations.put(haveReadRequest.getSenderUserNO() + RedisConfig.KEY_HAVE_READ, fileKey, true);
                    //设置消息过期
                    redisTemplate.expire(fileKey, 7, TimeUnit.DAYS);
                }
                break;
            case 3:
                Object o = hashOperations.get(haveReadRequest.getSenderUserNO() + RedisConfig.KEY_HAVE_READ, GroupMsgRequest.GROUP_MSG + haveReadRequest.getChatMessageNO());

                if (objectMapper.convertValue(o, Boolean.class)) {
                    //群文件
                    Channel groupSenderChannel = SessionUtil.getChannel(haveReadRequest.getSenderUserNO());
                    //序列化传输文件给请求下载者
                    groupSenderChannel.writeAndFlush(encode(new FilePacket(haveReadRequest.getChatMessageNO(), haveReadRequest.getChatMessageNO())));

                }
                hashOperations.put(haveReadRequest.getSenderUserNO() + RedisConfig.KEY_HAVE_READ, GroupMsgRequest.GROUP_MSG + haveReadRequest.getChatMessageNO(), true);
                return;
            case 4:
                //私邮件
                String mailKey = PrivateChatMessage.PRIVATE_MSG + haveReadRequest.getChatMessageNO();
                //redis 中设置已读
                hashOperations.put(mailKey, RedisConfig.FIELD_HAVE_READ, true);
                hashOperations.put(haveReadRequest.getSenderUserNO() + RedisConfig.KEY_HAVE_READ, mailKey, true);
                //设置消息过期
                redisTemplate.expire(mailKey, 7, TimeUnit.DAYS);
                break;
            case 5:
                //群邮件

            case 26:
                //会议提醒
                chatUserMeetingService.updateByUserNoAndMeetingNo(haveReadRequest, false);
                return;

            default:
                System.err.println("服务端不支持的已读类型");
        }
        //查看响应的接受收者是否在线
        Channel receiveChannel = SessionUtil.getChannel(haveReadRequest.getReceiveUserNO());
        //如果在线,并且接收者不是自己,告诉接收者,对方已经读取.
        if (receiveChannel != null
                && !haveReadRequest.getReceiveUserNO().equals(haveReadRequest.getSenderUserNO())
                && !haveReadRequest.getChatMessageType().equals(GROUP_CHAT_TYPE)) {
            HaveReadResponse haveReadResponse = new HaveReadResponse(haveReadRequest.getSenderUserNO(), haveReadRequest.getReceiveUserNO(), haveReadRequest.getChatMessageNO());
            receiveChannel.writeAndFlush(encode(haveReadResponse));
        }
    }

    /**
     * @param packet
     * @return void
     * @description 判断是否消息数据包, 并响应
     * @author Murray Law
     * @date 2020/10/25 21:57
     */
    private void messageRequestHandle(Packet packet) {
        // 处理消息并缓存
        PrivateChatMessage privateChatMsgReq = ((PrivateChatMessage) packet);
        System.out.println(new Date() + ": 收到客户端的消息: " + privateChatMsgReq.getMsg());
        String msgKey = PrivateChatMessage.PRIVATE_MSG + privateChatMsgReq.getMsgNo();
        //缓存消息
        hashOperations.putAll(msgKey, parseObj2Map(privateChatMsgReq));
        //存未读
        hashOperations.put(privateChatMsgReq.getReceiverUserNo() + RedisConfig.KEY_HAVE_READ, msgKey, false);
        stringRedisTemplate.opsForValue().set(privateChatMsgReq.getSenderUserNo() + privateChatMsgReq.getReceiverUserNo(), privateChatMsgReq.getMsg());
        stringRedisTemplate.opsForValue().set(privateChatMsgReq.getReceiverUserNo() + privateChatMsgReq.getSenderUserNo(), privateChatMsgReq.getMsg());

//    todo       channel.writeAndFlush(ServerPacketCodeCompile.encode(new ServerReceivedResponse(chatMessageRequest.getChatMessageNO(),null,true)));
        //查询Session的在线列表有没有消息接收人并推送消息
        sendMessageToReceiver(privateChatMsgReq);
    }

    /**
     * @param channel
     * @param packet
     * @return void
     * @description 登录响应 2.0 判断并响应
     * @author Murray Law
     * @date 2020/10/25 17:47
     */
    private void loginRequestHandle(Channel channel, Packet packet) {

        //拿到消息发送方账户信息
        LoginRequest loginRequest = (LoginRequest) packet;
        //数据库查询数据
        ChatUser selectedChatUser = chatUserService.selectChatUserByNo(loginRequest.getChatUserNO());

        //数据库没有账户并且密码为123456则注册
        Date date = new Date();
        if (null == selectedChatUser && loginRequest.getPassword().equals("123456")) {
            //插入基本信息
            ChatUser chatUser = new ChatUser();
            chatUser.setChatUserNo(loginRequest.getChatUserNO());
            chatUser.setPassword(loginRequest.getPassword());
            chatUser.setCreateDate(date);
            chatUser.setUpdateDate(date);
            chatUserService.insert(chatUser);
            //插入详细信息
            ChatUserInfo chatUserInfo = new ChatUserInfo();
            chatUserInfo.setUserNo(loginRequest.getChatUserNO());
            chatUserInfo.setCompany("中国移动");
            chatUserInfo.setAvatarType(".jpg");
            chatUserInfo.setCreateTime(date);
            chatUserInfo.setUpdateTime(date);
            chatUserInfoService.insert(chatUserInfo);
            //写数据,响应登录反馈信息
            channel.writeAndFlush(encode(new LoginResponse(CHANGE_PASSWORD)));
            return;
        }
        //客户端跳转修改密码页面
        if (null != selectedChatUser && selectedChatUser.getPassword().equals("123456")) {
            //写数据,响应登录反馈信息
            channel.writeAndFlush(encode(new LoginResponse(CHANGE_PASSWORD)));
            return;
        }
        //校验密码
        if (null == selectedChatUser || !loginRequest.getPassword().equals(selectedChatUser.getPassword())) {
            //写数据,响应登录反馈信息
            channel.writeAndFlush(encode(new LoginResponse("账号或密码不正确，请重新输入")));
            return;
        }

        //密码正确后的操作
        String currentUserNo = loginRequest.getChatUserNO();
        //绑定Session
        SessionUtil.bindSession(new Session(currentUserNo), channel);
        //初始化响应体
        LoginResponse loginResponse = new LoginResponse(currentUserNo, selectedChatUser.getName(), selectedChatUser.getPassword(), selectedChatUser.getAge(), selectedChatUser.getBirthday(), selectedChatUser.getSignature(), SUCCESS_STATUS);

        //推送用户信息
        ChatUserInfoResponse loginUserInfoResponse = writeAndFlushUserInfo(channel, currentUserNo);
        //推送公司成员信息
        chatDepartmentService.selectByParent(loginUserInfoResponse.getCompany()).forEach(chatDepartment -> {
            DepartmentMemberResponse depMemberResponse = departmentMemberService.getDepartmentMemberResponse(currentUserNo, chatDepartment.getDepartmentNo(), chatDepartment.getName());
            channel.writeAndFlush(encode(depMemberResponse));
        });
        //推送好友列表
//        chatFriendsService.selectFriendsByChatUserNO(currentUserNo).forEach((chatFriendResponse -> {
//          writeAndFlushUserInfo(channel, chatFriendResponse.getFriendUserNo());
//          channel.writeAndFlush(encode(chatFriendResponse));
//        }));
        //推送群组信息
        channel.writeAndFlush(encode(chatGroupService.selectByUserNo(currentUserNo)));
        //获得未读缓存
        Map<String, Boolean> haveReadMap = hashOperations.entries(currentUserNo + RedisConfig.KEY_HAVE_READ);
        String privateMsgPattern = "^" + PrivateChatMessage.PRIVATE_MSG + ".*";
        String groupMsgPattern = "^" + GroupMsgRequest.GROUP_MSG + ".*";
        //遍历消息缓存,
        haveReadMap.forEach((chatMessageNO, haveRead) -> {
            //有未读私聊消息则推送
            if (Pattern.matches(privateMsgPattern, chatMessageNO) && !haveRead) {
                Map<String, Object> entries = hashOperations.entries(chatMessageNO);
                PrivateChatMessage privateChatMessage = objectMapper.convertValue(entries, PrivateChatMessage.class);
                //推送消息给接收人
                sendMessageToReceiver(privateChatMessage);
            }
            //有未读群聊消息则推送
            if (Pattern.matches(groupMsgPattern, chatMessageNO) && !haveRead) {
                Map<String, Object> entries = hashOperations.entries(chatMessageNO);
                GroupMsgRequest groupMsgRequest = objectMapper.convertValue(entries, GroupMsgRequest.class);
                //推送消息给接收人
                pushGroupMessage(channel, groupMsgRequest, currentUserNo);
            }
        });
        //写数据,响应登录反馈信息
        channel.writeAndFlush(encode(loginResponse));
        //错过的会议提醒
        List<ChatUserMeeting> chatUserMeetings = chatUserMeetingService.selectByUserNoAndIsAnswer(currentUserNo, false);
        chatUserMeetings.forEach(userMeeting -> {
            ChatMeeting chatMeeting = chatMeetingService.selectByMeetingNo(userMeeting.getMeetingNo()).get(0);
            long timeDiff = chatMeeting.getStartTime().getTime() - date.getTime();
            if ((timeDiff < 0)) {
                MeetingReminderResponse meetingReminderResponse = new MeetingReminderResponse();
                BeanUtils.copyProperties(chatMeeting, meetingReminderResponse);
                meetingReminderResponse.setMeetingRemindNo(chatMeeting.getMeetingNo());
                channel.writeAndFlush(encode(meetingReminderResponse));
            }
        });
    }

    /**
     * @param privateChatMessageRequest
     * @return void
     * @description 发送消息给接收者
     * @author Murray Law
     * @date 2020/10/25 23:21
     */
    private void sendMessageToReceiver(PrivateChatMessage privateChatMessageRequest) {
        // 4.将消息发送给消息接收方
        //如果接收者是登录状态
        Channel channel = SessionUtil.getChannel(privateChatMessageRequest.getReceiverUserNo());
        if (null != channel && SessionUtil.hasLogin(channel)) {
            PrivateChatMessage privateChatMessageResponse = new PrivateChatMessage(privateChatMessageRequest.getMsgNo(), privateChatMessageRequest.getSenderUserNo(), privateChatMessageRequest.getMsg(), privateChatMessageRequest.getReceiverUserNo(), privateChatMessageRequest.getIssueTime(), false, privateChatMessageRequest.getMsgType());
            privateChatMessageResponse.setMsgNo(privateChatMessageRequest.getMsgNo());
            // 1. 转换数据
            ByteBuf buffer = encode(privateChatMessageResponse);
            //2.推送消息给客户端
            channel.writeAndFlush(buffer);
        }
    }
}