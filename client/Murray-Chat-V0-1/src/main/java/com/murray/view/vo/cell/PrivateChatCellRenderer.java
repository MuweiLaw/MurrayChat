package com.murray.view.vo.cell;

import com.murray.cache.ClientCache;
import com.murray.dto.request.HaveReadRequest;
import com.murray.entity.PrivateChatMessage;
import com.murray.utils.FLowLayoutUtil;
import com.murray.utils.FontUtil;
import com.murray.utils.PictureManipulationUtil;
import com.murray.utils.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import static com.murray.agreement.MessageType.PRIVATE_FILE_TYPE;
import static com.murray.cache.ClientCache.*;
import static com.murray.handler.ClientPacketCodeCompile.encode;
import static com.murray.utils.ColorUtil.OWN_MSG_BGC;
import static com.murray.utils.ColorUtil.UNREAD_COLOR;
import static com.murray.utils.DimensionUtil.*;
import static com.murray.utils.FontUtil.MICROSOFT_YA_HEI_16;
import static com.murray.utils.PictureManipulationUtil.getImageIcon;

public class PrivateChatCellRenderer extends JPanel {
    private JLabel profilePictureLabel, endFillLabel = new JLabel();
    public JProgressBar progressBar;
    public JPanel msgPanel;
    private PrivateChatMessage privateMsg;
    public JTextArea msgTextArea;

    public PrivateChatCellRenderer(PrivateChatMessage privateMsg) {
        this.privateMsg = privateMsg;

        switch (privateMsg.getMsgType()) {
            case 0:
                if (privateMsg.getSenderUserNo().equals(chatUserByLogIn.getChatUserNo())) {
                    setLayout(new FlowLayout(FlowLayout.RIGHT));
                    haveRead(privateMsg, JLabel.RIGHT);
                    getMsgR(privateMsg);
                    getHeadPortrait(privateMsg);
                } else {
                    getHeadPortrait(privateMsg);
                    getMsgL(privateMsg);
                    endFillLabel.setPreferredSize(DIM_230_40);
                    add(endFillLabel);
                    //setTime(privateMsg, JLabel.LEFT);
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                }
                break;
            case 2:
                if (privateMsg.getSenderUserNo().equals(chatUserByLogIn.getChatUserNo())) {
                    setLayout(new FlowLayout(FlowLayout.RIGHT));
                    haveDownloadR("上传中");
                    getFileR(privateMsg);
                    getHeadPortrait(privateMsg);
                } else {
                    getHeadPortrait(privateMsg);
                    getFileL(privateMsg);
                    haveDownloadL();
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                }
                break;
            case 4:
                if (privateMsg.getSenderUserNo().equals(chatUserByLogIn.getChatUserNo())) {
                    setLayout(new FlowLayout(FlowLayout.RIGHT));
                    haveRead(privateMsg, JLabel.RIGHT);
                    endFillLabel.setPreferredSize(DIM_260_40);
                    getMailR(privateMsg);
                    getHeadPortrait(privateMsg);
                } else {
                    getHeadPortrait(privateMsg);
                    getMailL(privateMsg);
                    endFillLabel.setPreferredSize(DIM_260_40);
                    add(endFillLabel);
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                }
        }
    }


    private void haveRead(PrivateChatMessage privateMsg, int alignment) {
        if (!privateMsg.getHaveRead()) {
            endFillLabel = new JLabel("未读", alignment);
        } else {
            endFillLabel = new JLabel("已读", alignment);
        }
        endFillLabel.setPreferredSize(DIM_230_40);
        endFillLabel.setFont(MICROSOFT_YA_HEI_16);
        endFillLabel.setForeground(UNREAD_COLOR);
        add(endFillLabel);
    }

    private void haveDownloadR( String labelName) {
        endFillLabel = new JLabel(labelName,JLabel.RIGHT);
        endFillLabel.setPreferredSize(DIM_255_40);
        endFillLabel.setFont(MICROSOFT_YA_HEI_16);
        endFillLabel.setForeground(Color.red);
        add(endFillLabel);
        if (!privateMsg.getHaveRead()) {
            if (ClientCache.uploadedToServerList.contains(privateMsg.getMsgNo())) {
                endFillLabel.setText("对方未下载");
            }
        } else {
            endFillLabel.setText("对方已下载");
        }
    }

