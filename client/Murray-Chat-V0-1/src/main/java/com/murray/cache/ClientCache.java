package com.murray.cache;

import com.murray.dto.response.*;
import com.murray.entity.PrivateChatMessage;
import com.murray.utils.PictureManipulationUtil;
import com.murray.view.vo.frame.*;
import io.netty.channel.Channel;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Murray Law
 * @describe 客户端缓存
 * @createTime 2020/10/25
 */
public class ClientCache {

    public static Channel CHANNEL;
    //下载文件的路径
    public static String fileSavePath;
    //下载文件的路径
    public static final String AVATAR_FOLDER = System.getProperty("user.dir") + "\\avatar\\";
    //音频文件的路径
    public static final String AUDIO_FOLDER = System.getProperty("user.dir") + "\\audio\\";

    public static String fileSaveUUID;
    public static byte openPanelType = 1;

    public static LoginFrame loginFrame;
    public static ChatMainFrame CHAT_MAIN_FRAME;
    public static ChangePasswordFrame CHANGE_PWD_FRAME;

    //各类常用图标
    public static final ImageIcon FILE_ICON = PictureManipulationUtil.getImageIcon("v1icon/annex.png", 60, 60);
    public static final ImageIcon EXCEL_ICON = PictureManipulationUtil.getImageIcon("v1icon/excel.png", 60, 60);
    public static final ImageIcon PDF_ICON = PictureManipulationUtil.getImageIcon("v1icon/pdf.png", 60, 60);
    public static final ImageIcon PPT_ICON = PictureManipulationUtil.getImageIcon("v1icon/ppt.png", 60, 60);
    public static final ImageIcon WORD_ICON = PictureManipulationUtil.getImageIcon("v1icon/word.png", 60, 60);
    public static final ImageIcon ZIP_ICON = PictureManipulationUtil.getImageIcon("v1icon/zip.png", 60, 60);
    public static final ImageIcon MAIL_ICON = PictureManipulationUtil.getImageIcon("icon/mail.jpg", 60, 60);//todo
    //头像白底框
    public static final ImageIcon AVATAR_FRAME_WHITE = PictureManipulationUtil.getImageIcon("v1icon/avatarFrame.png", 50, 50);
    //头像蓝底框
    public static final ImageIcon AVATAR_FRAME_BLUE = PictureManipulationUtil.getImageIcon("v1icon/headSculpture.png", 40, 40);
    //移除成员
    public static final ImageIcon REMOVE_MEMBER_ICON = PictureManipulationUtil.getImageIcon("v1icon/removeGroupMember.png", 20, 20);

    //OA面板Map
    public static JTable meetingTable;

    //登录的用户
    public static ChatUserInfoResponse chatUserByLogIn;
    //保存在内存的消息
    public static final Map<String, PrivateChatMessage> PRIVATE_CHAT_MESSAGE_MAP = new ConcurrentHashMap<>();
    //群聊消息的内存Map
    public static final Map<String, GroupMsgResponse> GROUP_MSG_MAP = new ConcurrentHashMap<>();
    //上传到服务器的列表
    public static final List<String> uploadedToServerList = new ArrayList<>();
    //好友内存Map
    public static final Map<String, ChatFriendResponse> FRIEND_RESPONSE_MAP = new ConcurrentHashMap<>();
    //聊天用户信息Map
    public static final Map<String, ChatUserInfoResponse> CHAT_USER_INFO_RESPONSE_MAP = new ConcurrentHashMap<>();
    //群组信息Map
    public static final Map<String, GroupBasic> GROUP_BASIC_MAP = new ConcurrentHashMap<>();
    //群组信息@已读Map
    public static final Map<String, Map<String, Boolean>> GROUP_AT_HAVE_READ_MAP = new HashMap<>();
    //部门信息Map
    public static final Map<String, Map<String, ChatUserInfoResponse>> DEPARTMENT_MEMBER_RESPONSE_MAP = new ConcurrentHashMap<>();
    //会议提醒面板Map
    public static final Map<String, MeetingReminderFrame> MEETING_REMINDER_FRAME_MAP = new ConcurrentHashMap<>();
    //会议提醒面板Map
    public static MeetingTableResponse meetingTableResponse;


    //计算打开私聊的窗口,限制每个好友只能有一个窗口
    public static final Map<String, PrivateChatFrame> PRIVATE_CHAT_FRAME_MAP = new ConcurrentHashMap<>();


    //声音提醒
    public static AudioClip meetingAudioClip;

    static {
        try {
            meetingAudioClip = Applet.newAudioClip(new File(ClientCache.AUDIO_FOLDER + "meetingaudio.wav").toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //消息声音提醒
    public static AudioClip msgAudioClip;

    static {
        try {
            msgAudioClip = Applet.newAudioClip(new File(ClientCache.AUDIO_FOLDER + "msg.wav").toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //计算创建群聊的窗口,创建成功会关闭
    //public static final Map<String, CreateGroupFrame> CREATE_GROUP_FRAME_MAP = new ConcurrentHashMap<>();
    private static JTextArea getFillTextArea() {
        JTextArea textArea = new JTextArea(" ");
//        textArea.setOpaque(false);
        return textArea;
    }
}
