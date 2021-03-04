package com.murray.view.vo.frame;

import com.murray.entity.FilePacket;
import com.murray.entity.PrivateChatMessage;
import com.murray.utils.FontUtil;
import com.murray.utils.ModuleUtil;
import com.murray.view.vo.panel.PrivateMSGPanel;
import com.murray.view.vo.panel.RichTextPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static com.murray.agreement.MessageType.PRIVATE_FILE_TYPE;
import static com.murray.agreement.MessageType.PRIVATE_MAIL_TYPE;
import static com.murray.cache.ClientCache.*;
import static com.murray.handler.ClientPacketCodeCompile.encode;
import static com.murray.utils.JFameUtil.eventHandle;
import static com.murray.utils.PictureManipulationUtil.getImageIcon;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

/**
 * @author Murray Law
 * @describe 私聊窗口
 * @createTime 2020/10/28
 */
public class PrivateChatFrame extends JFrame {
    private String counterpartName;
    public String counterpartChatNo;

    private SendFileListFrame sendFileListFrame;

    public PrivateMSGPanel privateMSGPanel = new PrivateMSGPanel();
    private JButton sendButton, expressionBtn, fileBtn, closeButton, exitButton, mailBtn;
    private ImageIcon expressionIcon, fileIcon, rightPanelBGI, headBGI, showImage, mailIcon;
    private JLabel headBackgroundImage, rightBackgroundLabel, showImageLabel;
    public JPanel mainPane, headPanel, rightPanel, footPanel, centerPanel;

    public PrivateChatFrame(String counterpartName, String counterpartChatNo) {
        /**try { // 使用Windows的界面风格
         UIManager
         .setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
         } catch (Exception e) {
         e.printStackTrace();
         }*/
        this.counterpartName = counterpartName;
        this.counterpartChatNo = counterpartChatNo;
        setUndecorated(true);

        //全局面板容器
        mainPane = new JPanel();
        //全局布局
        BorderLayout layout = new BorderLayout();

        headPanel = new JPanel();    //上层panel，
        rightPanel = new JPanel(); //右边panel
        centerPanel = new JPanel(); //中间panel

        footPanel = new JPanel();    //下层panel

        setSize(790, 670);
//        setLocationRelativeTo(null);
        setLocation(500, 210);
        //关闭方式
//        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setContentPane(mainPane);
        setLayout(layout);


        //设置头部布局
        setTheHeadLayout(counterpartName, headPanel);
        //设置中部布局
        setTheMiddleLayout(centerPanel);

        //设置右部布局
        setTheRightLayout(rightPanel);

        //设置底部布局
        setBottomLayout(footPanel);

        //设置顶层布局
        mainPane.add(headPanel, "North");
        mainPane.add(rightPanel, "East");
        mainPane.add(footPanel, "South");
        mainPane.add(centerPanel, "Center");


//        聊天信息输入框的监听回车按钮事件
//        msgTextArea.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
//                }
//            }
//        });
        //窗口显示
        setVisible(true);
        //设置可拖动的面板
        eventHandle(headPanel, PrivateChatFrame.this);
    }

    private void setTheRightLayout(JPanel rightPanel) {
        rightPanel.setBackground(Color.white);
        rightPanel.setPreferredSize(new Dimension(200, 0));
        rightPanel.setLayout(null);

        rightPanelBGI = getImageIcon("images/privateChatRightBGI.jpg", 200, 440);

        //设置图片缩略大小，并面板布局中
        rightBackgroundLabel = new JLabel(rightPanelBGI);
        rightBackgroundLabel.setBounds(0, 0, 200, 440);
        //分层
        rightPanel.add(rightBackgroundLabel, new Integer(Integer.MIN_VALUE));
        rightPanel.setOpaque(false);
    }