    private void haveDownloadL() {
        endFillLabel = new JLabel();
        endFillLabel.setPreferredSize(DIM_255_40);
        endFillLabel.setFont(MICROSOFT_YA_HEI_16);
        endFillLabel.setForeground(Color.red);
        add(endFillLabel);
        if (!privateMsg.getHaveRead()) {
            endFillLabel.setText("未下载");
        } else {
            endFillLabel.setText("已下载");
        }
    }

//    private void setTime(PrivateChatMessage privateMsg, int alignment) {
//        //设置是否显示时间,目前设置消息间隔十分钟会显示时间
//        if (privateMsg.isShowTime()) {
//            JLabel dateLabel = new JLabel(DateUtil.getDateString((privateMsg.getIssueTime())), alignment);
//            dateLabel.setFont(new Font("楷体", Font.PLAIN, 14));
//            dateLabel.setPreferredSize(new Dimension(100, 50));
//            dateLabel.setForeground(Color.gray);
//            add(dateLabel);
//        }
//    }

    private void getMsgL(PrivateChatMessage privateMsg) {
        //设置消息内容
        msgTextArea = new JTextArea();
        msgTextArea.setEditable(false);
        msgTextArea.setColumns(25);
        msgTextArea.setLineWrap(true);
        msgTextArea.setText(privateMsg.getMsg());
        msgTextArea.setFont(MICROSOFT_YA_HEI_16);
        msgTextArea.setBackground(Color.white);
        msgTextArea.setMargin(FLowLayoutUtil.LEFT_MSG);
        JPanel jPanel = new JPanel();
//        jPanel.setBorder(createLineBorder(Color.lightGray, 1, true));
        jPanel.setBackground(Color.white);
        jPanel.add(msgTextArea);
        add(jPanel);
    }

    private void getMsgR(PrivateChatMessage privateMsg) {
        //设置消息内容
        msgTextArea = new JTextArea();
        msgTextArea.setEditable(false);
        msgTextArea.setColumns(25);
        msgTextArea.setLineWrap(true);
        msgTextArea.setText(privateMsg.getMsg());
        msgTextArea.setFont(MICROSOFT_YA_HEI_16);
        msgTextArea.setBackground(OWN_MSG_BGC);
        msgTextArea.setMargin(FLowLayoutUtil.RIGHT_MSG);//设置间距
        JPanel jPanel = new JPanel();
//        jPanel.setBorder(createLineBorder(Color.lightGray, 1, true));
        jPanel.setBackground(OWN_MSG_BGC);
        jPanel.add(msgTextArea);
        add(jPanel);
    }


    private void getMailL(PrivateChatMessage privateMsg) {
        String msg = privateMsg.getMsg();
        String mailTitle = StringUtil.getCharBetweenTwoChar(msg, "\\(", "\\)");
        String mailText = msg.substring(mailTitle.length() + 2);
        //设置背景版
        msgPanel = new JPanel(null);
        msgPanel.setPreferredSize(DIM_400_90);
        msgPanel.setBackground(OWN_MSG_BGC);
        //邮件标题
        JLabel mailTitleLabel = new JLabel(mailTitle);
        mailTitleLabel.setBounds(15, 15, 300, 30);
        mailTitleLabel.setBackground(Color.WHITE);
        mailTitleLabel.setFont(FontUtil.MICROSOFT_YA_HEI_B18);
        //邮件正文
        JLabel mailTextLabel = new JLabel(mailText);
        mailTextLabel.setBounds(15, 45, 300, 30);
        mailTextLabel.setForeground(Color.gray);
        mailTextLabel.setBackground(Color.WHITE);
        mailTextLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        //文件图标
        JLabel fileIconLabel = new JLabel(MAIL_ICON);
        fileIconLabel.setBounds(325, 15, 60, 60);
        msgPanel.add(fileIconLabel);
        msgPanel.add(mailTitleLabel);
        msgPanel.add(mailTextLabel);
        add(msgPanel);
    }

