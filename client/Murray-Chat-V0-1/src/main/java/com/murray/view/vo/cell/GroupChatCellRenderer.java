package com.murray.view.vo.cell;

import com.murray.dto.request.HaveReadRequest;
import com.murray.dto.response.ChatUserInfoResponse;
import com.murray.dto.response.GroupMsgResponse;
import com.murray.utils.*;
import com.murray.view.dto.GroupChatNotification;
import com.murray.view.vo.frame.UserInfoFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Map;

import static com.murray.agreement.MessageType.GROUP_FILE_TYPE;
import static com.murray.cache.ClientCache.*;
import static com.murray.handler.ClientPacketCodeCompile.encode;
import static com.murray.utils.ColorUtil.*;
import static com.murray.utils.DimensionUtil.*;
import static com.murray.utils.FontUtil.MICROSOFT_YA_HEI_14;
import static com.murray.utils.FontUtil.MICROSOFT_YA_HEI_16;

//import static com.murray.view.vo.panel.MSGPanel.refreshMSGPanel;

public class GroupChatCellRenderer extends JPanel {
    private JLabel endFillLabel = new JLabel("", JLabel.RIGHT);
    public JProgressBar progressBar;
    public final JPanel msgPanel = new JPanel();
    private JLabel profilePictureLabel;
    private GroupMsgResponse groupMsg;
    private ChatUserInfoResponse userInfo;

    public GroupChatCellRenderer(GroupMsgResponse groupMsg) {
        this.groupMsg = groupMsg;
        this.userInfo = CHAT_USER_INFO_RESPONSE_MAP.get(groupMsg.getSenderUserNo());
        switch (groupMsg.getMsgType()) {
            case 1:
                if (groupMsg.getSenderUserNo().equals(chatUserByLogIn.getChatUserNo())) {
                    setLayout(new FlowLayout(FlowLayout.RIGHT));
                    endFillLabel.setPreferredSize(DIM_260_40);
                    add(endFillLabel);
                    atHaveRead();
                    getMsgR();
                    getHeadPortrait();
                } else {
                    getHeadPortrait();
                    getMsgL();
                    endFillLabel.setPreferredSize(DIM_260_40);
                    add(endFillLabel);
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                }
                break;
            case 3:
                if (groupMsg.getSenderUserNo().equals(chatUserByLogIn.getChatUserNo())) {
                    setLayout(new FlowLayout(FlowLayout.RIGHT));
                    endFillLabel.setPreferredSize(DIM_255_40);
                    add(endFillLabel);
                    getFileR();
                    getHeadPortrait();
                }  else {
                    getHeadPortrait();
                    getFileL();
                    endFillLabel.setPreferredSize(DIM_255_40);
                    add(endFillLabel);
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                }
        }
    }

    /**
     * @param
     * @return void
     * @description 被@的已读
     * @author Murray Law
     * @date 2020/12/19 23:03
     */
    public void atHaveRead() {
        if (GROUP_AT_HAVE_READ_MAP.containsKey(groupMsg.getGroupMsgNo())) {
            endFillLabel.setFont(MICROSOFT_YA_HEI_16);
            endFillLabel.setForeground(UNREAD_COLOR);
            Map<String, Boolean> atHaveReadMap = GROUP_AT_HAVE_READ_MAP.get(groupMsg.getGroupMsgNo());
            if (atHaveReadMap==null){ endFillLabel.setText("全部未读");return;}//为空就直接跳过
            int haveReadSum = 0;
            for (Map.Entry<String, Boolean> entry : atHaveReadMap.entrySet()) {
                if (entry.getValue()) {
                    haveReadSum += 1;
                }
            }

            endFillLabel.setText(haveReadSum + "/" + atHaveReadMap.size() + "已读");

        }

    }


    private void setTime(GroupChatNotification notification, int alignment) {
        //设置是否显示时间,目前设置消息间隔十分钟会显示时间
        if (notification.isShowTime()) {
            JLabel dateLabel = new JLabel(DateUtil.getDateString((notification.getIssueTime())), alignment);
            dateLabel.setFont(new Font("楷体", Font.PLAIN, 14));
            dateLabel.setPreferredSize(new Dimension(100, 50));
            dateLabel.setForeground(Color.gray);
            add(dateLabel);
        }
    }

