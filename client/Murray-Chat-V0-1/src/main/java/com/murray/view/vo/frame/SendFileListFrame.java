package com.murray.view.vo.frame;

import com.murray.entity.FilePacket;
import com.murray.entity.PrivateChatMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.murray.agreement.MessageType.PRIVATE_FILE_TYPE;
import static com.murray.cache.ClientCache.*;
import static com.murray.handler.ClientPacketCodeCompile.encode;
import static com.murray.utils.ColorUtil.WHITE235;
import static com.murray.utils.JFameUtil.eventHandle;

/**
 * @author Murray Law
 * @describe 私聊窗口中打开, 批量发送文件
 * @createTime 2020/11/4
 */
public class SendFileListFrame extends JFrame {
    private JPanel mainPanel, headPanel, filePanel, footPanel;
    private JScrollPane fileScrollPane;
    private JButton exitButton, sendFileBtn, resubmitBtn;
    private static final List<File> fileList = new ArrayList<>();
    private PrivateChatFrame privateChatFrame;
    private File[] selectedFiles;

    public SendFileListFrame(PrivateChatFrame privateChatFrame) {
        this.privateChatFrame = privateChatFrame;
        //标题
        setResizable(false);//不可调整
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(new FlowLayout());
        setUndecorated(true);
        setSize(320, 400);
        setLocation(1300, 250);
//        setLocationRelativeTo(null);
        //设置主面板
        mainPanel = new JPanel();
        mainPanel.setBorder(null);
        mainPanel.setLayout(null);


        //头部panel
        setHeadPanel();

        //设置文件显示框
        setFilePanel();


        //脚部Panel
        setFootPanel();

        setContentPane(mainPanel);
        setVisible(true);//一定要放在最后面
        eventHandle(headPanel, this);//设置可拖动
    }

    /**
     * @param
     * @return void
     * @description 设置头部
     * @author Murray Law
     * @date 2020/11/4 10:43
     */
    private void setHeadPanel() {
        headPanel = new JPanel();
        headPanel.setBounds(0, 0, 320, 40);
        headPanel.setBorder(null);
        headPanel.setLayout(null);
        headPanel.setBackground(WHITE235);
        //退出按钮
        exitButton = new JButton("X");
        exitButton.setForeground(Color.black);
        exitButton.setBounds(300, 0, 25, 25);
        exitButton.setBorder(null);
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        headPanel.add(exitButton);
        //设置title,文字,默认居中
        JLabel titleLabel = new JLabel("发送文件给" + privateChatFrame.counterpartChatNo, JLabel.CENTER);
        titleLabel.setBounds(60, 0, 200, 40);
        headPanel.add(titleLabel);
        mainPanel.add(headPanel);
    }

    /**
     * @param
     * @return void
     * @description 设置文件面板
     * @author Murray Law
     * @date 2020/11/5 0:41
     */
    private void setFilePanel() {
        //滑动面板,文件特别多时显示
        fileScrollPane = new JScrollPane();
        fileScrollPane.setBounds(0, 40, 320, 320);
        //文件区域panel,存放文件列表
        filePanel = new JPanel();
        GridLayout gridLayout = new GridLayout(0, 1);
        gridLayout.setVgap(20);
        filePanel.setLayout(gridLayout);
        filePanel.setBackground(Color.white);
        selectTheFile();
        mainPanel.add(fileScrollPane);
    }

    /**
     * @param
     * @return void
     * @description 设置足部面板
     * @author Murray Law
     * @date 2020/11/5 0:42
     */
    private void setFootPanel() {

        footPanel = new JPanel();
        footPanel.setLayout(null);
        footPanel.setBounds(0, 360, 320, 40);
        footPanel.setBackground(WHITE235);
        sendFileBtn = new JButton("一键发送");//一键发送按钮
        sendFileBtn.setBounds(180, 5, 100, 30);
        sendFileBtn.setContentAreaFilled(false);
        sendFileBtn.addActionListener(e -> {
            //发送文件消息
            for (File file : fileList) {
                String fileSize = "(" + (double) (file.length() / 1024 / 1024) + "MB)";
                FilePacket filePacket = new FilePacket();
                filePacket.setFile(file);
                //创建消息对象
                PrivateChatMessage privateChatMessage = new PrivateChatMessage(chatUserByLogIn.getChatUserNo(), file.getName() + fileSize, privateChatFrame.counterpartChatNo, new Date(), false, PRIVATE_FILE_TYPE);
                filePacket.setFileNo(privateChatMessage.getMsgNo());
                filePacket.setFileName(file.getName());
                //将自己的消息保存本地内存
                PRIVATE_CHAT_MESSAGE_MAP.put(privateChatMessage.getMsgNo(), privateChatMessage);
                privateChatFrame.privateMSGPanel.addPrivateChatCell(privateChatMessage);
                CHANNEL.writeAndFlush(encode(privateChatMessage));
                CHANNEL.writeAndFlush(encode(filePacket));
            }
            filePanel.removeAll();
            filePanel.repaint();
        });
        resubmitBtn = new JButton("继续选择");
        resubmitBtn.setBounds(40, 5, 100, 30);
        resubmitBtn.setContentAreaFilled(false);
        resubmitBtn.addActionListener(e -> {
            //
            selectTheFile();
        });
        footPanel.add(resubmitBtn);
        footPanel.add(sendFileBtn);
        mainPanel.add(footPanel);

    }


    /**
     * @param
     * @return void
     * @description 选择文件
     * @author Murray Law
     * @date 2020/11/5 0:36
     */
    private void selectTheFile() {
        JFileChooser chooser = new JFileChooser();             //设置选择器
        chooser.setMultiSelectionEnabled(true);             //设为多选
        int returnVal = chooser.showOpenDialog(this);        //是否打开文件选择框
        if (returnVal == JFileChooser.APPROVE_OPTION) {          //如果符合文件类型
            filePanel.removeAll();
            fileList.removeAll(fileList);
            selectedFiles = chooser.getSelectedFiles();
            for (File file : selectedFiles) {
                fileList.add(file);
                //显示已选择的文件
                JLabel fileLabel = new JLabel(file.getName());//输出相对路径
                fileLabel.setOpaque(true);
                fileLabel.setBackground(new Color(248, 215, 116));
                fileLabel.setBounds(15, 5, 270, 40);
                //去除文件
                JButton xBtn = new JButton("X");
                xBtn.setForeground(Color.gray);
                xBtn.setBorder(null);
                xBtn.setContentAreaFilled(false);
                xBtn.setBounds(240, 5, 30, 30);
                fileLabel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
                fileLabel.add(xBtn);
                JPanel jPanel = new JPanel();
                jPanel.setPreferredSize(new Dimension(290, 50));
                jPanel.setBackground(Color.white);
                jPanel.setLayout(null);
                jPanel.add(fileLabel);
                //关闭标签监听事件
                xBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        filePanel.remove(jPanel);
                        fileList.remove(file);
                        filePanel.repaint();
                    }
                });

                filePanel.add(jPanel);
            }

            filePanel.repaint();

        }
        fileScrollPane.setViewportView(filePanel);
    }
}