    private void getMailR(PrivateChatMessage privateMsg) {
        String msg = privateMsg.getMsg();
        String mailTitle = StringUtil.getCharBetweenTwoChar(msg, "\\(", "\\)");
        String mailText = msg.substring(mailTitle.length() + 2);
        //设置背景版
        msgPanel = new JPanel(null);
        msgPanel.setPreferredSize(DIM_400_90);
        msgPanel.setBackground(OWN_MSG_BGC);
        //邮件标题
        JLabel mailTitleLabel = new JLabel(mailTitle);
        mailTitleLabel.setBounds(90, 15, 300, 30);
        mailTitleLabel.setBackground(Color.WHITE);
        mailTitleLabel.setFont(FontUtil.MICROSOFT_YA_HEI_B18);
        //邮件正文
        JLabel mailTextLabel = new JLabel(mailText);
        mailTextLabel.setBounds(90, 45, 300, 30);
        mailTextLabel.setForeground(Color.gray);
        mailTextLabel.setBackground(Color.WHITE);
        mailTextLabel.setFont(FontUtil.MICROSOFT_YA_HEI_16);
        //文件图标
        JLabel fileIconLabel = new JLabel(MAIL_ICON);
        fileIconLabel.setBounds(15, 15, 60, 60);
        msgPanel.add(fileIconLabel);
        msgPanel.add(mailTitleLabel);
        msgPanel.add(mailTextLabel);
        add(msgPanel);
    }

    private void getFileL(PrivateChatMessage privateMsg) {
        String msg = privateMsg.getMsg();
        String fileName = msg.substring(0, msg.lastIndexOf("("));
        String fileType = StringUtil.getCharBetweenTwoChar(msg, "\\.", "\\(");
        String fileSize = StringUtil.getCharBetweenTwoChar(msg, "\\(", "\\)");
        //图标地址
        ImageIcon fileImageIcon = FILE_ICON;
        //设置背景版
        msgPanel = new JPanel(null);
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
        fileNameLabel.setBounds(15, 15, 300, 30);
        fileNameLabel.setBackground(Color.WHITE);
        fileNameLabel.setFont(FontUtil.MICROSOFT_YA_HEI_B18);
        //文件大小
        JLabel fileSizeLabel = new JLabel(fileSize);
        fileSizeLabel.setBounds(15, 45, 300, 30);
        fileSizeLabel.setForeground(Color.lightGray);
        fileSizeLabel.setBackground(Color.WHITE);
        fileSizeLabel.setFont(FontUtil.MICROSOFT_YA_HEI_14);
        //文件图标
        JLabel fileIconLabel = new JLabel(fileImageIcon);
        fileIconLabel.setBounds(325, 15, 60, 60);
        msgPanel.add(fileNameLabel);
        msgPanel.add(fileSizeLabel);
        msgPanel.add(fileIconLabel);
        addProgressBar(Color.WHITE);
        //鼠标监听器,下载用
        addMsgPopupMenuMouseListener(msgPanel, privateMsg);
        add(msgPanel);
    }

    private void getFileR(PrivateChatMessage privateMsg) {
        String msg = privateMsg.getMsg();
        String fileName = msg.substring(0, msg.lastIndexOf("("));
        String fileType = StringUtil.getCharBetweenTwoChar(msg, "\\.", "\\(");
        String fileSize = StringUtil.getCharBetweenTwoChar(msg, "\\(", "\\)");
        //图标地址
        ImageIcon fileImageIcon = FILE_ICON;
        //设置背景版
        msgPanel = new JPanel(null);
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
        addMsgPopupMenuMouseListener(msgPanel, privateMsg);
        add(msgPanel);
    }

    /**
     * @return void
     * @description 添加进度条
     * @author Murray Law
     * @date 2020/11/6 11:37
     */
    private void addProgressBar(Color FGColor) {
        // 创建一个进度条
        progressBar = new JProgressBar();
        // 设置进度的 最小值 和 最大值
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        // 设置当前进度值
        progressBar.setValue(100);
        progressBar.setBackground(Color.GRAY);
        progressBar.setForeground(FGColor);
        progressBar.setBorder(null);
        progressBar.setBorderPainted(false);//不打印边框
        progressBar.setBounds(0, 80, 400, 5);
        msgPanel.add(progressBar);
    }