    private void getMsgL() {

        msgPanel.setLayout(new GridBagLayout());
        JLabel nameLabel = new JLabel(userInfo.getName(), JLabel.LEFT);
        addCellMouseAdapter(nameLabel);
        nameLabel.setFont(MICROSOFT_YA_HEI_16);
        nameLabel.setPreferredSize(DIM_115_40);
        //设置消息内容
        JTextArea msgTextArea = new JTextArea();
        msgTextArea.setEditable(false);
        msgTextArea.setColumns(25);
        msgTextArea.setLineWrap(true);
        msgTextArea.setText(groupMsg.getMessage());
        msgTextArea.setFont(MICROSOFT_YA_HEI_16);
        msgTextArea.setBackground(Color.white);
        msgTextArea.setMargin(new Insets(10, 10, 10, 10));
        msgPanel.add(nameLabel, GridBagUtil.wrapBoth());
        msgPanel.add(msgTextArea, GridBagUtil.wrap());
        add(msgPanel);
    }

    private void getMsgR() {
        msgPanel.setLayout(new GridBagLayout());
        JLabel nameLabel = new JLabel(userInfo.getName(), JLabel.RIGHT);
        addCellMouseAdapter(nameLabel);
        nameLabel.setFont(MICROSOFT_YA_HEI_16);
        nameLabel.setPreferredSize(DIM_115_40);
        //设置消息内容
        JTextArea msgTextArea = new JTextArea();
        msgTextArea.setEditable(false);
        msgTextArea.setColumns(25);
        msgTextArea.setLineWrap(true);
        msgTextArea.setText(groupMsg.getMessage());
        msgTextArea.setFont(MICROSOFT_YA_HEI_16);
        msgTextArea.setBackground(OWN_MSG_BGC);
        msgTextArea.setMargin(new Insets(10, 10, 10, 10));
        msgPanel.add(nameLabel, GridBagUtil.wrapBoth());
        msgPanel.add(msgTextArea, GridBagUtil.wrap());
        add(msgPanel);
    }

    private void getFileL() {
        String msg = groupMsg.getMessage();
        String fileName = msg.substring(0, msg.lastIndexOf("("));
        String fileType = StringUtil.getCharBetweenTwoChar(msg, "\\.", "\\(");
        String fileSize = StringUtil.getCharBetweenTwoChar(msg, "\\(", "\\)");
        //图标地址
        ImageIcon fileImageIcon = FILE_ICON;
        //设置背景版
        JPanel groupMsgPanel = new JPanel(GridBagUtil.getGridBagLayout());
        JLabel nameLabel = new JLabel(userInfo.getName(), JLabel.LEFT);
        addCellMouseAdapter(nameLabel);
        nameLabel.setFont(MICROSOFT_YA_HEI_16);
        nameLabel.setPreferredSize(DIM_115_40);
        msgPanel.setLayout(null);
        msgPanel.setPreferredSize(DIM_400_90);
        msgPanel.setBackground(Color.WHITE);

        switch (fileType) {
            case "pdf":
            case "PDF":
                fileImageIcon = PDF_ICON;
                break;
            case "zip":
            case "ZIP":
                fileImageIcon = ZIP_ICON;
                break;
            case "jpeg":
                break;
            case "JPEG":
                break;
            case "jpg":
                break;
            case "JPG":
                break;
            case "png":
                break;
            case "PNG":
                break;
            case "xls":
            case "xlsx":
                fileImageIcon = EXCEL_ICON;
                break;
            case "doc":
            case "docx":
                fileImageIcon = WORD_ICON;
                break;
            case "ppt":
            case "pptx":
                fileImageIcon = PPT_ICON;
                break;


        }
        //文件名称
        JLabel fileNameLabel = new JLabel(fileName);
        fileNameLabel.setBounds(90, 15, 300, 30);
        fileNameLabel.setFont(FontUtil.MICROSOFT_YA_HEI_B18);
        //文件大小
        JLabel fileSizeLabel = new JLabel(fileSize);
        fileSizeLabel.setBounds(90, 45, 300, 30);
        fileSizeLabel.setForeground(Color.gray);
        fileSizeLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        //文件图标
        JLabel fileIconLabel = new JLabel(fileImageIcon);
        fileIconLabel.setBounds(15, 15, 60, 60);
        msgPanel.add(fileIconLabel);
        msgPanel.add(fileNameLabel);
        msgPanel.add(fileSizeLabel);
        addProgressBar(Color.WHITE);
        //鼠标监听器,下载用
        addMsgPopupMenuMouseListener(msgPanel);
        groupMsgPanel.add(nameLabel,GridBagUtil.wrapBoth());
        groupMsgPanel.add(msgPanel,GridBagUtil.wrap());
        add(groupMsgPanel);
    }