    private void setTheHeadLayout(String counterpartName, JPanel headPanel) {
        //退出按钮
        exitButton = new JButton("X");
        exitButton.setForeground(Color.white);
        exitButton.setBounds(765, 0, 25, 25);
        exitButton.setForeground(Color.gray);
        exitButton.setBorder(null);
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "确定关闭聊天界面?", "提示",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    PRIVATE_CHAT_FRAME_MAP.remove(counterpartChatNo);
                    dispose();
                    if (null != sendFileListFrame) {
                        sendFileListFrame.dispose();
                    }
                }
            }
        });
        headPanel.add(exitButton);
        headPanel.setLayout(null);
        headBackgroundImage = new JLabel();
        headBGI = getImageIcon("images/privateChatBGI.jpg", 790, 150);
        //设置图片缩略大小，并面板布局中
        headBackgroundImage = new JLabel(headBGI);
        headBackgroundImage.setText(counterpartName);
        headBackgroundImage.setHorizontalTextPosition(JLabel.CENTER);
        headBackgroundImage.setVerticalTextPosition(JLabel.CENTER);
        headBackgroundImage.setBounds(0, 0, 790, 50);
        headBackgroundImage.setFont(FontUtil.MICROSOFT_YA_HEI_20);
        headPanel.add(headBackgroundImage, new Integer(Integer.MIN_VALUE));
        headPanel.setPreferredSize(new Dimension(0, 50));
    }

    private void setTheMiddleLayout(JPanel centerPanel) {
        centerPanel.setPreferredSize(new Dimension(590, 450));
        centerPanel.setBackground(Color.white);
        centerPanel.setBorder(null);
        privateMSGPanel.initPrivateChatPanel(counterpartChatNo);
        centerPanel.add(privateMSGPanel.getPrivateMSGPanel());

    }

    private void setBottomLayout(JPanel footPanel) {
        int btnXBelow = 200;
        footPanel.setLayout(null);
        footPanel.setPreferredSize(new Dimension(0, 180));
        footPanel.setBackground(Color.white);
        //设置表情
        expressionIcon = getImageIcon("icon/expression.jpg", 40, 40);
        expressionBtn = new JButton();
        expressionBtn.setSize(expressionIcon.getImage().getWidth(null), expressionIcon.getImage().getHeight(null));
        expressionBtn.setIcon(expressionIcon);
        expressionBtn.setBounds(5, 5, 40, 40);
        expressionBtn.setBorderPainted(false);//不打印边框
        expressionBtn.setText(null);//除去按钮的默认名称
        expressionBtn.setContentAreaFilled(false);//除去默认的背景填充
        //设置发送文件
        fileIcon = getImageIcon("icon/file.jpg", 35, 35);
        fileBtn = new JButton();
        fileBtn.setSize(fileIcon.getImage().getWidth(null), fileIcon.getImage().getHeight(null));
        fileBtn.setIcon(fileIcon);
        fileBtn.setBounds(55, 5, 35, 35);
        fileBtn.setBorderPainted(false);//不打印边框
        fileBtn.setText(null);//除去按钮的默认名称
        fileBtn.setContentAreaFilled(false);//除去默认的背景填充

        //关闭按钮
        closeButton = new JButton("关闭");
//        closeButton.setPreferredSize(new Dimension(60, 40));
        closeButton.setBounds(btnXBelow, 125, 110, 40);
        closeButton.setBackground(new Color(240, 240, 240));
        closeButton.setBorder(null);
        //发送按钮
        sendButton = new JButton("发送");
        sendButton.setBounds(btnXBelow + 130, 125, 110, 40);
        sendButton.setBackground(new Color(200, 200, 200));
        sendButton.setBorder(null);

        //富文本输入框
        RichTextPanel richTextPanel = new RichTextPanel(footPanel,null, 10, 50, 560, 70);

        //设置发送邮件
        mailIcon = getImageIcon("icon/mail.jpg", 35, 35);
        mailBtn = new JButton("发送邮件");
//        mailBtn.setSize(mailIcon.getImage().getWidth(null), mailIcon.getImage().getHeight(null));
//        mailBtn.setIcon(mailIcon);
        mailBtn.setBounds(btnXBelow + 260, 125, 110, 40);
        mailBtn.setBackground(new Color(200, 200, 200));
        mailBtn.setBorder(null);
//        mailBtn.setBorderPainted(false);//不打印边框
//        mailBtn.setText(null);//除去按钮的默认名称
//        mailBtn.setContentAreaFilled(false);//除去默认的背景填充
        //发送按钮的右边图片
        showImage = getImageIcon("images/fefaultAvatar.jpg", 180, 180);
        showImageLabel = new JLabel();
        showImageLabel.setSize(showImage.getImage().getWidth(null), expressionIcon.getImage().getHeight(null));
        showImageLabel.setIcon(showImage);
        showImageLabel.setBounds(580, 0, 180, 180);

        footPanel.add(expressionBtn);
        footPanel.add(fileBtn);
        footPanel.add(mailBtn);
        footPanel.add(sendButton);
        footPanel.add(closeButton);
        //文件按钮
        fileBtn.addActionListener(e -> {  //按钮点击事件
            if (null != sendFileListFrame) {
                sendFileListFrame.dispose();
                sendFileListFrame = null;
            }
            sendFileListFrame = new SendFileListFrame(PrivateChatFrame.this);
        });
        //邮件按钮
        mailBtn.addActionListener(e -> sendEmail(richTextPanel));

        //发送按钮的监听事件
        sendButton.addActionListener(e -> {
            //发送文件消息
            for (String filePath : richTextPanel.getFilePaths()) {
                File file = new File(filePath);
                String fileSize = "(" + (double) (file.length() / 1024 / 1024) + "MB)";

                FilePacket filePacket = new FilePacket();
                filePacket.setFile(file);
                //创建文件发送请求对象
                PrivateChatMessage privateChatMessage = new PrivateChatMessage(chatUserByLogIn.getChatUserNo(), file.getName() + fileSize, counterpartChatNo, new Date(), false, PRIVATE_FILE_TYPE);
                filePacket.setFileNo(privateChatMessage.getMsgNo());
                filePacket.setFileName(file.getName());
                //将自己的消息保存本地内存
                PRIVATE_CHAT_MESSAGE_MAP.put(privateChatMessage.getMsgNo(), privateChatMessage);
                privateMSGPanel.addPrivateChatCell(privateChatMessage);
                CHANNEL.writeAndFlush(encode(privateChatMessage));
                CHANNEL.writeAndFlush(encode(filePacket));
            }
            String message = richTextPanel.getPaneText();
            if (!message.isEmpty()&&!message.equals(" ")) {
                richTextPanel.setPaneText("");
                //创建消息对象
                PrivateChatMessage privateChatMessage = new PrivateChatMessage(chatUserByLogIn.getChatUserNo(), message, counterpartChatNo, new Date(), false, (byte) 0);
                //将自己的消息保存本地内存
                PRIVATE_CHAT_MESSAGE_MAP.put(privateChatMessage.getMsgNo(), privateChatMessage);
                CHANNEL.writeAndFlush(encode(privateChatMessage));
                privateMSGPanel.addPrivateChatCell(privateChatMessage);
                CHAT_MAIN_FRAME.refreshMsgScrollPanel();
            }
        });

        closeButton.addActionListener(e -> {
            setExtendedState(ICONIFIED);//窗口最小化
        });
    }

    private void sendEmail(RichTextPanel richTextPanel) {
        //获取对方的邮箱
        String mail = CHAT_USER_INFO_RESPONSE_MAP.get(counterpartChatNo).getMail();
        if (mail.isEmpty()) {
            ModuleUtil.showDialog("提示", "对方没有填写邮箱信息", INFORMATION_MESSAGE);
            return;
        }
        String mailSender = "muweilaw@qq.com";
        String title = ModuleUtil.showInputDialog(this, "请输入邮件主题", chatUserByLogIn.getName() + "发给你一封邮件");
        if (title == null) {
            return;
        }
        HashMap<String, String> fileNoAndNameMap = new HashMap<>();
        //遍历得到附件
        for (String filePath : richTextPanel.getFilePaths()) {
            String fileNo = UUID.randomUUID().toString();
            File file = new File(filePath);
            FilePacket filePacket = new FilePacket();
            filePacket.setFile(file);
            //创建邮件对象
            filePacket.setFileNo(fileNo);
            filePacket.setFileName(file.getName());
            //将自己的消息保存本地内存
            fileNoAndNameMap.put(fileNo, file.getName());
            CHANNEL.writeAndFlush(encode(filePacket));
        }
//        MailRequest mailRequest = new MailRequest(mail, title, richTextPanel.getPaneText(), mailSender, fileNoAndNameMap, new Date());
//        CHANNEL.writeAndFlush(ClientPacketCodeCompile.encode(mailRequest));
        //创建消息对象
        PrivateChatMessage privateMailMessage = new PrivateChatMessage(chatUserByLogIn.getChatUserNo(), title , counterpartChatNo, new Date(), false, PRIVATE_MAIL_TYPE);
//        privateMailMessage.setMsgNo(mailRequest.getMailNo());
//        PRIVATE_CHAT_MESSAGE_MAP.put(mailRequest.getMailNo(), privateMailMessage);
        privateMSGPanel.addPrivateChatCell(privateMailMessage);
        richTextPanel.setPaneText("");
    }


}