    private void getHeadPortrait(PrivateChatMessage privateMsg) {
        //设置头像资料
        ImageIcon profilePicture = PictureManipulationUtil.getUserAvatar(privateMsg.getSenderUserNo(), 50, 50);
        profilePictureLabel = new JLabel(profilePicture, JLabel.CENTER);
        profilePictureLabel.setPreferredSize(DIM_70_50);
        add(profilePictureLabel);
    }


    public void removeHaveRead() {
        if (privateMsg.getMsgType().equals(PRIVATE_FILE_TYPE)) {
            endFillLabel.setText("已下载");
        } else {
            endFillLabel.setText("已读");
        }
    }

    /**
     * @return void
     * @description 上传失败
     * @author Murray Law
     * @date 2020/11/6 9:03
     */
    public void uploadFailed() {
        endFillLabel.setText(null);
        endFillLabel.setIcon(getImageIcon("icon/gantanhao.jpg", 20, 20));
    }

    public void uploadSuccess() {
        endFillLabel.setText("对方未下载");
    }

    /**
     * @param
     * @return void
     * @description 添加鼠标监听器
     * @author Murray Law
     * @date 2020/11/5 8:53
     */
    private void addMsgPopupMenuMouseListener(Component component, PrivateChatMessage privateMsg) {
        component.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // 鼠标释放

                // 如果是鼠标右键，则显示弹出菜单
                if (e.isMetaDown()) {
                    showMsgPopupMenu(e.getComponent(), e.getX(), e.getY(), privateMsg);
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
    private void showMsgPopupMenu(Component invoker, int x, int y, PrivateChatMessage privateMsg) {
        // 创建 弹出菜单 对象
        JPopupMenu popupMenu = new JPopupMenu();

        // 创建 一级菜单
        JMenuItem downloadMenuItem = new JMenuItem("下载");


        // 添加 一级菜单 到 弹出菜单
        popupMenu.add(downloadMenuItem);

        // 添加菜单项的点击监听器
        downloadMenuItem.addActionListener(e -> showFileSaveDialog(invoker, privateMsg));
        // 在指定位置显示弹出菜单
        popupMenu.show(invoker, x, y);
    }


    /**
     * @param parent
     * @param privateMsg
     * @return void
     * @description 显示保存文件选择器
     * @author Murray Law
     * @date 2020/11/5 10:35
     */
    private void showFileSaveDialog(Component parent, PrivateChatMessage privateMsg) {
        //获得文件名称
        String fileName = privateMsg.getMsg().substring(0, privateMsg.getMsg().lastIndexOf("("));

        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();
        // 设置打开文件选择框后默认输入的文件名
        fileChooser.setSelectedFile(new File(fileName));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showSaveDialog(parent);
        // 如果点击了"保存"
        if (result == JFileChooser.APPROVE_OPTION) {
            String senderNO = privateMsg.getSenderUserNo();
            //发出下载指令
            CHANNEL.writeAndFlush(encode(new HaveReadRequest(chatUserByLogIn.getChatUserNo(), senderNO, privateMsg.getMsgNo(), PRIVATE_FILE_TYPE)));
            // 获取选择的保存路径
            File file = fileChooser.getSelectedFile();
            fileSavePath = file.getAbsolutePath();
            fileSaveUUID = privateMsg.getMsgNo();
            //文件接收方去除未下载提示
            //不是本人上传的,才能更改未下载状态
            if (!chatUserByLogIn.getChatUserNo().equals(senderNO)) {
                PRIVATE_CHAT_MESSAGE_MAP.get(privateMsg.getMsgNo()).setHaveRead(true);
                removeHaveRead();
//                refreshMSGPanel();
            }
        }
    }

    public JLabel getProfilePictureLabel() {
        return profilePictureLabel;
    }

    public PrivateChatMessage getPrivateChatMessage() {
        return privateMsg;
    }
}