    private void getFileR() {
        String msg = groupMsg.getMessage();
        String fileName = msg.substring(0, msg.lastIndexOf("("));
        String fileType = StringUtil.getCharBetweenTwoChar(msg, "\\.", "\\(");
        String fileSize = StringUtil.getCharBetweenTwoChar(msg, "\\(", "\\)");
        //图标地址
        ImageIcon fileImageIcon = FILE_ICON;
        //设置背景版
        JPanel groupMsgPanel = new JPanel(GridBagUtil.getGridBagLayout());
        JLabel nameLabel = new JLabel(userInfo.getName(), JLabel.RIGHT);
        addCellMouseAdapter(nameLabel);
        nameLabel.setFont(MICROSOFT_YA_HEI_16);
        nameLabel.setPreferredSize(DIM_115_40);
        msgPanel.setLayout(null);
        msgPanel.setPreferredSize(DIM_400_90);
        msgPanel.setBackground(OWN_MSG_BGC);

        switch (fileType) {
            case "pdf":
            case "PDF":
                fileImageIcon = PDF_ICON;
                break;
            case "zip":
            case "ZIP":
                fileImageIcon = ZIP_ICON;
                break;
            case "jpeg":
                break;
            case "JPEG":
                break;
            case "jpg":
                break;
            case "JPG":
                break;
            case "png":
                break;
            case "PNG":
                break;
            case "xls":
            case "xlsx":
                fileImageIcon = EXCEL_ICON;
                break;
            case "doc":
            case "docx":
                fileImageIcon = WORD_ICON;
                break;
            case "ppt":
            case "pptx":
                fileImageIcon = PPT_ICON;
                break;


        }
        //文件名称
        JLabel fileNameLabel = new JLabel(fileName);
        fileNameLabel.setBounds(90, 15, 300, 30);
        fileNameLabel.setBackground(Color.WHITE);
        fileNameLabel.setFont(FontUtil.MICROSOFT_YA_HEI_B18);
        //文件大小
        JLabel fileSizeLabel = new JLabel(fileSize);
        fileSizeLabel.setBounds(90, 45, 300, 30);
        fileSizeLabel.setForeground(Color.gray);
        fileSizeLabel.setBackground(Color.WHITE);
        fileSizeLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        //文件图标
        JLabel fileIconLabel = new JLabel(fileImageIcon);
        fileIconLabel.setBounds(15, 15, 60, 60);
        msgPanel.add(fileIconLabel);
        msgPanel.add(fileNameLabel);
        msgPanel.add(fileSizeLabel);
        addProgressBar(OWN_MSG_BGC);
        //鼠标监听器,下载用
        addMsgPopupMenuMouseListener(msgPanel);
        groupMsgPanel.add(nameLabel,GridBagUtil.wrapBoth());
        groupMsgPanel.add(msgPanel,GridBagUtil.wrap());
        add(groupMsgPanel);
    }

    /**
     * @return void
     * @description 添加进度条
     * @author Murray Law
     * @date 2020/11/6 11:37
     */
    private void addProgressBar(Color fGColor) {
        // 创建一个进度条
        progressBar = new JProgressBar();
        // 设置进度的 最小值 和 最大值
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        // 设置当前进度值
        progressBar.setValue(100);
        progressBar.setBackground(Color.white);
        progressBar.setForeground(fGColor);
        progressBar.setBorder(null);
        progressBar.setBorderPainted(false);//不打印边框
        progressBar.setBounds(0, 80, 400, 5);
        msgPanel.add(progressBar);
    }

    private void getHeadPortrait() {
        //设置头像资料
        ImageIcon profilePicture = PictureManipulationUtil.getUserAvatar(groupMsg.getSenderUserNo(), 50, 50);
        profilePictureLabel = new JLabel(profilePicture, JLabel.CENTER);
        profilePictureLabel.setPreferredSize(DIM_70_50);
        addCellMouseAdapter(profilePictureLabel);
        add(profilePictureLabel);
    }


    /**
     * @param
     * @return void
     * @description 添加鼠标监听器
     * @author Murray Law
     * @date 2020/11/5 8:53
     */
    private void addMsgPopupMenuMouseListener(Component component) {
        component.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // 鼠标释放
                // 如果是鼠标右键，则显示弹出菜单
                if (e.isMetaDown()) {
                    showMsgPopupMenu(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }


    /**
     * @param invoker
     * @param x
     * @param y
     * @return void
     * @description 显示弹出菜单
     * @author Murray Law
     * @date 2020/11/5 8:48
     */
    private void showMsgPopupMenu(Component invoker, int x, int y) {
        // 创建 弹出菜单 对象
        JPopupMenu popupMenu = new JPopupMenu();

        // 创建 一级菜单
        JMenuItem downloadMenuItem = new JMenuItem("下载");


        // 添加 一级菜单 到 弹出菜单
        popupMenu.add(downloadMenuItem);

        // 添加菜单项的点击监听器
        downloadMenuItem.addActionListener(e -> showFileSaveDialog(invoker));
        // 在指定位置显示弹出菜单
        popupMenu.show(invoker, x, y);
    }


    /**
     * @param parent
     * @return void
     * @description 显示保存文件选择器
     * @author Murray Law
     * @date 2020/11/5 10:35
     */
    private void showFileSaveDialog(Component parent) {
        //获得文件名称
        String fileName = groupMsg.getMessage().substring(0, groupMsg.getMessage().lastIndexOf("("));

        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();
        // 设置打开文件选择框后默认输入的文件名
        fileChooser.setSelectedFile(new File(fileName));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showSaveDialog(parent);
        // 如果点击了"保存"
        if (result == JFileChooser.APPROVE_OPTION) {
            String senderNO = groupMsg.getGroupNo();
            //发出下载指令
            CHANNEL.writeAndFlush(encode(new HaveReadRequest(chatUserByLogIn.getChatUserNo(), senderNO, groupMsg.getGroupMsgNo(), GROUP_FILE_TYPE)));
            // 获取选择的保存路径
            File file = fileChooser.getSelectedFile();
            fileSavePath = file.getAbsolutePath();
            fileSaveUUID = groupMsg.getGroupMsgNo();
        }
    }

    public JLabel getProfilePictureLabel() {
        return profilePictureLabel;
    }

    private void addCellMouseAdapter(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!userInfo.getChatUserNo().equals(chatUserByLogIn.getChatUserNo())) {
                    if (label.getText() != null) {
                        MyLabel atLabel = new MyLabel("@" + userInfo.getName(), groupMsg.getSenderUserNo());
                        atLabel.setForeground(ColorUtil.AT_COLOR);
                        atLabel.setFont(MICROSOFT_YA_HEI_14);
                        CHAT_MAIN_FRAME.getTextPane().insertComponent(atLabel);
                    } else {
                        UserInfoFrame userInfoFrame = new UserInfoFrame(userInfo.getChatUserNo());
                        Point locationOnScreen = profilePictureLabel.getLocationOnScreen();
                        userInfoFrame.setLocation((int) locationOnScreen.getX() - userInfoFrame.getWidth() - 10, (int) locationOnScreen.getY() - userInfoFrame.getHeight() / 2);
                        userInfoFrame.addMsgButton();
                        userInfoFrame.setVisible(true);
                    }
                } else if (label.getText() == null) {
                    UserInfoFrame userInfoFrame = new UserInfoFrame(userInfo.getChatUserNo());
                    Point locationOnScreen = profilePictureLabel.getLocationOnScreen();
                    userInfoFrame.setLocation((int) locationOnScreen.getX() - userInfoFrame.getWidth() - 10, (int) locationOnScreen.getY() - userInfoFrame.getHeight() / 2);
                    userInfoFrame.setVisible(true);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (label.getText() != null && !userInfo.getChatUserNo().equals(chatUserByLogIn.getChatUserNo())) {
                    label.setForeground(ColorUtil.MAIN_LEFT_COLOR);
                    label.setText(label.getText() + "@");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (label.getText() != null && !userInfo.getChatUserNo().equals(chatUserByLogIn.getChatUserNo())) {
                    label.setForeground(null);
                    label.setText(userInfo.getName());
                }
            }
        });
    }

}
